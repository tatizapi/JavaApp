package domain.Statements.fileStmt;

import domain.PrgState;
import domain.Statements.IStmt;
import domain.expressions.Expr;
import exceptions.ADTException;
import exceptions.ExprException;
import exceptions.StmtExecException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadFile implements IStmt
{
    Expr exp_file_id;
    String var_name;

    public ReadFile(Expr id, String name) {
        exp_file_id=id;
        var_name=name;
    }

    public PrgState execute(PrgState state) throws StmtExecException
    {
        try {
            int value = exp_file_id.evaluate(state.getSymTable(), state.getHeap());
            Pair<String, BufferedReader> r = state.getFileTable().getValue(value);
            state.getSymTable().put(var_name, Integer.parseInt(r.getValue().readLine()));
        } catch (NumberFormatException e) {
            throw new StmtExecException("Bad data file. Wrong format.");
        } catch (IOException e) {
            throw new StmtExecException("Error loading file");
        } catch (ExprException e) {
            throw new StmtExecException("");
        } catch (ADTException e) {
            throw new StmtExecException (e.getMessage());
        }

        return null;
    }

    public String toString(){
        return "readFile( "+ exp_file_id.toString() + "," + var_name + ")";
    }
}
