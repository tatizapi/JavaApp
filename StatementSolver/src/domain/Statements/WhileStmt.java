package domain.Statements;

import domain.ADT.IStack;
import domain.PrgState;
import domain.expressions.Expr;
import exceptions.ExprException;
import exceptions.StmtExecException;

public class WhileStmt implements IStmt
{
    private Expr expr;
    private IStmt stmt;

    public WhileStmt(Expr expr, IStmt stmt)
    {
        this.expr = expr;
        this.stmt = stmt;
    }

    public PrgState execute(PrgState prg) throws StmtExecException {
        IStack<IStmt> stk = prg.getExeStack();
        try{
            if (expr.evaluate(prg.getSymTable(), prg.getHeap()) != 0) {
                stk.push(this);
                stk.push(stmt);
            }
        }catch(ExprException e){
            throw new StmtExecException(e.getMessage());
        }


        return null;
    }

    public String toString()
    {
        return "while (" + expr + ") " + stmt.toString();
    }
}
