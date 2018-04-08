package domain.Statements;

import domain.ADT.IDictionary;
import domain.ADT.IStack;
import domain.ADT.MyDictionary;
import domain.ADT.MyStack;
import domain.PrgState;
import exceptions.StmtExecException;

public class ForkStmt implements IStmt
{
    private IStmt stmt;
    public ForkStmt(IStmt stmt)
    {
        this.stmt = stmt;
    }
    private static int id = 1;

    public PrgState execute(PrgState p) throws StmtExecException
    {
        IStack<IStmt> newExeStack = new MyStack<>();
        newExeStack.push(stmt);

        IDictionary<String, Integer> cloneSymTbl = new MyDictionary<>();
        for (String key : p.getSymTable().getKeys())
        {
            cloneSymTbl.put(key, p.getSymTable().getValue(key));
        }

        id = id*10;
        return new PrgState(newExeStack, cloneSymTbl, p.getOut(),
                                            p.getFileTable(), p.getHeap(), p.getSemaphoreTable(), id);



    }
    public String toString()
    {
        return "fork( " + stmt + " )";
    }
}
