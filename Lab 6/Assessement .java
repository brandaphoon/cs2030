class Assessment implements Keyable<String>  {
    String name;
    String grade;

    Assessment(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getKey() {
        return this.name;
    }

    public String getGrade() {
        return this.grade;
    }

    @Override
    public String toString() {
        return "{" + this.name + ", " + this.grade + "}";
    }
}