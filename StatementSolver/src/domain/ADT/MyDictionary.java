package domain.ADT;

import exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1, T2> implements IDictionary<T1, T2> {
    private HashMap<T1, T2> dictionary;

    public MyDictionary() { dictionary = new HashMap<T1, T2>(); }

    public void put(T1 key, T2 val)
    {
        dictionary.put(key, val);
    }
    public void remove(T1 key) throws ADTException
    {
        if (!findKey(key))
            throw new ADTException("Key doesn't exist");
        dictionary.remove(key);
    }
    public void update(T1 key, T2 val) { dictionary.put(key, val); }
    public boolean findKey(T1 key) { return dictionary.containsKey(key); }
    public T2 getValue(T1 key) { return dictionary.get(key); }
    public Set<T1> getKeys() { return dictionary.keySet(); }
    public Collection<T2> values() {return dictionary.values();}
//    public Set<Map.Entry<T1,T2>> entrySet( return dictionary.entrySet() );

    public HashMap<T1, T2> getContent() {return this.dictionary;}
}
