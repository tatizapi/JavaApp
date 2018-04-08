package domain.Statements;

import domain.PrgState;
import exceptions.StmtExecException;

public interface IStmt {
    PrgState execute(PrgState p) throws StmtExecException;
    String toString();
}