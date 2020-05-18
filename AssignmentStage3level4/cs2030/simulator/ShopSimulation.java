package cs2030.simulator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * ShopSimulation class represents the simulation of the discrete event
 * simulator.
 */

public class ShopSimulation {

    /**
     * The RandomGenerator that is used for the simulation.
     */

    private final RandomGenerator rg;

    /**
     * The list of servers in the shop.
     */

    private final ArrayList<Server> servers;

    /**
     * The list of ArriveEvents when customers arrive.
     */

    private final ArrayList<ArriveEvent> customers;

    /**
     * The probability of resting for the human servers.
     */

    private final double probRest;

    /**
     * The probability of the customer that entered is a greedy customer.
     */

    private final double probGreedy;

    /**
     * Creates a new ShopSimulation with a RandomGenerator, servers, arriving
     * customers, probability of rest for servers and greedy customers. While
     * setting the maximum queue number of all the servers.
     * 
     * @param seed        The seed for the RandomGenerator
     * @param numOfSv     The number of servers to be generated.
     * @param numOfSelf   The number of self-check counters.
     * @param maxQueue    The maximum number of customers allowed to wait in the
     *                    queue.
     * @param numOfCus    The number of customers that arrived.
     * @param arrivalRate The rate of arrival for the RandomGenerator.
     * @param serviceRate The rate of service for the RandomGenerator.
     * @param restingRate The rate of resting for human servers for the
     *                    RandomGenerator.
     * @param probRest    The probability of human server resting.
     * @param probGreedy  The probability of the greedy customer.
     */

