import java.util.List;
import java.util.ArrayList;
interface TypeCaster<S,T> {
    public T cast(S s);
}
class ToString<S> implements TypeCaster<S,String> {
    public String cast(S s) {
        return s.toString();
    }
}
class Round implements TypeCaster<Double,Integer> {
    //private final int xl;
    public Integer cast(Double x) {
        return (int) Math.round(x);
    }
}
class ToList<T> implements TypeCaster<T[], List<T>> {
    public List<T> cast(T[] tList) {
        List<T> list = new ArrayList<>();
        for (T each: tList) {
            list.add(each);
        }
        return list;
    }
}
class ListCaster<S,T> {
   static <S,T> List<T> castList(List<S> s, TypeCaster<S,T> tc) {
       List<T> finalL = new ArrayList<>(); 
       for (S each: s) {
           finalL.add(tc.cast(each));
       }
       return finalL;
   }
}
