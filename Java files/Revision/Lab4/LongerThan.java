public class LongerThan implements BooleanCondition<String> {
    private final int limit;

    LongerThan(int limit) {
        this.limit = limit;
    }

    public boolean test(String s) {
        if (s.length() > limit) {
            return true;
        } else {
            return false;
        }
    }
}

