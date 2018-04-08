package domain.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<TKey, TValue> implements IHeap<TKey, TValue>
{
    private HashMap<TKey, TValue> heap;
    public MyHeap() { heap = new HashMap<TKey, TValue>(); }

    public void put(TKey k, TValue v) { heap.put(k, v); }
    public TValue getValue(TKey k) { return heap.get(k); }
    public Set<TKey> getKeys() { return heap.keySet(); }
    public int getSize() { return heap.size(); }
    public boolean isEmpty() {return heap.isEmpty(); }
    public boolean findKey(TKey key) { return heap.containsKey(key); }
    public void update(TKey key, TValue val) { heap.put(key, val); }

    public HashMap<TKey, TValue> getContent() { return heap; }
    public void setContent(Map<TKey, TValue> anotherMap) { this.heap = new HashMap<>(anotherMap); }

}
