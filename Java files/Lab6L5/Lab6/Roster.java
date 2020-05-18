public class Roster extends KeyableMap<String, String, Student> {

    Roster(String id) {
        super(id);
    }

    @Override
    public Roster put(Student s) {
        return (Roster) super.put(s);
    }

    /**
     * This is a method to retrieve a grade from a student for a particular
     * module and assessment. If not found, it would throw a custom exception
     * to show that there is no such record found.
     */

    public String getGrade(String id, String code, String name) throws 
        NoSuchRecordException {
        return this.get(id).flatMap(s -> s.get(code)).flatMap(m -> 
                m.get(name)).map(a -> a.getGrade()).orElseThrow(() -> 
                new NoSuchRecordException("No such record: " + id + " " +
                    code + " " + name));
    }
             
}

