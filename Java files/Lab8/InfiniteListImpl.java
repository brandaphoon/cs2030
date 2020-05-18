import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;

class InfiniteListImpl<T> implements InfiniteList<T> {
    
    private final Supplier<Optional<T>> head;
    private final Supplier<InfiniteListImpl<T>> tail;
    
    InfiniteListImpl(Supplier<Optional<T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }
    
    static <T> InfiniteListImpl<T> generate(Supplier<? extends T> supplier) {
        Supplier<Optional<T>> newHead = () ->  Optional.of(supplier.get());
        Supplier<InfiniteListImpl<T>> newTail = () -> 
            InfiniteListImpl.generate(supplier);
        
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    boolean isEmpty() {
        return false;
    }

    static <T> InfiniteListImpl<T> iterate(T seed, UnaryOperator<T> next) {
        Supplier<Optional<T>> newHead = () -> Optional.of(seed);
        Supplier<InfiniteListImpl<T>> newTail = () -> 
            InfiniteListImpl.iterate(next.apply(seed),
                next);
        return new InfiniteListImpl<T>(newHead, newTail); 
    }

    public void forEach(Consumer<? super T> action) {
        InfiniteListImpl<T> curr = this;
        while (true) {
            curr.head.get().ifPresent(action);
            curr = curr.tail.get();
        }
    }

    public <T> InfiniteListImpl<T> limit(long maxSize) {

        Supplier<Optional<T>> newHead = () -> this.head;
        Supplier<InfiniteListImpl<T>> newTail = () -> tail.get().limit(maxSize - 1);
        return new InfiniteListImpl<T>(newHead, newTail);

        
    }

    public Optional<T> get() {
        return this.head.get();
    }
    
    public <R> InfiniteListImpl<R> map(Function<T,R> mapper) {
        Supplier<Optional<R>> newHead = () -> head.get().map(mapper);
        Supplier<InfiniteListImpl<R>> newTail = () -> tail.get().map(mapper);
        return new InfiniteListImpl<R>(newHead, newTail);
    }
    
    public InfiniteListImpl<T> filter(Predicate<T> predicate) {
        Supplier<Optional<T>> newHead = () -> head.get().filter(predicate);
        Supplier<InfiniteListImpl<T>> newTail = () -> tail.get().filter(predicate);
        return new InfiniteListImpl<T>(newHead, newTail);
    }
}
