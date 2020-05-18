import java.util.ArrayList;
import java.util.List;

class D {

    static <T> List<T> add(List<T> list, T t) {
        List<T> newList = new ArrayList<>(list);
        newList.add(t);
        return newList;
    }

    static <T> List<T> join(List<T> a, List<? extends T> b) {
        List<T> newList = new ArrayList<>(a);
        if (!(a.equals(b))) {
            newList.addAll(b);
        }
        return newList;
    }
}
