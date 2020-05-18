public enum Status {
    ARRIVES(1), 
    SERVED(2),
    LEAVES(3),
    DONE(4),
    WAITS(5);

    private final int statusCode;

    /**
     * Creates a new status.
     */

    Status(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
