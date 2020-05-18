import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.Arrays;

abstract class InfiniteListImpl<T> implements InfiniteList<T> {
    
    static <T> InfiniteListImpl<T> generate(Supplier<? extends T> supplier) {
        return new InfiniteListImpl<T>() {
            public Optional<T> get() {
                return Optional.of(supplier.get());
            }
        };
    }

    static <T> InfiniteListImpl<T> iterate(T seed, UnaryOperator<T> next) {
        return new InfiniteListImpl<T>() {
            private T ele = seed;
            private boolean firstele = true;
            public Optional<T> get() {
                if (firstele) {
                    // This is so that we start from the second ele
                    firstele = false;
                } else {
                    ele = next.apply(ele);
                }
                return Optional.of(ele);
            }
        };
    }

    public void forEach(Consumer<? super T> action) {
        Optional<T> curr = InfiniteListImpl.this.get();
        
        while (true) {
            
            if (curr.isEmpty()) {
                //Once the the list is empty, it will stop running
                break;
            }
            
            //Applying the action on the element
            action.accept(curr.get());
            
            //Get the next ele
            curr = InfiniteListImpl.this.get();
        }
    }

    public InfiniteListImpl<T> limit(long maxSize) {
        if (maxSize < 0) {
            //If it is negative, it will throw an error
            throw new IllegalArgumentException(Long.toString(maxSize));
        }

        return new InfiniteListImpl<T>() {
            private long count = maxSize;
            //Keeping track of the number to add
            public Optional<T> get() {
                if (count > 0) {
                    count--;
                    // returning the next ele
                    return InfiniteListImpl.this.get();
                } else {
                    //End of the list, it will return this
                    return Optional.empty();
                }
            } 
        };
    }

    
    public Object[] toArray() {
        Optional<T> curr = InfiniteListImpl.this.get();
        ArrayList<Object> list = new ArrayList<Object>();
      
        while (curr.isPresent()) {
            //If ele is present, we add it to the list
            list.add(curr.get()); 
            //Get the next ele
            curr = InfiniteListImpl.this.get();
        }

        //Convert to arrays
        return list.toArray();
        
    }
    

    public <S> InfiniteListImpl<S> map(Function<? super T, ? extends S> mapper) {
        return new InfiniteListImpl<S>() {
            public Optional<S> get() { 
                Optional<T> curr = InfiniteListImpl.this.get();
                if (curr.isEmpty()) {
                    return Optional.empty();
                }
                //Apply the map then return
                return Optional.of(mapper.apply(curr.get()));
            }
        };  
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        return new InfiniteListImpl<T>() {
            public Optional<T> get() {
                Optional<T> curr = InfiniteListImpl.this.get();
                
                while (curr.isPresent()) {
                   
                    while (curr.isPresent() && curr.filter(predicate).isEmpty()) {
                        //Get the next ele, since it the values would return
                        // empty
                        curr = InfiniteListImpl.this.get();  
                    }
                    
                    //Filter the ele accordingly
                    return curr.filter(predicate);
                }
                return curr;
            }
        };
    }

    public InfiniteListImpl<T> takeWhile(Predicate<? super T> predicate) {
        //This is like filter but with a termination operator - returning optional.empty()
        return new InfiniteListImpl<T>() {
            public Optional<T> get() {
                Optional<T> curr = InfiniteListImpl.this.get();
                //Checking if its present else return terminal operator
                if (curr.filter(predicate).isPresent()) {
                    return curr.filter(predicate);
                }
                return Optional.empty();
            }
        };
    }

    public long count() {
        Optional<T> curr = InfiniteListImpl.this.get();
        long n = 0;
        while (curr.isPresent()) {
            n++;
            curr = InfiniteListImpl.this.get();
            //Counting through each ele
        }

        return n;
    }

 
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        Optional<T> curr = InfiniteListImpl.this.get();
        Optional<T> v = Optional.empty();

        while (curr.isPresent()) {
            if (v.isPresent()) { 
                //if present, then apply on the next one with the current v
                v = Optional.of(accumulator.apply(v.get(), curr.get()));
            } else {
                // else v = curr
                v = curr;
            }
            
            //Getting the next ele
            curr = InfiniteListImpl.this.get();
        }
        return v;
    } 

    public T reduce(T identity, BinaryOperator<T> accumulator) {
        T v = identity;
        //The starting pt will be with the identity
        Optional<T> curr = InfiniteListImpl.this.get();

        while (curr.isPresent()) {
            //check ele is not empty than apply
            v = accumulator.apply(v, curr.get());
            //get the next ele
            curr = InfiniteListImpl.this.get();
        }
        return v;
    }
    
}
