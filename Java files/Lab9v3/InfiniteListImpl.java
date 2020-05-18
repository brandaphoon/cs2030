import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.Optional;
import java.util.function.Predicate;
import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

class InfiniteListImpl<T> implements InfiniteList<T> {
    private final Lazy<T> head;
    private final Supplier<InfiniteListImpl<T>> tail;

    private InfiniteListImpl(Lazy<T> head, Supplier<InfiniteListImpl<T>> 
            tail) {
        this.head = head;
        this.tail = tail;
    }

    protected InfiniteListImpl(Supplier<InfiniteListImpl<T>> tail) {
        this.head = Lazy.ofNullable(null);
        this.tail = tail;
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> s) {
        Lazy<T> newHead = Lazy.generate(s);
        Supplier<InfiniteListImpl<T>> newTail = 
            () -> InfiniteListImpl.generate(s);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, 
            UnaryOperator<T> next) {
        Lazy<T> newHead = Lazy.ofNullable(seed);
        Supplier<InfiniteListImpl<T>> newTail = 
            () -> InfiniteListImpl.iterate(next.apply(seed), next);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    public InfiniteListImpl<T> peek() {
        this.head.get().ifPresent(System.out::println);
        return this.tail.get();
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        Lazy<T> newHead = Lazy.generate(() -> head.filter(predicate).get().orElse(null));
        Supplier<InfiniteListImpl<T>> newTail = 
            () -> tail.get().filter(predicate);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    public <R>  InfiniteListImpl<R> map(Function<? super T, ? extends R> 
            mapper) {
        Lazy<R> newHead = Lazy.generate(() -> head.map(mapper).get().orElse(null));
        Supplier<InfiniteListImpl<R>> newTail = () -> tail.get().map(mapper);
        return new InfiniteListImpl<R>(newHead, newTail);
    }

    public boolean isEmpty() {
        return this instanceof EmptyList;
    }

    public InfiniteListImpl<T> limit(long maxSize) { 
        if (maxSize <= 0 || this.isEmpty()) {
            return new EmptyList<T>();
        } else if (maxSize == 1) {
            if (head.get().isPresent()) {
                return new InfiniteListImpl<T>(head,() -> new EmptyList<T>());
            } else {
                return tail.get().limit(maxSize);
            }
        } else {
            if (head.get().isPresent()) {
                return new InfiniteListImpl<T>(head,() -> tail.get().limit(maxSize - 1));
            } else {
                return tail.get().limit(maxSize);
            }
        }     
    } 

    public InfiniteListImpl<T> takeWhile(Predicate<? super T> predicate) { 
        Lazy<T> newHead = head.filter(predicate);
        Supplier<InfiniteListImpl<T>> newTail = () -> (head.get().isPresent() 
                && !newHead.get().isPresent()) ? 
            new EmptyList<T>() : tail.get().takeWhile(predicate);
        return new InfiniteListImpl<T>(newHead, newTail);  
    }

    public Object[] toArray() {
        InfiniteListImpl<T> curr = this;
        ArrayList<Object> list = new ArrayList<>();
        
        while(!curr.isEmpty()) {
            if (curr.head.get().isPresent()) {
                list.add(curr.head.get().get());
            }

            curr = curr.tail.get();
        }

        return list.toArray();
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


    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator) {
        U v = identity;
        InfiniteListImpl<T> curr = this;
        while(!curr.isEmpty()) {
            if (curr.head.get().isPresent()) {
                v = accumulator.apply(v,curr.head.get().get());
            }

            curr = curr.tail.get();
        }

        return v;
    }

    public void forEach(Consumer<? super T> action) {
        InfiniteListImpl<T> curr = this;

        while (!curr.isEmpty()) {
            curr.head.get().ifPresent(action::accept);
            curr = curr.tail.get();
        }

    }

}

