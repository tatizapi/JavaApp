package repo;


import domain.PrgState;

import java.util.List;

public interface IRepository
{
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    void add(PrgState prog);
    void logPrgStateExec(PrgState crtPrgState);
    void clearFile();
    PrgState getByID(Integer id);
}