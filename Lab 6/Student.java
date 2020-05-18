
public class Student extends KeyableMap<String, String, Module> {

    Student(String id) {
        super(id);
    }

    public Student put(Module m) {
        return (Student) super.put(m);
    }


}