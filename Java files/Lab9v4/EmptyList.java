import java.util.function.Predicate;
import java.util.function.Function;

public class EmptyList<T> extends InfiniteListImpl<T> {

    EmptyList() {
        super();
    }

    public EmptyList<T> filter(Predicate<? super T> predicate) {
        return new EmptyList<T>();
    }

    public <R> EmptyList<R> map(Function<? super T, ? extends R> mapper) {
        return new EmptyList<R>();
    }

    public EmptyList<T> limit(long n) {
        return new EmptyList<T>();
    }

    public EmptyList<T> takeWhile(Predicate<? super T> predicate) {
        return new EmptyList<T>();
    }

    public boolean isEmpty() {
        return true;
    }

}