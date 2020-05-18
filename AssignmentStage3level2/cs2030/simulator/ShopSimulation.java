package cs2030.simulator;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Stream;
import java.util.Optional;

public class ShopSimulation {

    private final RandomGenerator rg;

    private final ArrayList<Server> servers;

    private final PriorityQueue<Customer> customers;

    private final double probRest;

    public ShopSimulation(int seed, int numOfSv, int maxQueue, int numOfCus, double arrivalRate, double serviceRate,
            double restRate, double probRest) {
        this.rg = new RandomGenerator(seed, arrivalRate, serviceRate, restRate);
        this.customers = generateArrivals(numOfCus);
        this.servers = generateServers(numOfSv, maxQueue);
        this.probRest = probRest;
    }

    public PriorityQueue<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public PriorityQueue<Customer> generateArrivals(int numOfCus) {
        ArrayList<Customer> arrivals = new ArrayList<>();
        if (numOfCus != 0) {
            arrivals.add(new Customer(1, 0));
            for (int i = 2; i < numOfCus + 1; i++) {
                arrivals.add(new Customer(i, rg.genInterArrivalTime() + arrivals.get(arrivals.size() - 1).getTime()));
            }
        }
        return new PriorityQueue<>(arrivals);
    }

    public ArrayList<Server> generateServers(int numOfSv, int maxQueue) {
        ArrayList<Server> servers = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(numOfSv).map(x -> new Server(x, maxQueue, 0)).forEach(x -> servers.add(x));
        return servers;
    }

    public void simulation() {
        PriorityQueue<Event> finalLog = new PriorityQueue<>();
        PriorityQueue<Customer> custList = new PriorityQueue<>(this.customers);
        ArrayList<Server> servsList = new ArrayList<>(this.servers);

        int served = 0;
        double totalWaitingTime = 0;

        if (servsList.size() != 0) {
            while (custList.size() > 0) {

                Customer cu = custList.peek();
                Status status = cu.getStatus();
                Server sv;

                if (status == Status.ARRIVES) {
                    sv = findAvailableServer(cu, servsList);
                } else {
                    sv = findServer(cu, servsList);
                }

                if (status == Status.DONE) {
                    Server newSv = checkRest(sv);
                    servsList = replace(newSv, servsList);
                    sv = newSv;
                }

                if (status == Status.DONE && !sv.isQueueEmpty()) {
                    Customer next = sv.getWaiting().get(0);
                    totalWaitingTime += sv.getCompletionTime() - next.getTime();

                    ArrayList<Object> waitingResult = processWaiting(sv);
                    Server newSv = (Server) waitingResult.get(0);
                    servsList = replace(newSv, servsList);
                    // servsList.set(servsList.indexOf(sv), newSv);
                    Customer afterWaiting = (Customer) waitingResult.get(1);

                    // Add customers who are served
                    served++;
                    custList.add(afterWaiting);

                } else if (status == Status.ARRIVES || status == Status.SERVED) {

                    ArrayList<Object> result = processEvent(cu, sv);

                    if (result != null & result.size() == 2) {

                        Server newSv = (Server) result.get(0);

                        servsList = replace(newSv, servsList);
                        // servsList.set(servsList.indexOf(sv), newSv);

                        Customer nextEvent = (Customer) result.get(1);
                        if (nextEvent.getStatus() == Status.SERVED) {
                            served++;
                        }

                        custList.add(nextEvent);
                    }

                }

                finalLog.add(new Event(cu, sv));
                custList.remove(cu);

            }

            Stats stat = new Stats(customers.size(), served, totalWaitingTime);
            printing(finalLog);
            System.out.println(stat.toString());
        } else {
            PriorityQueue<Event> newList = new PriorityQueue<>();
            Server sv = new Server(1, 0, 0);
            // dummy server
            for (Customer cu : this.customers) {
                newList.add(new Event(cu, sv));
                newList.add(new Event(cu.statusChange(Status.LEAVES), sv));
            }

            Stats stat = new Stats(customers.size(), 0, 0);
            printing(newList);
            System.out.println(stat.toString());
        }

    }

    public static <T> void printing(PriorityQueue<T> list) {
        list.stream().forEach(System.out::println);
    }

    public Server findServer(Customer cu, ArrayList<Server> servers) {
        Status status = cu.getStatus();

        if (status == Status.SERVED || status == Status.DONE) {
            return servers.stream().filter(sv -> sv.getCustomer().get().equals(cu)).findFirst().orElse(servers.get(0));
        } else {
            return servers.stream().filter(s -> s.getWaiting().contains(cu)).findFirst().orElse(servers.get(0));
        }

    }

    public Server findAvailableServer(Customer cu, ArrayList<Server> servers) {
        Optional<Server> server = servers.stream().filter(sv -> sv.canServe(cu)).findFirst();
        if (!server.isPresent()) {
            server = servers.stream().filter(sv -> sv.canWait()).findFirst();
        }

        return (server.isPresent()) ? server.get() : this.servers.get(0);

    }

    public ArrayList<Object> processEvent(Customer cu, Server sv) {
        ArrayList<Object> list = new ArrayList<Object>(2);

        if (cu.getStatus() == Status.ARRIVES) {
            if (sv.canServe(cu)) {
                list.add(sv.serve(cu, rg.genServiceTime()).get());
                list.add(cu.statusChange(Status.SERVED));
            } else if (sv.canWait()) {
                list.add(sv.wait(cu).get());
                list.add(cu.statusChange(Status.WAITS));
            } else {
                list.add(sv);
                list.add(cu.statusChange(Status.LEAVES));
            }
        } else {
            list.add(sv);
            double serviceTime = sv.getServiceTime();
            list.add(cu.served(serviceTime));
        }

        return list;
    }

    public ArrayList<Object> processWaiting(Server sv) {
        ArrayList<Object> list = new ArrayList<Object>(2);

        Customer cu = sv.getWaiting().get(0);
        ArrayList<Customer> svWaitingList = sv.getWaiting();
        svWaitingList.remove(cu);
        double newStartTime = sv.getCompletionTime();
        cu = new Customer(cu.getID(), newStartTime);
        Server newSv;

        if (svWaitingList.size() == 0) {
            newSv = new Server(sv.getID(), sv.getMaxQueue(), 0);
            list.add(newSv.serve(cu, rg.genServiceTime()).get());
        } else {
            newSv = new Server(sv.getID(), cu, svWaitingList, sv.getMaxQueue(), rg.genServiceTime());
            list.add(newSv);
        }

        list.add(cu.statusChange(Status.SERVED));
        return list;
    }

    public Server checkRest(Server sv) {
        if (probRest > rg.genRandomRest()) {
            sv = sv.rest(rg.genRestPeriod());
        }
        return sv;
    }

    public ArrayList<Server> replace(Server sv, ArrayList<Server> servsList) {
        Optional<Server> original = servsList.stream().filter(s -> s.equals(sv)).findFirst();

        if (original.isPresent()) {
            servsList.set(servsList.indexOf(original.get()), sv);
        }
        return servsList;
    }

}