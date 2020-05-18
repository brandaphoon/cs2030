
public class Module extends KeyableMap<String, String, Assessment> {

    Module(String id) {
        super(id);
    }

    public Module put(Assessment ass) {
        return (Module) super.put(ass);
    }

}