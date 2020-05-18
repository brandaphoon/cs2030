import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;




class q13 {

     static <T> Predicate<T> and(Predicate<T> p1, Predicate<T> p2) {
        return i -> (p1.test(i) && p2.test(i));
    }



}


class LazyInt {
    Supplier<Integer> supp;

    LazyInt(Supplier<Integer> supp) {
        this.supp = supp;
    }

    int get() {
        return this.supp.get();
    }

    LazyInt map(Function<? super Integer, Integer> mapper) {
        return new LazyInt(() -> mapper.apply(this.get()));
    }

    LazyInt flatMap(Function<? super Integer, LazyInt> mapper) {
        return new LazyInt(() -> mapper.apply(this.get()).get());
    }
    //removes the boxes that the item is encapsulated in
}