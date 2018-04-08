package domain.ADT;

import exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyFileTable<TKey, TValue> implements IFileTable<TKey, TValue>
{
    private HashMap<TKey, TValue> dict;

    public MyFileTable() { dict = new HashMap<TKey, TValue>(); }

    public void put(TKey key, TValue val) { dict.put(key, val); }
    public void remove(TKey key) throws ADTException
    {
        if (!dict.containsKey(key))
            throw new ADTException("Key doesn't exist");
        dict.remove(key);
    }
    public TValue getValue(TKey key) throws ADTException
    {
        if (!dict.containsKey(key))
            throw new ADTException("Key doesn't exist");
        return dict.get(key);
    }
    public boolean containsKey(TKey key)
    {
        if(!dict.containsKey(key))
            return false;
        return true;
    }
    public boolean containsValue(TValue val)
    {
        if(!dict.containsValue(val))
            return false;
        return true;
    }
    public String toString()
    {
        String msg = "";
        for(TKey key : dict.keySet())
            msg += key.toString() + " = "+  dict.get(key).toString() + '\n';
        return msg;
    }
    public Set<TKey> getKeys()
    {
        return dict.keySet();
    }

    public HashMap<TKey, TValue> getContent() { return dict; }
    public void setContent(Map<TKey, TValue> anotherMap) { this.dict = new HashMap<>(anotherMap); }

    public Collection<TValue> getValues()
    {
        return dict.values();
    }

}
