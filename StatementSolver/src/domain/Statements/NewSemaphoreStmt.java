package domain.Statements;

import domain.ADT.IDictionary;
import domain.ADT.ISemaphoreTable;
import domain.PrgState;
import domain.expressions.Expr;
import exceptions.ADTException;
import exceptions.ExprException;
import exceptions.StmtExecException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStmt implements IStmt
{
    private String var;
    private Expr expr;
    Lock lock;
    private static int freeLocation = 1;

    public NewSemaphoreStmt(String _var, Expr _expr)
    {
        var = _var;
        expr = _expr;
        lock = new ReentrantLock();
    }

    /*
    newSemaphore(var,exp)
    SemaphoreTable2 = SemaphoreTable1 synchronizedUnion {newfreelocation ->(number,empty list)}
    if var exists in SymTable1 then
        SymTable2 = update(SymTable1,var, newfreelocation)
        else SymTable2 = add(SymTable1,var, newfreelocation)
     */
    public PrgState execute(PrgState prg) throws StmtExecException
    {
        IDictionary<String, Integer> symTbl = prg.getSymTable();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> smphTbl = prg.getSemaphoreTable();
        int nr;

        try
        {
            nr = expr.evaluate(prg.getSymTable(), prg.getHeap());
        }catch (ExprException ex)
        {
            throw new StmtExecException(ex.getMessage());
        }

        if(symTbl.findKey(var))
        {
            symTbl.update(var, freeLocation);
        }
        else
        {
            symTbl.put(var, freeLocation);
        }

        List<Integer> emptyList = new ArrayList<>();
        lock.lock();
            smphTbl.put(freeLocation, new Pair<Integer, List<Integer>>(nr, emptyList));
            freeLocation = freeLocation + 1;
        lock.unlock();

        return null;

    }
    public String toString()
    {
        return "newSemaphore(" + var + ", " + expr + ")";
    }
}
