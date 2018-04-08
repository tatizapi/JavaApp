package domain.ADT;

import exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IDictionary<T1, T2>
{
    void put(T1 key, T2 val);
    void remove(T1 key) throws ADTException;
    boolean findKey(T1 key);
    void update(T1 key, T2 val);
    T2 getValue(T1 key);
    Set<T1> getKeys();

    Collection<T2> values();
    HashMap<T1, T2> getContent();
   // Set<Map.Entry<T1,T2>> entrySet();

}