    public ShopSimulation(int seed, int numOfSv, int numOfSelf, int maxQueue, 
            int numOfCus, double arrivalRate, double serviceRate, double restingRate, 
            double probRest, double probGreedy) {
        this.rg = new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);
        this.probGreedy = probGreedy;
        this.customers = generateArrivals(numOfCus);
        this.servers = generateServers(numOfSv, numOfSelf);
        this.probRest = probRest;
        Server.setMaxQueue(maxQueue);
    }

    /**
     * Generate customer arrival events and populate into a list.
     * 
     * @param n The number of arrivals.
     * @return A list of ArriveEvents with both greedy and normal customers.
     */

    public ArrayList<ArriveEvent> generateArrivals(int n) {
        ArrayList<ArriveEvent> arrivals = new ArrayList<>();
        boolean first = true;

        for (int i = 1; i < n + 1; i++) {
            if (probGreedy > rg.genCustomerType()) {
                if (first) {
                    arrivals.add(new ArriveEvent(0, new GreedyCustomer(1)));
                    first = false;
                } else {
                    double time = arrivals.get(arrivals.size() - 1).getTime() + 
                            rg.genInterArrivalTime();
                    arrivals.add(new ArriveEvent(time, new GreedyCustomer(i)));
                }
            } else {
                if (first) {
                    arrivals.add(new ArriveEvent(0, new Customer(1)));
                    first = false;
                } else {
                    double time = arrivals.get(arrivals.size() - 1).getTime() + 
                            rg.genInterArrivalTime();
                    arrivals.add(new ArriveEvent(time, new Customer(i)));
                }

            }

        }

        return arrivals;
    }

    /**
     * Generate human and self-check servers and populate into a list.
     * 
     * @param numOfSv   The number of human servers.
     * @param numOfSelf The number of self-check "servers".
     * @return A list of both human and self-check servers.
     */

    public ArrayList<Server> generateServers(int numOfSv, int numOfSelf) {
        ArrayList<Server> servers = new ArrayList<>();

        Stream.iterate(1, i -> i + 1)
            .limit(numOfSv).map(i -> new HumanServer(i))
            .forEach(s -> servers.add(s));

        Stream.iterate(numOfSv + 1, i -> i + 1)
            .limit(numOfSelf)
            .map(i -> new SelfCheck(i))
            .forEach(s -> servers.add(s));

        return servers;
    }

    /**
     * The simulation of the entire ShopSimulation, processing the events according
     * the their conditions and populating into a final log that would be printed as
     * a result.
     */

    public void simulation() {

        // Compares Events
        Comparator<Event> com = new EventComparator();
        // Compares ServedEvents
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

                // Process Served Events by the time
                if (log.size() == 0
                        || (servedEvents.size() != 0 && servedEvents.peek().getDoneTime() <= 
                                log.peek().getTime())) {
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
                    // Processing ArriveEvents
                    Server sv = findAvailableServer(((ArriveEvent) e), servsList);
                    e = processArrival((ArriveEvent) e, sv);

                    if (e instanceof ServedEvent) {
                        // Process events if the customer is immediately 
                        // served and update the servers.
                        finalLog.add(e);
                        // Update new Server
                        Server newSv = ((ServedEvent) e).getServer();
                        servsList = replaceServer(newSv, servsList);
                        servedEvents.add((ServedEvent) e);

                    } else if (e instanceof WaitEvent) {
                        // Process events if the customer is in the waiting queue and update the
                        // servers.
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

                        // checking if the human server needs to rest -> add serverback to log
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

                    // if not, just continue with process its waiting list.
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
            // If there are no servers, all the customers leave.
            for (Event ce : log) {
                finalLog.add(ce);
                LeaveEvent le = new LeaveEvent(ce.getTime(), ((ArriveEvent) ce).getCustomer());
                finalLog.add(le);
            }
        }

        while (finalLog.size() != 0) {
            // Printing the final log
            System.out.println(finalLog.poll());

        }

        // Printing the statistics of the simulation
        Stats stats = new Stats(customers.size(), served, waitingTime);
        System.out.println(stats.toString());
    }

    /**
     * Process ArriveEvent with three scenarios: (i) The customer would be served
     * immediately. (ii) The customer would be placed on the waiting list. (iii) The
     * customer would leave if the server cannot serve or the customer cannot wait.
     * 
     * @param ae The ArriveEvent
     * @param sv The server to either serve, place the customer on wait or cause the
     *           customer to leave.
     * @return A CustomerEvent (ServedEvent/LeaveEvent/WaitEvent).
     */
    public CustomerEvent processArrival(ArriveEvent ae, Server sv) {
        if (sv.canServe()) {
            // if can serve, return served event
            return new ServedEvent(ae.getTime(), rg.genServiceTime(), ae.getCustomer(), sv);
        } else if (sv instanceof HumanServer && ((HumanServer) sv).canWait()
                || sv instanceof SelfCheck && SelfCheck.canWait()) {
            // if can wait, return wait event
            return new WaitEvent(ae.getTime(), ae.getCustomer(), sv);
        } else {
            // If both can't, return leave
            return new LeaveEvent(ae.getTime(), ae.getCustomer());
        }
    }

    /**
     * Process ServedEvent to complete the service into a DoneEvent and updating the
     * server to no longer serving the customer.
     * 
     * @param se        The ServedEvent to be processed.
     * @param servsList The list of servers in the shop.
     * @return A new DoneEvent representing the customer's completion of service.
     */

    public DoneEvent processServed(ServedEvent se, ArrayList<Server> servsList) {
        double finishedTime = se.getDoneTime();
        Server sv = findServer(se.getServer(), servsList);
        Server afterService = sv.doneServing();
        return new DoneEvent(finishedTime, se.getCustomer(), afterService);
    }

    /**
     * Process a DoneEvent or a ServerBack event where the server involved would
     * serve the next customer on the waiting list.
     * 
     * @param e         The event to be processed.
     * @param servsList The list of servers in the shop.
     * @return A new ServedEvent that represents the next waiting customer being
     *         served.
     */
    public ServedEvent processWait(Event e, ArrayList<Server> servsList) {
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
    }

    /**
     * To find a available server to serve a new customer that arrived or to allow
     * the customer to wait for.
     * 
     * @param ae        The ArriveEvent with the customer.
     * @param servsList The list of servers in the shop.
     * @return A human server or a self-check server.
     */

    public Server findAvailableServer(ArriveEvent ae, ArrayList<Server> servsList) {
        Optional<Server> s = servsList.stream().filter(sv -> sv.canServe()).findFirst();
        if (!s.isPresent()) {
            if (ae.getCustomer() instanceof GreedyCustomer) {
                // If customer is greedy, then check for the shortest waiting queue.
                // Checking if its a HumanServer or SelfCheck, and whether we can wait.
                int shortest = servsList.stream()
                        .map(sv -> (sv instanceof HumanServer) ? 
                            (((HumanServer) sv).getWaitingList().size())
                                : SelfCheck.getWaitingList().size())
                        .min(Integer::compare).get();

                if (shortest < Server.maxQueue) {
                    s = servsList.stream()
                            .filter(sv -> (sv instanceof HumanServer)
                                    ? (((HumanServer) sv).getWaitingList().size() == shortest)
                                    : SelfCheck.getWaitingList().size() == shortest)
                            .findFirst();
                }

            } else {

                // Checking if its a HumanServer or SelfCheck, and whether we can wait.
                s = servsList.stream()
                            .filter(
                                sv -> (sv instanceof HumanServer) ? 
                                    (((HumanServer) sv).canWait()) : SelfCheck.canWait())
                            .findFirst();
            }
        }

        if (!s.isPresent()) {
            // If there is no available servers, return the first server.
            return servsList.get(0);
        }

        return s.get();
    }

    /**
     * Update the server list with an updated server.
     * 
     * @param sv        The updated server.
     * @param servsList The current list of servers in the shop.
     * @return The updated list of servers in the shop.
     */

    public ArrayList<Server> replaceServer(Server sv, ArrayList<Server> servsList) {
        Server original = servsList.stream().filter(s -> s.equals(sv)).findFirst().get();
        servsList.set(servsList.indexOf(original), sv);
        return servsList;
    }

    /**
     * To find the server in the current list of servers to ensure that we retrieve
     * the proper state of the server.
     * 
     * @param sv        The server to find in the list of servers.
     * @param servsList The list of servers in the shop.
     * @return The found server.
     */

    public Server findServer(Server sv, ArrayList<Server> servsList) {
        return servsList.stream().filter(s -> s.equals(sv)).findFirst().get();
    }

    /**
     * To find the arrival time of the given customer.
     * 
     * @param cu The customer that we are looking for.
     * @return The time of the customer's arrival.
     */

    public double findCustomerArrival(Customer cu) {
        return customers.stream()
            .filter(c -> c.getCustomer().getID() == cu.getID())
            .findFirst()
            .get()
            .getTime();
    }

    /**
     * Checking if the human server needs to rest.
     * 
     * @return True/False
     */

    public boolean checkRest() {
        return probRest > rg.genRandomRest();
    }

}