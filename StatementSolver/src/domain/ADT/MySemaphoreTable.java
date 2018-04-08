package domain.ADT;

import exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MySemaphoreTable<TKey, TVal> implements ISemaphoreTable<TKey, TVal>
{
    private Map<TKey, TVal> dict;

    public MySemaphoreTable() { dict = new HashMap<>(); }

    public void put(TKey key, TVal value) { dict.put(key, value); }
    public void remove(TKey key) throws ADTException
    {
        if (!dict.containsKey(key))
        {
            throw new ADTException("Key" + key + "doesn't exist in the Semaphore Table");
        }
        else
        {
            dict.remove(key);
        }
    }
    public boolean containsKey(TKey key) { return dict.containsKey(key); }
    public Set<TKey> getKeys() { return dict.keySet(); }
    public TVal getValue(TKey key) { return dict.get(key); }
}
