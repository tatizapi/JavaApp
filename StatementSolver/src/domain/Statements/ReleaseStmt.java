package domain.Statements;

import domain.ADT.IDictionary;
import domain.ADT.ISemaphoreTable;
import domain.PrgState;
import exceptions.StmtExecException;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt
{
    private String var;
    private Lock lock;

    public ReleaseStmt(String _var)
    {
        var = _var;
        lock = new ReentrantLock();
    }
    public PrgState execute(PrgState prg) throws StmtExecException
    {
        IDictionary<String, Integer> symTbl = prg.getSymTable();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semTable = prg.getSemaphoreTable();
        int idx;

        if (!symTbl.findKey(var))
        {
            throw new StmtExecException("Key " + var + " is not in the Symbol Table");
        }
        else
        {
            idx = symTbl.getValue(var);
        }

        Pair<Integer, List<Integer>> scndPart;
        List<Integer> l;
        int n1, nl;
        if(!semTable.containsKey(idx))
        {
            throw new StmtExecException("Key" + var + " is not in the Semaphore Table");
        }
        else {
            lock.lock();
            scndPart = semTable.getValue(idx);
            lock.unlock();

            n1 = scndPart.getKey();
            l = scndPart.getValue();
            nl = scndPart.getValue().size();

            if (l.contains(prg.getId()))
            {
                lock.lock();
                prg.getSemaphoreTable().getValue(idx).getValue().remove(0);
                lock.unlock();
            }
        }

        return null;

    }
    public String toString()
    {
        return "release(" + var + ")";
    }
}
