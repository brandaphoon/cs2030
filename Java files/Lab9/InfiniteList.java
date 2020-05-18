import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.BinaryOperator;

interface InfiniteList<T> {

    public static <T> InfiniteList<T> generate(Supplier<? extends T> supplier) {
        return InfiniteListImpl.generate(supplier);
    }

    public static <T> InfiniteList<T> iterate(T seed, UnaryOperator<T> next) {
        return InfiniteListImpl.iterate(seed, next);
    }

    public InfiniteList<T> peek();

    public InfiniteList<T> filter(Predicate<? super T> predicate);

    public <R> InfiniteList<R> map(Function<? super T, ? extends R> mapper);
    
    public boolean isEmpty();

    public InfiniteList<T> limit(long maxSize);

    public InfiniteList<T> takeWhile(Predicate<? super T> predicate);

    public Object[] toArray();
    
    public long count();

    public T reduce(T identity, BinaryOperator<T> accumulator);

    public void forEach(Consumer<? super T> action);
    
}
