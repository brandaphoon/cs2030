import java.util.stream.Stream;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.function.Consumer;
import java.util.Optional;

public interface InfiniteList<T> {
    
    public static <T> InfiniteList<T> generate(Supplier<? extends T> supplier) {
        return InfiniteListImpl.generate(supplier);
    }

    public static <T> InfiniteList<T> iterate(T seed, UnaryOperator<T> next) {
        return InfiniteListImpl.iterate(seed, next);
    }

    public void forEach(Consumer<? super T> action);

    Optional<T> get();
}


