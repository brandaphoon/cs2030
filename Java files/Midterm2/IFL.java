import java.util.List;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.Optional;
import java.util.ArrayList;

class IFL<T> {
    Supplier<T> head;
    Supplier<IFL<T>> tail;
    /* FIELDS AND METHODS START: DO NOT REMOVE */
    /* FIELDS AND METHODS END: DO NOT REMOVE */

    IFL(Supplier<T> head, Supplier<IFL<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    static <T> IFL<T> of(List<? extends T> list) {
        /* OF START: DO NOT REMOVE!!! */
        Supplier<T> newHead = () -> list.get(0);
        Supplier<IFL<T> > newTail = () -> (list.size() != 1) ? 
        IFL.of(list.subList(1, list.size())) : null;
        return new IFL<T>(newHead, newTail);
        /* OF END: DO NOT REMOVE!!! */
    }

    Optional<T> findMatch(Predicate<? super T> predicate) {
        /* FINDMATCH START: DO NOT REMOVE!!! */
        IFL<T> curr = this;
        while(curr != null) {
            if(predicate.test(curr.head.get())) {
                return Optional.of(curr.head.get());
            } 
            curr = curr.tail.get();
        }
        return Optional.empty();
        /* FINDMATCH END: DO NOT REMOVE!!! */
    }
}

/* ADDITIONAL CODE START: DO NOT REMOVE */
/* ADDITIONAL CODE END: DO NOT REMOVE */
