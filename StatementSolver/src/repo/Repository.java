package repo;

import domain.ADT.*;
import domain.PrgState;
import domain.Statements.IStmt;
import exceptions.ADTException;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    private String logFilePath;

    public Repository(String _logFilePath)
    {
        prgStates = new ArrayList<>();
        logFilePath = _logFilePath;
        clearFile();
    }

    public void logPrgStateExec(PrgState crtPrgState)
    {
        try{
            PrintWriter logFile = null;
            try {
                logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            } catch(IOException ex) { System.err.println("FileWriter error" + ex); }

            // PrgState crtPrgState = getCrtPrgRepo();
            IStack<IStmt> exeStack = crtPrgState.getExeStack();
            IDictionary<String, Integer> symTable = crtPrgState.getSymTable();
            IFileTable<Integer, Pair<String, BufferedReader>> fileTable = crtPrgState.getFileTable();
            IList<Integer> out = crtPrgState.getOut();
            IHeap<Integer, Integer> heap = crtPrgState.getHeap();
            int id = crtPrgState.getId();

            logFile.println("Program State nr. " + id);
            logFile.println("EXESTACK:");
            ArrayList<IStmt> a = new ArrayList<IStmt>(exeStack.getStack());
            ListIterator<IStmt> li = a.listIterator(a.size());
            while(li.hasPrevious())
            {
                logFile.println("-> " + li.previous().toString());
            }

            logFile.println("SYMTABLE:");
            for ( String key : symTable.getKeys() )
            {
                logFile.println("-> " + "Key: " + key + ", Value: " + symTable.getValue(key).toString());
            }

            logFile.println("OUTPUT:");
            for(Integer e : out.getList())
            {
                logFile.println("-> " + e.toString());
            }

            logFile.println("HEAP:");
            for (Integer key : heap.getKeys() )
            {
                logFile.println("-> " + "Address: " + key + ", Value: " + heap.getValue(key).toString());
            }

            logFile.println("FILETABLE:");
            for(Integer key : fileTable.getKeys())
            {
                logFile.println("-> " + "Key: " + key.toString() + ", Value: " + fileTable.getValue(key).getKey());
            }
            logFile.println("------------------------------------------");
            logFile.close();
        }catch(ADTException ex){
            System.err.println(ex.getMessage());
        }

        }


    public void clearFile() {
        try {
            PrintWriter logFile = new PrintWriter(new FileWriter(logFilePath));
            logFile.close();
        } catch (IOException ex) {
            System.err.println("FileWriter error" + ex);
        }
    }

    public void add(PrgState prg) { prgStates.add(prg);}

    public List<PrgState> getPrgList() { return prgStates; }
    public void setPrgList(List<PrgState> newList) { this.prgStates = newList; }

    public PrgState getByID(Integer id)
    {
        for (PrgState prg : prgStates)
            if (prg.getId() == id)
                return prg;
        return null;
    }
}
