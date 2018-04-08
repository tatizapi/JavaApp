package domain.Statements;

import domain.PrgState;
import domain.expressions.Expr;
import exceptions.ExprException;
import exceptions.StmtExecException;

public class CondStmt implements IStmt
{
    private String var;
    private Expr condExpr;
    private Expr expr1;
    private Expr expr2;
    /*
    - pop the statement
- create the following statement:
if (exp1) then v=exp2 else v=exp3
- push the new statement on the stack

    c=a?100:200;
     */

    public CondStmt(String _var, Expr _cnd, Expr e1, Expr e2)
    {
        var = _var;
        condExpr = _cnd;
        expr1 = e1;
        expr2 = e2;
    }

    public PrgState execute(PrgState prg) throws StmtExecException
    {
        int res;
        try{
            res = condExpr.evaluate(prg.getSymTable(), prg.getHeap());
        }catch (ExprException ex)
        {
            throw new StmtExecException(ex.getMessage());
        }

        IStmt stmt;
        if (res > 0)
        {
            stmt = new AssignStmt(var, expr1);
        }
        else
        {
            stmt = new AssignStmt(var, expr2);
        }
        prg.getExeStack().push(stmt);

        return null;
    }

    public String toString()
    {
        return var + "=" + condExpr + "?" + expr1 + ":" + expr2;
    }
}
