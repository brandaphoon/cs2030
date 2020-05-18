public class X {
    private final int num;    
    X(int num) {
        this.num = num;
    }
    @Override
    public String toString() {
        return "X:" + this.num;
    }
}

public class Y {
    private final X x;
    Y(X x) {
        this.x = x;
    }
    @Override
    public String toString() {
        return "Y->" + this.x;
    }
}
