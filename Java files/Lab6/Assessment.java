public class Assessment implements Keyable<String> {
    private final String name;
    private final String grade;

    Assessment(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String getKey() {
        return this.name;
    }

    public String getGrade() {
        return this.grade;
    }

    @Override
    public String toString() {
        return "{" + this.name + ": " + this.grade + "}";
    }
}
