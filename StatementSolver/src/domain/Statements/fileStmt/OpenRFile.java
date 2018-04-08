package domain.Statements.fileStmt;

import domain.ADT.IDictionary;
import domain.ADT.IFileTable;
import domain.PrgState;
import domain.Statements.IStmt;
import exceptions.StmtExecException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt
{
    private String var_file_id;
    private String filename;
    private int count = 0;

    public OpenRFile(String vfi, String fn)
    {
        var_file_id = vfi;
        filename = fn;
    }

    public PrgState execute(PrgState state) throws StmtExecException
    {
        IFileTable<Integer, Pair<String, BufferedReader>> fileTable = state.getFileTable();
        BufferedReader bf = null;


        try
        {
            bf = new BufferedReader(new FileReader(filename));
        } catch (IOException ex) { throw new StmtExecException("File doesn't exist " + filename); }

        for (Pair<String, BufferedReader> pair : fileTable.getValues())
            if (pair.getKey().equals(filename))
                throw new StmtExecException("File was open before");

        fileTable.put(count, new Pair<String, BufferedReader>(filename, bf));

        IDictionary<String, Integer> symTbl = state.getSymTable();
        symTbl.put(var_file_id, count);
        count = count + 1;

        return null;
    }

    public String toString()
    {
        return "openRFile(" + var_file_id + ", " + filename +")";
    }
}