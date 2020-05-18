import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class InfiniteListImpl<T> implements InfiniteList<T>{

    private final Lazy<T> head;
    private final Supplier<InfiniteListImpl<T>> tail;
    

    InfiniteListImpl(Lazy<T> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    InfiniteListImpl() {
        this.head = Lazy.ofNullable(null);
        this.tail = () -> new EmptyList<T>();
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> s) {
        Lazy<T> newHead = Lazy.generate(s);
        Supplier<InfiniteListImpl<T>> newTail = () -> InfiniteListImpl.generate(s);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, UnaryOperator<T> next) {
        Lazy<T> newHead = Lazy.ofNullable(seed);
        Supplier<InfiniteListImpl<T>> newTail = () -> InfiniteListImpl.iterate(next.apply(newHead.get().get()), next);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    public InfiniteListImpl<T> peek() {
        this.head.get().ifPresent(System.out::println);
        return this.tail.get();
    }

    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        //Lazy<R> newHead = Lazy.generate(() -> mapper.apply(head.get().get()));
        //Lazy<R> newHead = Lazy.generate(() -> head.map(mapper).get().orElse(null));
        Lazy<R> newHead = this.head.map(mapper);
        Supplier<InfiniteListImpl<R>> newTail = () -> tail.get().map(mapper);
        return new InfiniteListImpl<R>(newHead, newTail);
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate){
        //Lazy<T> newHead = Lazy.generate(() -> head.filter(predicate).get().orElse(null));
        Lazy<T> newHead = this.head.filter(predicate);
        Supplier<InfiniteListImpl<T>> newTail = 
            () -> tail.get().filter(predicate);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    public boolean isEmpty() {
        return false;
    }

    /*public InfiniteListImpl<T> limit(long n) {
        if (n <= 0) {
            return new EmptyList<>();
        }

        if (n == 1) {
            if (this.head.get().isPresent()) {
                return new InfiniteListImpl<T>(this.head, () -> new EmptyList<T>());
            } else {
                return tail.get().limit(n);
            }
        } else if (n > 1) {
            if (this.head.get().isPresent()) {
                Lazy<T> newHead = Lazy.ofNullable(head.get().get()); 
                Supplier<InfiniteListImpl<T>> newTail = () -> tail.get().limit(n-1);
                return new InfiniteListImpl<T>(newHead, newTail);
            } else {
                return tail.get().limit(n);
            }   
        } else {
            return new EmptyList<>();
        }
    };*/

    /*public InfiniteListImpl<T> limit(long n){
        if (n <= 0) {
            return new EmptyList<T>();
        } else if (this.head.get().isPresent()) {
            return new InfiniteListImpl<>(this.head, () -> (n == 1) ? new EmptyList<T>() : tail.get().limit(n-1));
        } else {
            return tail.get().limit(n);
        }
    }*/

    public InfiniteListImpl<T> limit(long n) {
        if (n <= 0) {
            return new EmptyList<T>();
        }
        
        if (n > 1) {
            Supplier<InfiniteListImpl<T>> newTail = ()-> !this.head.get().isEmpty()? tail.get().limit(n-1) : tail.get().limit(n);
            return new InfiniteListImpl<T>(this.head, newTail);     
        }

        Supplier<InfiniteListImpl<T>> newTail = ()-> !this.head.get().isEmpty()? new EmptyList<>() : tail.get().limit(n);
        return new InfiniteListImpl<T>(this.head, newTail);
    }

    public InfiniteListImpl<T> takeWhile(Predicate<? super T> predicate) { 
        Lazy<T> newHead = head.filter(predicate);
        Supplier<InfiniteListImpl<T>> newTail = () -> (head.get().isPresent() 
                && newHead.get().isEmpty()) ? 
            new EmptyList<T>() : tail.get().takeWhile(predicate);
        //if head is present and the filtered is not, it will stop.
        return new InfiniteListImpl<T>(newHead, newTail);  
    }

    public void forEach(Consumer<? super T> action) {
        InfiniteListImpl<T> curr = this;
        while(!curr.isEmpty()) {
            curr.head.get().ifPresent(action);
            curr = curr.tail.get();
        }
    }

    public long count() {
        InfiniteListImpl<T> curr = this;
        long n = 0;
        while(!curr.isEmpty()) {
            if (curr.head.get().isPresent()) {
                n++;
            }
            curr = curr.tail.get();
        }
        return n;
    }

    public Object[] toArray() {
        InfiniteListImpl<T> curr = this;
        List<Object> list = new ArrayList<>();
        while(!curr.isEmpty()) {
            if (curr.head.get().isPresent()) {
                list.add(curr.head.get().get());
            }
            curr = curr.tail.get();
        }

        return list.toArray();
    }

    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator) {
        U seed = identity;
        InfiniteListImpl<T> curr = this;

        while(!curr.isEmpty()) {
            if (curr.head.get().isPresent()) {
                seed = accumulator.apply(seed, curr.head.get().get());
            }
            curr = curr.tail.get();
        }

        return seed;


    }
    
    
}