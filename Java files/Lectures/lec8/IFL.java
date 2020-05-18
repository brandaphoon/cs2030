import java.util.stream.Stream;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;

public interface IFL<T> {
    
    public static <T> IFL<T> generate(Supplier<T> supplier) {
        return IFLImpl.generate(supplier);
    }

    public static <T> IFL<T> iterate(T seed, Function<T, T> next) {
        return IFLImpl.iterate(seed, next);
    }

    public void forEach(Consumer<T> action);
}


