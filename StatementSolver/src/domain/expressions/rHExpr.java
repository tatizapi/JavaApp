package domain.expressions;


import domain.ADT.IDictionary;
import domain.ADT.IHeap;
import exceptions.ExprException;

public class rHExpr extends Expr {
    private String varName;

    public rHExpr(String varName)
    {
        this.varName = varName;
    }

    public int evaluate(IDictionary<String,Integer> symTable, IHeap<Integer,Integer> heap)  throws ExprException
    {
        if (!symTable.findKey(varName))
            throw new ExprException("Variable " + varName + " not found");

        int address = symTable.getValue(varName);
        if (!heap.findKey(address))
            throw new ExprException("Address " + Integer.toString(address) + " not found");

        return heap.getValue(address);
    }

    public String toString()
    {
        return "rH(" + varName + ')';
    }
}
