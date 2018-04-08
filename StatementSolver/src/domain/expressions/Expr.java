package domain.expressions;

import domain.ADT.IDictionary;
import domain.ADT.IHeap;
import exceptions.ADTException;
import exceptions.ExprException;


public abstract class Expr {
    public abstract int evaluate(IDictionary<String,Integer> symTable, IHeap<Integer,Integer> heap) throws ExprException;
    abstract public String toString();
}
