import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lazy<T> {
    private T value;
    private Supplier<? extends T> supp;
    private boolean cached;

    Lazy(T value) {
        this.value = value;
        this.supp = () -> value;
        this.cached = true;
    }

    Lazy(Supplier<? extends T> supp) {
        this.value = null;
        this.supp = supp;
        this.cached = false;
    }

    static <T> Lazy<T> ofNullable(T value) {
        return new Lazy<T>(value);
    }

    static <T> Lazy<T> generate(Supplier<? extends T> supp) {
        return new Lazy<T>(supp);
    }

    <R> Lazy<R> map(Function<? super T, ? extends R> func) {
        /*if (!cached) {
            this.value = (T) this.supp.get();
        }

        if (this.value == null){
            return new Lazy<R>(() -> null);
        } else {
            return new Lazy<R>(() -> func.apply(this.value));
        }*/
        return Lazy.generate(() -> this.get().map(func).orElse(null));
        //Using the map of the Optional
    }

    Lazy<T> filter(Predicate<? super T> pred) {
        return Lazy.generate(() -> Optional.of(this.get()).get().filter(pred).orElse(null));
        //Using the filter of the Optional
    }

    public Optional<T> get() {
        
        if (!cached) {
            value = supp.get();
            cached = true;
        }

        return Optional.ofNullable(value); 
    }

    @Override
    public String toString() {
        if (this.cached == true && this.value == null) {
            return "null";
        }
        if (!cached) {
            return "?";
        } else {
            return String.format("%s",this.value );
        }
    }

    public boolean isEmpty() {
        return true;
    }
}