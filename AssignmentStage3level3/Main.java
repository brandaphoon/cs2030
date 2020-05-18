import java.util.Scanner;

import cs2030.simulator.ShopSimulation;

public class Main {
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

        ShopSimulation sh = new ShopSimulation(seed, numOfSv, numOfSelf, maxQueue, numOfCus, arrivalRate, serviceRate, restingRate, prob);

        sc.close();

        sh.simulation();

    }
}