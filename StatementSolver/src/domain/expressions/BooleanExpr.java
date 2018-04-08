package domain.expressions;

import domain.ADT.IDictionary;
import domain.ADT.IHeap;
import exceptions.ADTException;
import exceptions.ExprException;

public class BooleanExpr extends Expr
{
    private Expr left;
    private Expr right;
    private String operator;

    public BooleanExpr(String operator, Expr left, Expr right)
    {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public int evaluate(IDictionary<String,Integer> symTable, IHeap<Integer,Integer> heap)  throws ExprException
    {
        int resultFirst = left.evaluate(symTable, heap);
        int resultSecond = right.evaluate(symTable, heap);

        switch (operator)
        {
            case "<":
                return (resultFirst < resultSecond) ? 1 : 0;
            case ">":
                return (resultFirst > resultSecond) ? 1 : 0;
            case "==":
                return (resultFirst == resultSecond) ? 1 : 0;
            case "!=":
                return (resultFirst != resultSecond) ? 1 : 0;
            case "<=":
                return (resultFirst <= resultSecond) ? 1 : 0;
            case ">=":
                return (resultFirst >= resultSecond) ? 1 : 0;
            default:
                throw new ExprException("Invalid operator");

        }
    }

    public String toString()
    {
        return '(' + left.toString() + operator + right.toString() + ')';
    }

}
