import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

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

    /**
     * This method is to retrieve the value using a key in the map,
     * however, if the key is not found it will return an empty Optional.
     */

    public Optional<V> get(K key) {
        return Optional.ofNullable(map.get(key));
    }

    public KeyableMap<T,K,V> put(V item) {
        this.map.put(item.getKey(), item);
        return this;
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
