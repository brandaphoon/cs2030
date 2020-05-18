import java.util.function.Supplier;
import java.util.Optional;
import java.util.Scanner;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.BiFunction;

interface Compute<T> {

    boolean isRecursive();

    Compute<T> recurse();

    T evaluate();
}

class Base<T> implements Compute<T> {
    private Supplier<T> res;
    
    Base(Supplier<T> res) {
        this.res = res;
    }

    public boolean isRecursive() {
        return false;
    }

    public T evaluate() {
        return res.get();
    }

    public Compute<T> recurse() {
        throw new IllegalStateException("Invalid recursive call in base case");
    }
}

class Recursive<T> implements Compute<T>{
    private Supplier<Compute<T>> supplier;

    Recursive(Supplier<Compute<T>> supplier) {
        this.supplier = supplier;
    }

    public boolean isRecursive() {
        return true;
    }

    public Compute<T> recurse() {
        return supplier.get();
    }

    public T evaluate() {
        throw new IllegalStateException("Invalid evaluation in recursive case");
    }


}

class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        //System.out.println(summer(n));
        System.out.println(factorial(n));

        sc.close();
    }

    static Compute<Long> sum(long n, long s) {
        if (n == 0) {
            return new Base<>(() -> s);
        } else {
            return new Recursive<>(() -> sum(n-1, n+s));
        }
    }

    static long summer(long n) {
        Compute<Long> result = sum(n,0);

        while (result.isRecursive()) {
            result = result.recurse();
        }

        return result.evaluate();
    }

    static Compute<Long> factor(long n, long s) {
        if (n == 1) {
            return new Base<>(() -> s);
        } else {
            return new Recursive<>(() -> factor(n-1, n*s));
        }
    }

    static long factorial(long n) {
        Compute<Long> result = factor(n, 1);

        while (result.isRecursive()) {
            result = result.recurse();
        }

        return result.evaluate();
    }

}


class Lazy<T> {
    T value;
    Supplier<T> supplier;

    Lazy(T v) {
        this.value = v;
        this.supplier = () -> v;
    }

    Lazy(Supplier<T> supp) {
        this.value = null;
        this.supplier = supp;
    }

    static <T> Lazy<T> of(T v) {
        return new Lazy<>(v);
    }

    static <T> Lazy<T> of(Supplier<T> s) {
        return new Lazy<>(s);
    }

    T get() {
        if (this.value == null) {
            this.value = supplier.get();
        }

        return this.value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "?";
        } 
        
        return String.format("The value is: %s", this.get());   
    }

    <R> Lazy<R> map(Function<T,R> mapper) {
        return Lazy.of(() -> mapper.apply(this.supplier.get()));
    }

    <R> Lazy<R> flatMap(Function<T, Lazy<R>> mapper) {
        return Lazy.of(() -> mapper.apply(this.supplier.get()).get());
    }

    <R,U> Lazy<R> combine(Lazy<U> lazy, BiFunction<T,U,R> func) {
        return Lazy.of(() -> func.apply(this.get(), lazy.get()));
    }
}


class LazyList<T> {
    private List<Lazy<T>> list;

    LazyList(List<LazyList<T>> list) {
        this.list = list;
    }

    static <T> LazyList<T> generate(int n, T seed, UnaryOperator<T> f) {
        return new LazyList<T> (
            Stream.iterate(Lazy.of(seed), x -> x.map(f.apply(v))).limit(n).collect(Collectors.toList()));
    }

    public Lazy<T> get(int i){
        return this.list.get(i);
    }

    public int indexOf(T v){
        return this.list.indexOf(v);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

