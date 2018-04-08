package sample;

import java.util.*;

import domain.ADT.*;
import domain.PrgState;
import domain.Statements.IStmt;
import exceptions.ADTException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import repo.IRepository;
import repo.Repository;

import javax.print.attribute.IntegerSyntax;
import java.io.BufferedReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.Collections.list;
import static java.util.Collections.reverse;

public class ExecuteStmtController
{
    private IRepository repo;
    private ExecutorService executor;
    private final String varSymTblKey = "Symbol Table Variable";
    private final String valSymTblKey = "Symbol Table Value";
    private final String addressHeapKey = "Heap Table Address";
    private final String valHeapKey = "Heap Table Value";
    private final String idFileTableKey = "File Table Identifier";
    private final String fnFileTableKey = "File Table Filename";
    private final String idxSmphTblKey = "Semaphore Table Index";
    private final String valSmphTblKey = "Semaphore Table Value";
    private final String listvalSmphKey = "Semaphore Table List Values";

    @FXML
    private Button exitBtn;

    @FXML
    private Button stepBtn;

    @FXML
    private ListView<Integer> prgStatesIDs;

    @FXML
    private ListView<IStmt> exeStackLV;

    @FXML
    private ListView<Integer> outputLV;

    @FXML
    private TextField nrPrgStatesTF;

    @FXML
    private TableView symbolTV;

    @FXML
    private TableView heapTV;

    @FXML
    private TableView fileTV;

    @FXML
    private TableView semaphoreTV;

    @FXML
    private TableColumn<Map, String> varSymTblColumn;

    @FXML
    private TableColumn<Map, String> valSymTblColumn;

    @FXML
    private TableColumn<Map, String> addressHeapColumn;

    @FXML
    private TableColumn<Map, String> valHeapColumn;

    @FXML
    private TableColumn<Map, String> idFileTableColumn;

    @FXML
    private TableColumn<Map, String> fnFileTableColumn;

    @FXML
    private TableColumn<Map, String> indexSmphColumn;

    @FXML
    private TableColumn<Map, String> valSmphColumn;

    @FXML
    private TableColumn<Map, String> listvalSmphColumn;

