import java.util.List;

/* class A {
    void foo(List<Integer> integerList) {}
    void foo(List<String> stringList) {};
} */

/* class B<T> {
    T x;
    static T y;
} */

class C<T> {
    static int b = 0;

    C() {
        this.b++;
    }

    public static void main(String[] args) {
        C<Integer> x = new C<>();
        C<String> y = new C<>();
        C<Boolean> z = new C<>();

        System.out.println(x.b);
        System.out.println(y.b);
        
    }
}
