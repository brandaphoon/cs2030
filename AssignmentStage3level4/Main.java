import java.util.Scanner;

import cs2030.simulator.ShopSimulation;

/**
 * Main class represents the main enrty to the simulation where the inputs are
 * collected.
 */
public class Main {
    /**
     * Collect user inputs from string arguments to run event simulation.
     * 
     * @param args An array of string inputs.
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int seed = sc.nextInt();

        int numOfSv = sc.nextInt();

        int numOfSelf = sc.nextInt();

        int maxQueue = sc.nextInt();

        int numOfCus = sc.nextInt();

        double arrivalRate = sc.nextDouble();

        double serviceRate = sc.nextDouble();

        double restingRate = sc.nextDouble();

        double prob = sc.nextDouble();

        double probGreedy = sc.nextDouble();

        ShopSimulation sh = new ShopSimulation(seed, numOfSv, numOfSelf, maxQueue, numOfCus, 
                arrivalRate, serviceRate, restingRate, prob, probGreedy);

        sc.close();

        sh.simulation();

    }
}