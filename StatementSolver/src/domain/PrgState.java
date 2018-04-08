package domain;


import domain.ADT.*;
import domain.Statements.IStmt;
import exceptions.ADTException;
import exceptions.ExprException;
import exceptions.StmtExecException;
import javafx.util.Pair;
import java.util.List;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, Integer> symTable;
    private IList<Integer> out;
    private IFileTable<Integer, Pair<String, BufferedReader>> fileTable;
    private IHeap<Integer, Integer> heap;
    private ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semTable;
    private int id;

    public PrgState(IStack<IStmt> stk, IDictionary<String, Integer> symtbl, IList<Integer> ot,
                    IFileTable<Integer, Pair<String, BufferedReader>> ft, IHeap<Integer, Integer> hp,
                    ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> stbl, int ID)
    {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = ft;
        heap = hp;
        semTable = stbl;
        id = ID;
    }

    public Boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws StmtExecException {
        if (exeStack.isEmpty()) {
            throw new StmtExecException("Stack is empty");
        }


        try {
            IStmt crtStmt = exeStack.pop();
            PrgState prg;
            prg = crtStmt.execute(this);
            return prg;
        } catch (StmtExecException ex) {
            throw ex;
        } catch (ADTException ex){
            throw new StmtExecException(ex.getMessage()); }

    }

    public IStack<IStmt> getExeStack() {return exeStack;}
    public IDictionary<String, Integer> getSymTable() {return symTable;}
    public IList<Integer> getOut() {return out;}
    public IFileTable<Integer, Pair<String, BufferedReader>> getFileTable() {return fileTable; }
    public IHeap<Integer, Integer> getHeap() {return heap;}
    public ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() { return semTable; }
    public int getId() {return id;}
}
