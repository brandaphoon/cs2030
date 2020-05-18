import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class KeyableMap<T,K,V extends Keyable<K>> implements Keyable<T> {

    T key;
    Map<K,V> map;

    KeyableMap(T key) {
        this.key = key;
        this.map = new HashMap<>();
    }

    public Optional<V> get(K key) {
        if(map.get(key) == null) {
            return Optional.empty();
        }

        return Optional.of(map.get(key));
    }

    @Override
    public T getKey() {
        return this.key;
    }

    public KeyableMap<T,K,V> put(V item){
        
        map.put(item.getKey(), item);
        return this;
    }

    /*@Override
    public String toString() {
        String str = this.key + ": {";
        for (Map.Entry<K,V> e: map.entrySet()){
            str += e.getValue() + ", ";
        }
        str += str.substring(0, str.length()-2) + "}";
        return str;
    }*/

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
        return this.key + ": {" + f + "}";
        
    }


}