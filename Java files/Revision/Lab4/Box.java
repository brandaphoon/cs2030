public class Box<T> {
    private final T t;
    private static Box<?> EMPTY_BOX = new Box<>(null);
    
    Box(T t) {
        this.t = t;
    }

   @SuppressWarnings("unchecked")
    static <T> Box<T> of(T t) {
        if ( t == null ) {
            return null;
        } 
        return new Box<T>(t);
    }

    public T getT() {
        return this.t;
    }

    static <T> Box<T> empty() {
        return (Box<T>) EMPTY_BOX;
    }

    static <T> Box<T> ofNullable(T t) {
        if ( t == null ) {
            return empty();
        } else {
            return new Box<T>(t);
        }
    }

    public boolean isPresent() {
        if (this == EMPTY_BOX) {
            return false;
        } else {
            return true;
        }
    }

    // We use super T because, we want to accept types of Number when let say
    // BooleanCondition<Integer> is implemented, it still makes sense that 
    // Number type values can be use to test.
    public Box<T> filter(BooleanCondition<? super T> cond) {
        if ( t == null) {
            return empty();
        }
        if (cond.test(t)) {
            return this;
        } else {
            return empty();
        }
    }

    // It is extend U because you want the new box to be U so any subclass
    // of U would be accepted, however if you use super which makes it more
    // general you cannot be sure that it will be a U.
    public <U> Box<U> map(Transformer<? super T, ? extends U> trans) {
        if (this.t == null) {
            return empty();
        }

        return Box.ofNullable(trans.transform(this.t));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Box) {
            Box<?> o = (Box<?>) obj;
            if (o.getT() == null | this.getT() == null) {
                return false;
            }
            return o.getT().equals(this.getT());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this != EMPTY_BOX) {
            return "[" + this.t + "]";
        } else {
            return "[]";
        }
    }
}
