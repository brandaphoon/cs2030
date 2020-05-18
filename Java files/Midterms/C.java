public class C implements basicInterface {
    private final String s;

    C(String s) {
        this.s = s;
    }

    C() {
        this.s = "C";
    }

    public String getValue() {
        return this.s;
    }

    public <T> add(basicInterface<? extends T> o) {
        if (o == null) {
            return this;
        } else {
            return new C();
    }

    @Override
    public String toString() {
        return this.s;
    }

}
