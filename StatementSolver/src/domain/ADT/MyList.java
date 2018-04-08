package domain.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T>
{
    private List<T> list;

    public MyList() { list = new ArrayList<T>(); }

    public void add(T val)
    {
        list.add(val);
    }
    public void remove(T val) { list.remove(val); }
    public List<T> getList() {return list;}
    public T getElem(int pos) {return list.get(pos);}

}
