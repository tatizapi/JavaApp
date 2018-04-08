package domain.ADT;

import java.util.List;

public interface IList<T>
{
    void add(T val);
    void remove(T val);
    T getElem(int pos);
    List<T> getList();
}