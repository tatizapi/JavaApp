package domain.Statements;

import domain.ADT.IStack;
import domain.PrgState;
import domain.expressions.Expr;
import exceptions.ExprException;
import exceptions.StmtExecException;

public class IfStmt implements IStmt
{
    private Expr expr;
    private IStmt thenStmt;
    private IStmt elseStmt;

    public IfStmt(Expr e, IStmt t, IStmt el)
    {
        expr = e;
        thenStmt = t;
        elseStmt = el;
    }

    public PrgState execute(PrgState prg) throws StmtExecException
    {
        IStack<IStmt> stk = prg.getExeStack();
        try{
            if (expr.evaluate(prg.getSymTable(), prg.getHeap()) != 0)
                stk.push(thenStmt);
            else
                stk.push(elseStmt);

            return null;
        }catch (ExprException e){
            throw new StmtExecException(e.getMessage()); }

    }

    public String toString()
    { return "IF(" + expr.toString()+ ") THEN(" + thenStmt.toString() + ")ELSE(" + elseStmt.toString()+ ")";}
}
