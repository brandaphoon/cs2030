public class Module extends KeyableMap<String, String, Assessment> implements
    Keyable<String> {
    
    Module(String code) {
        super(code);
    }

    @Override
    public Module put(Assessment a) {
        return (Module) super.put(a);
    }
}
