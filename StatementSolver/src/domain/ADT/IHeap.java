package domain.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public interface IHeap<TKey, TValue>
{
    void put(TKey k, TValue v);
    TValue getValue(TKey k);
    Set<TKey> getKeys();
    int getSize();
    boolean isEmpty();
    boolean findKey(TKey key);
    void update(TKey key, TValue val);
    HashMap<TKey, TValue> getContent();
    void setContent(Map<TKey, TValue> anotherMap);
}
