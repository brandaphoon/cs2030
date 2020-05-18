import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.Comparator;

public class ImmutableList<T> {

    private final List<T> list;

    @SuppressWarnings("unchecked")
    ImmutableList(T... values) {
        List<T> l = new ArrayList<>();
        for(T v: values) {
            l.add(v);
        }
        this.list = l;
    }

    ImmutableList(List<T> list){
        List<T> l = new ArrayList<>();
        for (T v: list) {
            l.add(v);
        }

        this.list = l;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    public ImmutableList<T> add(T v) {
        List<T> newList = new ArrayList<>(list);
        //creating a newlist, so that we don't modify the original list
        newList.add(v);
        return new ImmutableList<T>(newList);
    }

    public ImmutableList<T> remove(T v) {
        List<T> newList = new ArrayList<>(list);
        newList.remove(v);
        return new ImmutableList<T>(newList);
    }

    public ImmutableList<T> replace(T v1, T v2) {
        List<T> newList = new ArrayList<>(list);
        Collections.replaceAll(newList, v1, v2);
        return new ImmutableList<T>(newList);
    }

    public ImmutableList<T> filter(Predicate<? super T> f){
        List<T> newList = new ArrayList<>();
        for (T each: list) {
            if (f.test(each)) {
                newList.add(each);
            }
        }
        return new ImmutableList<T>(newList);
    }

    public <R> ImmutableList<R> map(Function<? super T, ? extends R> f){
        List<R> newList = new ArrayList<>();
        for (T each: list) {
            newList.add(f.apply(each));
        }
        return new ImmutableList<R>(newList);
    }

    public ImmutableList<T> limit(int n) {
        List<T> newList = new ArrayList<>();
        if (n < 0) {
            throw new IllegalArgumentException("Limit size < 0");
        } else if (n > list.size()) {
            newList.addAll(list);
        } else {        
            for (int i = 0; i < n; i++) {
                newList.add(list.get(i));
            }
        }
        return new ImmutableList<T>(newList);
    }

    public ImmutableList<T> sorted() {
        List<T> newList = new ArrayList<T>(this.list);
        if (this.list.size() == 1) {
            return new ImmutableList<>(newList);
        }

        T ele = newList.get(0);

        if(ele instanceof Comparable) {
            T[] arr = (T) newList.toArray();

        } else {
            throw IllegalStateException("List elements do not implement comparable");
        }

    }

}