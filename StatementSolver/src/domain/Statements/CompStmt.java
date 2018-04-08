package domain.Statements;

import domain.ADT.IStack;
import domain.PrgState;
import exceptions.StmtExecException;

public class CompStmt implements IStmt
{
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt f, IStmt s)
    {
        first = f;
        second = s;
    }

    @Override
    public PrgState execute(PrgState prg) throws StmtExecException
    {
        IStack<IStmt> stk = prg.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public String toString() {
        return "{" + first + ";" + second + "}";
    }
}