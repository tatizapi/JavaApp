package domain.Statements;

import domain.ADT.IList;
import domain.PrgState;
import domain.expressions.Expr;
import exceptions.ExprException;
import exceptions.StmtExecException;

public class PrintStmt implements IStmt
{
    private Expr expr;
    public PrintStmt(Expr e)
    {
        expr = e;
    }

    public PrgState execute(PrgState prg) throws StmtExecException
    {
        IList<Integer> out = prg.getOut();
        try{
            out.add(expr.evaluate(prg.getSymTable(), prg.getHeap()));
        }catch (ExprException e){
            throw new StmtExecException(e.getMessage());
        }

        return null;
    }
    public String toString(){ return "print(" + expr.toString() + ")"; }

}
