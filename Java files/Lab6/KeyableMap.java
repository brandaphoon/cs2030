import java.util.Map;
import java.util.HashMap;

public class KeyableMap<T,K,V extends Keyable<K>> {
    protected T id;
    protected Map<K,V> map;

    public KeyableMap(T id) {
        this.id = id;
        this.map = new HashMap<>();
    }
    
    public T getKey() {
        return this.id;
    }

    public V get(K key) {
        return this.map.get(key);
    }

    public KeyableMap<T,K,V> put(V item) {
        this.map.put(item.getKey(), item);
        return this;
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public String toString() {
        String f = "";
        for (Map.Entry<K,V> e: this.map.entrySet()) {
            if (f == "") {
                f += e.getValue();
            } else {
                f += ", " + e.getValue();
            }
        }
        return this.id + ": {" + f + "}";
        
    }
}
