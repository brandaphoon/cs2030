import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.Collections;
import java.util.Comparator;

public class ImmutableList<T> {

    private final List<T> list;

    /**
     * Create an immutableList that takes inputs non-list inputs.
     */

    @SafeVarargs
    public ImmutableList(T... list) {
        
        List<T> finalList = new ArrayList<>();

        for (T each: list) {
            finalList.add(each);
        }

        this.list = finalList;
    }

    /**
     * Create an immutableList that takes a list as input.
     */

    public ImmutableList(List<T> list) {

        List<T> finalList = new ArrayList<>();

        for (T each: list) {
            finalList.add(each);
        }

        this.list = finalList;
    }

    /**
     * Add a new element to the exiting immutableList and return it as a new
     * one.
     */

    public ImmutableList<T> add(T t) {

        List<T> newList = new ArrayList<T>(this.list);
        newList.add(t);
        return new ImmutableList<T>(newList);

    }

    /**
     * Removes a element in the existing immutableList and return it as a new
     * one.
     */

    public ImmutableList<T> remove(T t) {

        List<T> newList = new ArrayList<T>(this.list);
        newList.remove(t);
        return new ImmutableList<T>(newList);
    }

    /**
     * Replaces all elements based on input in the existing immutableList 
     * with another and return it as a new one.
     */

    public ImmutableList<T> replace(T t1, T t2) {

        ArrayList<T> newList = new ArrayList<T>(this.list);
        Collections.replaceAll(newList, t1, t2);
        List<T> finalList = new ArrayList<T>(newList);
        return new ImmutableList<T>(finalList);
    }
    
    /**
     * Filter each element in the existing immutableList using a predicate as 
     * an input and return elements that passed into a new immutableList.
     */

    public ImmutableList<T> filter(Predicate<? super T> pred) {
        
        List<T> newList = new ArrayList<T>();
        for (T each : this.list) {
            if (pred.test(each)) {
                newList.add(each);
            }
        }
        return new ImmutableList<T>(newList);
    }

    /**
     * Map each element in the existing immutableList with a function applied 
     * as an input and return elements that passed into a new immutableList.
     */

    public <R> ImmutableList<R> map(Function<? super T, ? extends R> func) {
        
        List<R> newList = new ArrayList<R>();
        for (T each: this.list) {
            newList.add(func.apply(each));
        }
        return new ImmutableList<R>(newList);
    }
    
    /**
     * Truncate the existing immutableList to the given size as an input 
     * and return as a new immutableList.
     */

    public ImmutableList<T> limit(long n) {
        if (n < 0) {
            throw new IllegalArgumentException("limit size < 0");
        } else {
            List<T> newList = new ArrayList<T>(this.list);
            if (n < newList.size()) {
                return new ImmutableList<T>(newList.subList(0, (int) n));
            } else {
                return new ImmutableList<T>(newList);
            }
        }
    }
   
    /**
     * Sort the existing immutableList according to precondtions with elements
     * that implements comparable only and return them as a new immutableList.
     */

    public ImmutableList<T> sorted() {
        if (this.list.size() < 1) {
            List<T> newList = new ArrayList<T>(this.list);
            return new ImmutableList<T>(newList);
        }

        T ele = this.list.get(0);
        
        if (ele instanceof Comparable) {
            
            @SuppressWarnings("unchecked")
            T[] array = (T[]) this.list.toArray();
            Arrays.sort(array);
            List<T> newList = new ArrayList<T>(Arrays.asList(array));
            return new ImmutableList<T>(newList);
            
        } else {
            throw new IllegalStateException("List elements do not implement" +
                    " Comparable");
        }
    

    }

    /**
     * Sort the existing immutableList according to precondtions set by the 
     * input comparator and return them as a new immutableList.
     */

    public ImmutableList<T> sorted(Comparator<T> comp) {
        if (comp == null) {
            throw new NullPointerException("Comparator is null");
        } else {
            
            ArrayList<T> newList = new ArrayList<T>(this.list);
            Collections.sort(newList, comp);
            return new ImmutableList<T>(newList);
        } 
    
    }

    public Object[] toArray() {
        Object[] array = (Object[]) this.list.toArray();
        return array;
    }

    
    /**
     * Convert the existing immutableList to an array given a generic type as
     * an input and return elements into a new immutableList.
     */

    public <U> U[] toArray(U[] array) {
        
        if (array == null) {
            throw new NullPointerException("Input array cannot be null");
        } else {
            try {   
                return this.list.toArray((U[]) array);
            } catch (ArrayStoreException e) {
                throw new ArrayStoreException("Cannot add element to array " + 
                        "as it is the wrong type");
            }
        }
    }

    @Override
    public String toString() {
        return (this.list).toString();
    }

}
