package domain.ADT;

import exceptions.ADTException;

import java.util.Stack;

public interface IStack<T>
{
    void push(T val);
    T pop() throws ADTException;
    boolean isEmpty();
    Stack<T> getStack();
}
