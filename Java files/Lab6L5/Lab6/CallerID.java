class CallerID implements Keyable<Integer> {
    int number;
    String name;
    
    CallerID(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public Integer getKey() {
        return this.number;
    }

    @Override
    public String toString() { 
        return this.number + ": " + this.name;
    }
}
