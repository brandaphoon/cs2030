import java.util.Scanner;
import java.util.stream.IntStream;

public class Addition {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        int j = sc.nextInt();

        IntStream.iterate(0, x -> x + 1).limit(String.valueOf(i + j).length()).reduce(0, (remember, pow) -> {
            int v1 = (i / (int) Math.pow(10, pow)) - (i / (int) Math.pow(10, pow + 1)) * 10;
            int v2 = (j / (int) Math.pow(10, pow)) - (j / (int) Math.pow(10, pow + 1)) * 10;
            int sum = v1 + v2 + remember;
            int carry = sum / 10;
            System.out.println("" + v1 + " + " + v2 + (remember == 0 ? "" : " (1) ") + " = " + (sum % 10)
                    + (carry == 0 ? "" : " carry 1"));
            return carry;
        });

        sc.close();
    }
}