package domain.ADT;

import exceptions.ADTException;

import java.util.Set;

public interface ISemaphoreTable<TKey, TVal>
{
    void put(TKey key, TVal value);
    void remove(TKey key) throws ADTException;
    boolean containsKey(TKey key);
    Set<TKey> getKeys();
    TVal getValue(TKey key);
}
