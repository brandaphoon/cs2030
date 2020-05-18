package cs2030.simulator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

public class ShopSimulation {

    private final RandomGenerator rg;

    private final ArrayList<Server> servers;

    private final ArrayList<ArriveEvent> customers;

    private final double probRest;

    public ShopSimulation(int seed, int numOfSv, int numOfSelf, int maxQueue, int numOfCus, double arrivalRate,
            double serviceRate, double restingRate, double prob) {
        rg = new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);
        customers = generateArrivals(numOfCus);
        servers = generateServers(numOfSv, numOfSelf);
        probRest = prob;
        Server.setMaxQueue(maxQueue);
    }

    public ArrayList<ArriveEvent> generateArrivals(int n) {
        ArrayList<ArriveEvent> arrivals = new ArrayList<>();
        if (n != 0) {
            arrivals.add(new ArriveEvent(0, new Customer(1)));
            for (int i = 2; i < n + 1; i++) {
                double time = arrivals.get(arrivals.size() - 1).getTime() + rg.genInterArrivalTime();
                ArriveEvent ae = new ArriveEvent(time, new Customer(i));
                arrivals.add(ae);
            }
        }
        return arrivals;
    }

    // Creates the HumanServers and SelfCheck counters
    public ArrayList<Server> generateServers(int numOfSv, int numOfSelf) {
        ArrayList<Server> servers = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(numOfSv).map(i -> new HumanServer(i)).forEach(s -> servers.add(s));
        Stream.iterate(numOfSv + 1, i -> i + 1).limit(numOfSelf).map(i -> new SelfCheck(i))
                .forEach(s -> servers.add(s));
        return servers;
    }

    public void simulation() {

        // Compares the event
        Comparator<Event> com = new EventComparator();
        Comparator<ServedEvent> scom = new ServedComparator();
        ArrayList<Server> servsList = new ArrayList<Server>(this.servers);

        PriorityQueue<ServedEvent> servedEvents = new PriorityQueue<>(scom);
        Queue<Event> finalLog = new LinkedList<>();

        PriorityQueue<Event> log = new PriorityQueue<>(com);
        log.addAll(customers);

        int served = 0;
        double waitingTime = 0;

        if (servsList.size() != 0) {

            while (log.size() != 0 || servedEvents.size() != 0) {

                // Process Served Events by time
                if (log.size() == 0
                        || (servedEvents.size() != 0 && servedEvents.peek().getDoneTime() <= log.peek().getTime())) {
                    // process done event
                    CustomerEvent e = servedEvents.poll();
                    e = processServed((ServedEvent) e, servsList);
                    Server afterService = ((DoneEvent) e).getServer();
                    servsList = replaceServer(afterService, servsList);
                    log.add(e);
                    served++;
                }

                Event e = log.poll();
                if (e instanceof ArriveEvent) {
                    finalLog.add(e);
                    Server sv = findAvailableServer(((ArriveEvent) e), servsList);
                    e = processArrival((ArriveEvent) e, sv);

                    if (e instanceof ServedEvent) {
                        finalLog.add(e);
                        // Update new Server
                        Server newSv = ((ServedEvent) e).getServer();
                        servsList = replaceServer(newSv, servsList);
                        servedEvents.add((ServedEvent) e);

                    } else if (e instanceof WaitEvent) {
                        finalLog.add(e);
                        Server newSv = ((WaitEvent) e).getServer();
                        servsList = replaceServer(newSv, servsList);
                    } else {
                        finalLog.add(e);
                    }

                } else if (e instanceof DoneEvent || e instanceof ServerBack) {

                    Server sv;

                    if (e instanceof DoneEvent) {
                        finalLog.add(e);
                        sv = findServer(((DoneEvent) e).getServer(), servsList);

                        // if rest -> add serverback to log
                        if (sv instanceof HumanServer && checkRest()) {
                            double timeBack = e.getTime() + rg.genRestPeriod();
                            sv = ((HumanServer) sv).rest();
                            servsList = replaceServer(sv, servsList);
                            ServerBack sb = new ServerBack(timeBack, sv);
                            log.add(sb);
                            continue;
                        }

                    } else {

                        sv = findServer(((ServerBack) e).getServer(), servsList);
                        // waking the server
                        sv = ((HumanServer) sv).doneResting();
                        servsList = replaceServer(sv, servsList);
                    }

                    // if not, just continue with bottom
                    if (sv instanceof HumanServer && ((HumanServer) sv).getWaitingList().size() != 0
                            || sv instanceof SelfCheck && SelfCheck.getWaitingList().size() != 0) {
                        e = processWait(e, servsList);
                        Server newSv = ((ServedEvent) e).getServer();
                        servsList = replaceServer(newSv, servsList);
                        finalLog.add(e);
                        servedEvents.add((ServedEvent) e);

                        // Calculate Waiting Time
                        double startTime = findCustomerArrival(((ServedEvent) e).getCustomer());
                        waitingTime += e.getTime() - startTime;
                    }

                }
            }

        } else {
            for (Event ce : log) {
                finalLog.add(ce);
                LeaveEvent le = new LeaveEvent(ce.getTime(), ((ArriveEvent) ce).getCustomer());
                finalLog.add(le);
            }
        }

        while (finalLog.size() != 0) {
            System.out.println(finalLog.poll());

        }

        Stats stats = new Stats(customers.size(), served, waitingTime);
        System.out.println(stats.toString());
    }

    public CustomerEvent processArrival(ArriveEvent ae, Server sv) {
        if (sv.canServe()) {
            // if can serve, return served event
            return new ServedEvent(ae.getTime(), rg.genServiceTime(), ae.getCustomer(), sv);
        } else if (sv instanceof HumanServer && ((HumanServer) sv).canWait() || sv instanceof SelfCheck && SelfCheck.canWait()) {
            // if can wait, return wait event
            return new WaitEvent(ae.getTime(), ae.getCustomer(), sv);
        } else {
            // If both can't, return leave
            return new LeaveEvent(ae.getTime(), ae.getCustomer());
        }
    }

    public CustomerEvent processServed(ServedEvent se, ArrayList<Server> list) {
        double finishedTime = se.getDoneTime();
        Server sv = findServer(se.getServer(), list);
        Server afterService = sv.doneServing();
        return new DoneEvent(finishedTime, se.getCustomer(), afterService);
    }

    public CustomerEvent processWait(Event e, ArrayList<Server> servsList) {
        Server sv;
        if (e instanceof DoneEvent) {
            sv = findServer(((DoneEvent) e).getServer(), servsList);
        } else {
            sv = findServer(((ServerBack) e).getServer(), servsList);
        }

        if (sv instanceof HumanServer) {
            Queue<Customer> waitingList = ((HumanServer) sv).getWaitingList();
            Customer cu = waitingList.poll();
            return new ServedEvent(e.getTime(), rg.genServiceTime(), cu, sv, waitingList);
        } else {
            Queue<Customer> waitingList = SelfCheck.getWaitingList();
            Customer cu = waitingList.poll();
            return new ServedEvent(e.getTime(), rg.genServiceTime(), cu, sv);
        }


        /*if (e instanceof DoneEvent) {
            Server sv = findServer(((DoneEvent) e).getServer(), servsList);
            Queue<Customer> waitingList = ((HumanServer) sv).getWaitingList();
            Customer cu = waitingList.poll();
            return new ServedEvent(e.getTime(), rg.genServiceTime(), cu, ((DoneEvent) e).getServer(), waitingList);
        } else {
            Server sv = findServer(((ServerBack) e).getServer(), servsList);
            Queue<Customer> waitingList = ((HumanServer) sv).getWaitingList();
            Customer cu = waitingList.poll();
            return new ServedEvent(e.getTime(), rg.genServiceTime(), cu, ((ServerBack) e).getServer(), waitingList);
        }*/
    }

    public Server findAvailableServer(CustomerEvent e, ArrayList<Server> list) {
        Optional<Server> s = list.stream().filter(sv -> sv.canServe()).findFirst();
        if (!s.isPresent()) {
            //Checking if its a HumanServer or SelfCheck, and whether we can wait
            s = list.stream()
                    .filter(sv -> (sv instanceof HumanServer) ? (((HumanServer) sv).canWait()) : SelfCheck.canWait())
                    .findFirst();
        }

        if (!s.isPresent()) {
            return list.get(0);
        }

        return s.get();
    }

    public ArrayList<Server> replaceServer(Server sv, ArrayList<Server> list) {
        Server original = list.stream().filter(s -> s.equals(sv)).findFirst().get();
        list.set(list.indexOf(original), sv);
        return list;
    }

    public Server findServer(Server sv, ArrayList<Server> list) {
        return list.stream().filter(s -> s.equals(sv)).findFirst().get();
    }

    public double findCustomerArrival(Customer cu) {
        return customers.stream().filter(c -> c.getCustomer().getID() == cu.getID()).findFirst().get().getTime();
    }

    public boolean checkRest() {
        return probRest > rg.genRandomRest();
    }

}