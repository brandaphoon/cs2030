public class DivisibleBy implements BooleanCondition<Integer> {
    private final int x;
    
    DivisibleBy(int x) {
        this.x = x;
    }

    public boolean test(Integer t) {
        if (t % x == 0) {
            return true;
        } else {
            return false;
        }
    }
}
