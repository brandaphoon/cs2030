public class Box<T> {
    private final T t;

    private Box(T t) {
        this.t = t;
    }

    public T get() {
        return this.t;
    }

    static <T> Box<T> of(T t){
        return new Box<>(t);
    }

    @Override
    public String toString() {
        return "[" + this.t + "]";
    }
}
