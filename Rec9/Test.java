import java.util.function.Function;

public class Test {
    
    static int it = 1000;
    public static void main(String[] args) {

        int thing = 100;

        

        Function<Integer, Integer> f = x -> it = x;

        System.out.println(f.apply(1));
        System.out.println(it);
        System.out.println(f.apply(30));
        System.out.println(it);

        Function<String, Integer> co = new Function<String, Integer> () {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        

        System.out.println(co.apply("String"));
       
    }

    
}