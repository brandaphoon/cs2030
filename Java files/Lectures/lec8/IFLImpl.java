import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;

class IFLImpl<T> implements IFL<T> {
    
    private final Supplier<Optional<T>> head;
    private final Supplier<IFLImpl<T>> tail;
    
    IFLImpl(Supplier<Optional<T>> head, Supplier<IFLImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }
    
    static <T> IFLImpl<T> generate(Supplier<T> supplier) {
        Supplier<Optional<T>> newHead = () ->  Optional.of(supplier.get());
        Supplier<IFLImpl<T>> newTail = () -> IFLImpl.generate(supplier);
        
        return new IFLImpl<T>(newHead, newTail);
    }

    boolean isEmpty() {
        return false;
    }

    static <T> IFLImpl<T> iterate(T seed, Function<T, T> next) {
        Supplier<Optional<T>> newHead = () -> Optional.of(seed);
        Supplier<IFLImpl<T>> newTail = () -> IFLImpl.iterate(next.apply(seed),
                next);
        return new IFLImpl<T>(newHead, newTail); 
    }

    public void forEach(Consumer<T> action) {
        IFLImpl<T> curr = this;
        while (true) {
            curr.head.get().ifPresent(action);
            curr = curr.tail.get();
        }
    }
    
    public <R> IFLImpl<R> map(Function<T,R> mapper) {
        Supplier<Optional<R>> newHead = () -> head.get().map(mapper);
        Supplier<IFLImpl<R>> newTail = () -> tail.get().map(mapper);
        return new IFLImpl<R>(newHead, newTail);
    }
    
    public IFLImpl<T> filter(Predicate<T> predicate) {
        Supplier<Optional<T>> newHead = () -> head.get().filter(predicate);
        Supplier<IFLImpl<T>> newTail = () -> tail.get().filter(predicate);
        return new IFLImpl<T>(newHead, newTail);
    }
}
