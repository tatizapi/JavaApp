package domain.expressions;

import domain.ADT.IDictionary;
import domain.ADT.IHeap;
import exceptions.ExprException;

public class VarExpr extends Expr {
    private String name;

    public VarExpr(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(IDictionary<String,Integer> symTable, IHeap<Integer,Integer> heap)  throws ExprException {
        if(symTable.findKey(name)) {
            return symTable.getValue(name);
        } else {
            throw new ExprException("Variable not found");
        }
    }

    @Override
    public String toString() { return name; }
}