    @FXML
    public void initialize()
    {
        //prgStatesIDs.setItems(ge);
        initPrgStatesIDs();

        //initialize TextField
        initTextField();

        //initialize threading
        initializeThreading();

        //for them weird tables
        initializeSymTable();
        initializeHeapTable();
        initializeFileTable();
        initializeSemaphoreTable();

        //when selectedItem event from prgStatesID ListView happens
        prgStatesIDs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleSelectedItem());

    }

    @FXML
    void handleSelectedItem()
    {
        if (prgStatesIDs.getSelectionModel().isEmpty())
            prgStatesIDs.getSelectionModel().selectFirst();
        Integer prgStID = prgStatesIDs.getSelectionModel().getSelectedItem();

        PrgState prg = repo.getByID(prgStID);

        if (prg != null)
        {
            updateExeStackLV(prg);
            symbolTV.setItems(generateSymTableValues(prg));
            semaphoreTV.setItems(generateSmphTableValues(prg));

        }
    }

    @FXML
    void handleExitButton() throws Exception
    {
        executor.shutdown();
        Stage stage = (Stage)exitBtn.getScene().getWindow();
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Programs");
        stage.setScene(new Scene(root, 480, 400));
        stage.show();
    }

    @FXML
    void handleOneStepButton()
    {
        oneStepForAllPrg();
    }

    public void initializePrgState(IStmt stmt)
    {
        IStack<IStmt> stack = new MyStack<>();
        stack.push(stmt);
        IDictionary<String, Integer> symTable = new MyDictionary<>();
        IList<Integer> output = new MyList<>();
        IFileTable<Integer, Pair<String, BufferedReader>> fileTable = new MyFileTable<>();
        IHeap<Integer, Integer> heap = new MyHeap<>();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semphTbl = new MySemaphoreTable<>();
        PrgState prgState = new PrgState(stack, symTable, output, fileTable, heap, semphTbl, 1);

        this.repo = new Repository("E:\\github Projects\\Projects\\StatementSolver\\src\\File1.txt");
        repo.add(prgState);

    }

    private void initializeThreading()
    {
        executor = Executors.newFixedThreadPool(5);
    }

    private void initializeSymTable()
    {
        varSymTblColumn.setCellValueFactory(new MapValueFactory<>(varSymTblKey));
        valSymTblColumn.setCellValueFactory(new MapValueFactory<>(valSymTblKey));
    }

    private void initializeHeapTable()
    {
        addressHeapColumn.setCellValueFactory(new MapValueFactory<>(addressHeapKey));
        valHeapColumn.setCellValueFactory(new MapValueFactory<>(valHeapKey));
    }

    private void initializeFileTable()
    {
        idFileTableColumn.setCellValueFactory(new MapValueFactory<>(idFileTableKey));
        fnFileTableColumn.setCellValueFactory(new MapValueFactory<>(fnFileTableKey));

    }

    private void initializeSemaphoreTable()
    {
        indexSmphColumn.setCellValueFactory(new MapValueFactory<>(idxSmphTblKey));
        valSmphColumn.setCellValueFactory(new MapValueFactory<>(valSmphTblKey));
        listvalSmphColumn.setCellValueFactory(new MapValueFactory<>(listvalSmphKey));
    }


    public void oneStepForAllPrg()
    {
        List<PrgState> prgList = repo.getPrgList();

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            System.out.println("Error when executing one step: " + e.getMessage());
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);
            prgList.forEach(prg -> repo.logPrgStateExec(prg));
            repo.setPrgList(prgList);

        } catch (InterruptedException e) {
            System.out.println("Error when executing threads" + e.getMessage());
        }

        prgList = removeCompletedPrg(repo.getPrgList());
        updateViews();
        repo.setPrgList(prgList);
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }


    private void initPrgStatesIDs()
    {
        List<Integer> ids = new ArrayList<>();
        for (PrgState prg : repo.getPrgList())
        {
            ids.add(prg.getId());
        }
        ObservableList<Integer> identifiers = FXCollections.observableArrayList();
        identifiers.addAll(ids);
        prgStatesIDs.setItems(identifiers);
    }

    private void initTextField()
    {
        Integer nrOfPrgStates = repo.getPrgList().size();
        nrPrgStatesTF.setText(nrOfPrgStates.toString());
    }

    private void updateViews()
    {
        Integer prgStID = prgStatesIDs.getSelectionModel().getSelectedItem();
        PrgState prg = repo.getByID(prgStID);

        if (prg != null)
        {
            initTextField();
            initPrgStatesIDs();
            updateExeStackLV(prg);
            updateOutputLV(prg);
            symbolTV.setItems(generateSymTableValues(prg));
            heapTV.setItems(generateHeapValues(prg));
            fileTV.setItems(generateFileTableValues(prg));
            semaphoreTV.setItems(generateSmphTableValues(prg));
        }


    }

    private void updateExeStackLV(PrgState prg)
    {
        Stack<IStmt> exeStack = prg.getExeStack().getStack();
        reverse(exeStack);
        ObservableList<IStmt> obsExeStack = FXCollections.observableArrayList(exeStack);
        exeStackLV.setItems(obsExeStack);
        reverse(exeStack);

    }

    private void updateOutputLV(PrgState prg)
    {
        ObservableList<Integer> obsOutput = FXCollections.observableArrayList(prg.getOut().getList());
        outputLV.setItems(obsOutput);
    }

    private ObservableList<Map> generateSymTableValues(PrgState prg)
    {
        IDictionary<String, Integer> symTbl = prg.getSymTable();
        ObservableList<Map> obsSymTbl = FXCollections.observableArrayList();

        for (String x : symTbl.getKeys())
        {
            Map<String, String> map = new HashMap<>();
            String key = x;
            String val = symTbl.getValue(x).toString();
            map.put(varSymTblKey, key);
            map.put(valSymTblKey, val);
            obsSymTbl.add(map);
        }
        return obsSymTbl;

    }

    private ObservableList<Map> generateHeapValues(PrgState prg)
    {
        IHeap<Integer, Integer> heap = prg.getHeap();
        ObservableList<Map> obsHeap = FXCollections.observableArrayList();

        for (Integer x : heap.getKeys())
        {
            Map<String, String> map = new HashMap<>();
            String key = x.toString();
            String val = heap.getValue(x).toString();
            map.put(addressHeapKey, key);
            map.put(valHeapKey, val);
            obsHeap.add(map);
        }
        return obsHeap;

    }

    private ObservableList<Map> generateFileTableValues(PrgState prg)
    {
        Set<Integer> fileTblKeys = prg.getFileTable().getKeys();
        IFileTable<Integer, Pair<String, BufferedReader>> fileTable = prg.getFileTable();
        ObservableList<Map> obsFileTable = FXCollections.observableArrayList();

        for (Integer x : fileTblKeys)
        {
            Map<String, String> map = new HashMap<>();
            String key = x.toString();
            String val = "";
            try{
                val = fileTable.getValue(x).toString();
            }catch (ADTException ex){
                System.err.println(ex.getMessage());
            }
            map.put(idFileTableKey, key);
            map.put(fnFileTableKey, val);
            obsFileTable.add(map);
        }
        return obsFileTable;

    }

    private ObservableList<Map> generateSmphTableValues(PrgState prg)
    {
        ObservableList<Map> obsFileTable = FXCollections.observableArrayList();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> smphTable = prg.getSemaphoreTable();
        Set<Integer> smphTblKeys = prg.getSemaphoreTable().getKeys();

        for (Integer x : smphTblKeys)
        {
            Map<String, String> map = new HashMap<>();
            String key = x.toString();
            String val = "";
            String listval = "";

            val = smphTable.getValue(x).getKey().toString();
            listval = smphTable.getValue(x).getValue().toString();

            map.put(idxSmphTblKey, key);
            map.put(valSmphTblKey, val);
            map.put(listvalSmphKey, listval);
            obsFileTable.add(map);
        }
        return obsFileTable;
    }

}
