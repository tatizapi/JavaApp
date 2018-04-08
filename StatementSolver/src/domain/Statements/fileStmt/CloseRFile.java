package domain.Statements.fileStmt;

import domain.PrgState;
import domain.Statements.IStmt;
import domain.expressions.Expr;
import exceptions.ADTException;
import exceptions.ExprException;
import exceptions.StmtExecException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt
{
    Expr exp_file_id;

    public CloseRFile(Expr id) {
        exp_file_id=id;
    }

    public PrgState execute(PrgState state) throws StmtExecException {
        int fileId;
        try {
            fileId = exp_file_id.evaluate(state.getSymTable(), state.getHeap());
            BufferedReader br = state.getFileTable().getValue(fileId).getValue();
            br.close();
            state.getFileTable().remove(fileId);
        } catch (IOException e) {
            throw new StmtExecException("File error for file id = " + exp_file_id.toString());
        } catch (NullPointerException e) {
            throw new StmtExecException("File wasn't open before");
        } catch (ADTException e) {
            throw new StmtExecException (e.getMessage());
        } catch (ExprException e) {
            throw new StmtExecException(e.getMessage());
        }

        return null;
    }

    public String toString(){
        return "closeRFile(" + exp_file_id.toString() + ")";
    }
}
