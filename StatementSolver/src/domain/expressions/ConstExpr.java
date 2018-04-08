package domain.expressions;

import domain.ADT.IDictionary;
import domain.ADT.IHeap;
import exceptions.ExprException;

public class ConstExpr extends Expr {
    private int value;

    public ConstExpr(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(IDictionary<String,Integer> symTable, IHeap<Integer,Integer> heap)  throws ExprException {
        return value;
    }

    @Override
    public String toString() { return "" + value; }
}