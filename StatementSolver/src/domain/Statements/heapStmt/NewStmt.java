package domain.Statements.heapStmt;

import domain.ADT.IDictionary;
import domain.PrgState;
import domain.Statements.IStmt;
import domain.expressions.Expr;
import exceptions.ExprException;
import exceptions.StmtExecException;

public class NewStmt implements IStmt {
    private String varName;
    private Expr exp;
    private static int address = 1;

    public NewStmt(String varName, Expr exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    public PrgState execute(PrgState p) throws StmtExecException
    {

        try{
            int v = exp.evaluate(p.getSymTable(), p.getHeap());

            p.getHeap().put(address, v);

            IDictionary<String, Integer> symTbl = p.getSymTable();
            if (symTbl.findKey(varName))
                symTbl.update(varName, address);
            else
                symTbl.put(varName, address);

            address = address + 1;


        }catch(ExprException e){
            throw new StmtExecException(e.getMessage());
        }



        return null;
    }

    public String toString()
    {
        return "new(" + varName + ", " + exp + ')';
    }
}
