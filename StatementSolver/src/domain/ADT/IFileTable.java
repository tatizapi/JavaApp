package domain.ADT;

import exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IFileTable<TKey, TValue> {
    void put(TKey key, TValue value);
    void remove(TKey key) throws ADTException;
    TValue getValue(TKey key) throws ADTException;
    boolean containsKey(TKey key);
    boolean containsValue(TValue value);
    Set<TKey> getKeys();
    HashMap<TKey, TValue> getContent();
    Collection<TValue> getValues();
    String toString();
    void setContent(Map<TKey, TValue> anotherMap);
}
