import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Optional;

public class Lazy<T> {
    private final Supplier<? extends T> supplier;
    private T value;
    private boolean cached;

    private Lazy(T v) {   
        this.supplier = () -> v;
        this.value = v;
        this.cached = true;
    }

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        this.value = null;
        this.cached = false;
    }

    static <T> Lazy<T> ofNullable(T v) {
        return new Lazy<T>(v);
    }

    static <T> Lazy<T> generate(Supplier<? extends T> supplier) {
        return new Lazy<T>(supplier);
    }

    public <R> Lazy<R> map(Function<? super T, ? extends R> mapper) {
        // return new Lazy<R>(() -> this.get().map(mapper).orElse(null));
        return Lazy.generate(() -> Optional.of(this.get()).get().map(mapper).orElse(null));
        //return Lazy.generate(() -> this.get().map(mapper).orElse(null));
    } 


    public Lazy<T> filter(Predicate<? super T> predicate) {
        // return new Lazy<T>(() -> this.get().filter(predicate).orElse(null));
        return Lazy.generate(() -> Optional.of(this.get()).get().filter(predicate).orElse(null));
        //return Lazy.generate(() -> this.get().filter(predicate).orElse(null));
    } 

    Optional<T> get() {

        if (!cached) {
            //if value is not cached, use supplier.get()'s value
            this.value = this.supplier.get();
            this.cached = true;
        }

        return Optional.ofNullable(this.value);
    }

    @Override
        public String toString() {
            if (this.cached == true && this.value == null) {
                return "null";
            }
            if (this.value == null) {
                return "?";
            }
            return this.value.toString();
        }

}



