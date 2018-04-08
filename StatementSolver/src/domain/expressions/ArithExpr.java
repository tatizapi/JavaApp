package domain.expressions;

import domain.ADT.IDictionary;
import domain.ADT.IHeap;
import exceptions.ADTException;
import exceptions.ExprException;

public class ArithExpr extends Expr {
    private char operator;
    private Expr operand1, operand2;

    public ArithExpr(char op, Expr op1, Expr op2) {
        operator = op;
        operand1 = op1;
        operand2 = op2;
    }

    @Override
    public int evaluate(IDictionary<String,Integer> symTable, IHeap<Integer,Integer> heap)  throws ExprException {
        int resultFirst = operand1.evaluate(symTable, heap), resultSecond = operand2.evaluate(symTable, heap);
        switch(operator) {
            case '+': return resultFirst + resultSecond;
            case '-': return resultFirst - resultSecond;
            case '*': return resultFirst * resultSecond;
            case '/':
                if(resultSecond == 0) {
                    throw new ArithmeticException("Cannot divide by 0");
                } else {
                    return resultFirst / resultSecond;
                }
            default: throw new RuntimeException("Invalid operator");
        }
    }

    @Override
    public String toString() { return operand1 + " " + operator + " " + operand2; }
}