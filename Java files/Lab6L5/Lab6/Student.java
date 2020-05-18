public class Student extends KeyableMap<String, String, Module> implements 
    Keyable<String> {
    
    Student(String id) {
        super(id);
    }

    @Override
    public Student put(Module m) {
        return (Student) super.put(m);
    }

}
