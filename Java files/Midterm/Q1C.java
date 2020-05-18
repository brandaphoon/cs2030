public class Basics{
    String s;
    Basics(String s) {
        this.s = s;
    }
    Basics add(Basics t) {
        s += t;
        return this;
    }
}
public class B extends Basics {
    private static final String b = "B";
    B() {
        super(b);
    }
    B ex(String c) {
        this.s = (String) c;
        return this;
    }
    public String getValue() {
        return this.b;
    }
    public Basics ad(Basics c) {
        String newv = c + this.getValue();
        return new Basics(newv);
    }
    @Override
    public String toString() {
        return this.s;
    }
}
public class C extends Basics {
    private final static String c = "C";
    C() {
        super(c);
    }
    @Override
    public String toString() {
        return this.s;
    }
}
