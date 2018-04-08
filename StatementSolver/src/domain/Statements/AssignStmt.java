package domain.Statements;

import domain.ADT.IDictionary;
import domain.PrgState;
import domain.expressions.Expr;
import exceptions.ExprException;
import exceptions.StmtExecException;

public class AssignStmt implements IStmt
{
    private String var;
    private Expr expr;

    public AssignStmt(String v, Expr e)
    {
        var = v;
        expr = e;
    }

    @Override
    public PrgState execute(PrgState prg) throws StmtExecException
    {
        IDictionary<String, Integer> symTable = prg.getSymTable();
        try{
            int val = expr.evaluate(symTable, prg.getHeap());
            symTable.put(var, val);
        }catch (ExprException e){
            throw new StmtExecException(e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return " " + var + " = " + expr;
    }

}

