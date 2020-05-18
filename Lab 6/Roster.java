
public class Roster extends KeyableMap<String, String, Student>{

    Roster(String id){
        super(id);
    }

    public Roster put(Student s) {
        return (Roster) super.put(s);
    }

    public String getGrade(String id, String code, String name) throws NoSuchRecordException {
        return this.get(id)
        .flatMap(i -> i.get(code))
        .flatMap(x -> x.get(name))
        .map(a -> a.getGrade())
        .orElseThrow(() -> new NoSuchRecordException("No such record: " + id + " " + code + " " + name));
    }

}