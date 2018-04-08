package domain.Statements.heapStmt;


import domain.PrgState;
import domain.Statements.IStmt;
import domain.expressions.Expr;
import exceptions.ExprException;
import exceptions.StmtExecException;

public class wHStmt implements IStmt
{
    private String varName;
    private Expr exp;

    public wHStmt(String varName, Expr exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    public PrgState execute(PrgState p) throws StmtExecException
    {
        try{
            int v = exp.evaluate(p.getSymTable(), p.getHeap());

            int address = p.getSymTable().getValue(varName);
            if (!p.getHeap().findKey(address))
                throw new StmtExecException("Address " + Integer.toString(address) + " not found");

            p.getHeap().update(address, v);

        }catch(ExprException e){
            throw new StmtExecException(e.getMessage());
        }

        return null;
    }

    public String toString()
    {
        return "wH(" + varName + ", " + exp + ')';
    }
}
