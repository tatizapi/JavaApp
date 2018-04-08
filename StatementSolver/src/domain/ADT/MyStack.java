package domain.ADT;

import exceptions.ADTException;

import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stack;

    public MyStack() { stack = new Stack<T>(); }

    public void push(T val)
    {
        stack.push(val);
    }
    public T pop() throws ADTException
    {
        if (stack.isEmpty())
            throw new ADTException("Stack is empty");
        return stack.pop();
    }
    public boolean isEmpty() {return stack.isEmpty();}
    public Stack<T> getStack() {return stack;}
}
