package sample;

import domain.Statements.*;
import domain.Statements.fileStmt.CloseRFile;
import domain.Statements.fileStmt.OpenRFile;
import domain.Statements.fileStmt.ReadFile;
import domain.Statements.heapStmt.NewStmt;
import domain.Statements.heapStmt.wHStmt;
import domain.expressions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller
{
    @FXML
    private ListView<IStmt> stmtListView;

    @FXML
    private Button executeBtn;

    @FXML
    public void initialize()
    {
        stmtListView.setItems(getIStmtList());

    }

    @FXML
    void handleExecuteButton() throws Exception
    {
        IStmt stmtToExec = stmtListView.getSelectionModel().getSelectedItem();
        ExecuteStmtController ctrl = new ExecuteStmtController();
        ctrl.initializePrgState(stmtToExec);


        Stage stage = (Stage)executeBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("execStmt.fxml"));
        loader.setController(ctrl);
        Pane root = (Pane)loader.load();
        stage.setTitle("Execute statement");
        stage.setScene(new Scene(root, 700, 700));
        stage.show();

    }


    private ObservableList<IStmt> getIStmtList()
    {
        IStmt ex1 = new CompStmt(new AssignStmt("a", new ArithExpr('+',new ConstExpr(2),new
                ArithExpr('*',new ConstExpr(3), new ConstExpr(5)))),
                new CompStmt(new AssignStmt("b",new ArithExpr('+',new VarExpr("a"), new
                        ConstExpr(1))), new PrintStmt(new VarExpr("b"))));
        IStmt ex2 = new CompStmt(new PrintStmt(new ArithExpr('-', new ConstExpr(20), new BooleanExpr("<", new ConstExpr(3), new ConstExpr(1)))),
                new PrintStmt(new ArithExpr('+', new ConstExpr(10), new BooleanExpr("!=", new ConstExpr(2), new ConstExpr(6)))));
        IStmt ex3 = new CompStmt(new AssignStmt("v", new ConstExpr(6)),
                new CompStmt(new WhileStmt(new ArithExpr('-', new VarExpr("v"), new ConstExpr(4)),
                        new CompStmt(new PrintStmt(new VarExpr("v")), new AssignStmt("v", new ArithExpr('-', new VarExpr("v"), new ConstExpr(1))))),
                        new PrintStmt(new VarExpr("v"))));
        IStmt ex4 = new CompStmt(new AssignStmt("v", new ConstExpr(10)),
                new CompStmt(new NewStmt("v", new ConstExpr(20)),
                        new CompStmt( new NewStmt("a", new ConstExpr(22)),
                                new CompStmt( new wHStmt("a", new ConstExpr(30)),
                                        new CompStmt(new PrintStmt(new VarExpr("a")),
                                                new CompStmt(new PrintStmt(new rHExpr("a")), new AssignStmt("a", new ConstExpr(0))))))));
        IStmt ex5 = new CompStmt(new CompStmt(new AssignStmt("v", new ConstExpr(10)),
                new NewStmt("a", new ConstExpr(22))), new CompStmt(new ForkStmt(
                new CompStmt(new wHStmt("a", new ConstExpr(30)),
                        new CompStmt(new AssignStmt("v", new ConstExpr(32)),
                                new CompStmt(new PrintStmt(new VarExpr("v")),
                                        new PrintStmt(new rHExpr("a")))))),
                new CompStmt(new PrintStmt(new VarExpr("v")),
                new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new rHExpr("a"))))));


        IStmt ex6 = new CompStmt(new OpenRFile("var_f", "E:\\github Projects\\Projects\\StatementSolver\\src\\files\\test.txt"),
                                new CompStmt(new CompStmt(new ReadFile(new VarExpr("var_f"), "var_c"), new PrintStmt(new VarExpr("var_c"))),
                                        new CompStmt(new IfStmt(new VarExpr("var_c"), new CompStmt(new ReadFile(new VarExpr("var_f"), "var_c"),
                                                new PrintStmt(new VarExpr("var_c"))),
                new PrintStmt(new ConstExpr(0))), new CloseRFile(new VarExpr("var_f")))));


        /*
        v=1;fork(v=2);fork(v=3)
         */
        IStmt ex7 = new CompStmt(new AssignStmt("v", new ConstExpr(1)),
                new CompStmt(new ForkStmt(new AssignStmt("v", new ConstExpr(2))),
                                 new ForkStmt(new AssignStmt("v", new ConstExpr(3)))));

        /*
        a=1;b=2;
        c=a?100:200;
        print(c);
        c= (b-2)?100:200
        print(c);
         */

        IStmt cmp1 = new CompStmt( new CondStmt("c", new VarExpr("a"), new ConstExpr(100), new ConstExpr(200)),
                new CompStmt(new PrintStmt(new VarExpr("c")) ,
                new CompStmt(new CondStmt("c", new ArithExpr('-', new VarExpr("b"), new ConstExpr(2)),
                                               new ConstExpr(100), new ConstExpr(200)),
                    new PrintStmt(new VarExpr("c")))));
        IStmt cmp2 = new CompStmt(new AssignStmt("a", new ConstExpr(1)), new AssignStmt("b", new ConstExpr(2)));
        IStmt ex8 = new CompStmt(cmp2, cmp1);

        /*
        cmp13: new(v1,1);newSemaphore(cnt,rH(v1));

        cmp12: fork(acquire(cnt);wh(v1,rh(v1)*10);print(rh(v1));release(cnt));

        cmp11: fork(acquire(cnt);wh(v1,rh(v1)*10);wh(v1,rh(v1)*2));print(rh(v1));release(cnt));

        cmp10:
        acquire(cnt);
        print(rh(v1)-1);
        release(cnt)
         */

        IStmt cmp10 = new CompStmt(new AcquireStmt("cnt"),
                                new CompStmt(new PrintStmt(new ArithExpr('-', new rHExpr("v1"), new ConstExpr(1))),
                                              new ReleaseStmt("cnt")));
        IStmt cmp11 = new CompStmt(new AcquireStmt("cnt"),
                                new CompStmt(new wHStmt("v1", new ArithExpr('*', new rHExpr("v1"), new ConstExpr(10))),
                                        new CompStmt(new wHStmt("v1", new ArithExpr('*', new rHExpr("v1"), new ConstExpr(2))),
                                                new CompStmt(new PrintStmt(new rHExpr("v1")), new ReleaseStmt("cnt")))));
        IStmt cmp12 = new CompStmt(new AcquireStmt("cnt"),
                                new CompStmt(new wHStmt("v1", new ArithExpr('*', new rHExpr("v1"), new ConstExpr(10))),
                                            new CompStmt(new PrintStmt(new rHExpr("v1")), new ReleaseStmt("cnt"))));
        IStmt cmp13 = new CompStmt(new NewStmt("v1", new ConstExpr(1)), new NewSemaphoreStmt("cnt", new rHExpr("v1")));
        IStmt ex9 = new CompStmt(cmp13, new CompStmt(new ForkStmt(cmp12), new CompStmt(new ForkStmt(cmp11), cmp10)));


        ObservableList<IStmt> stmts = FXCollections.observableArrayList(ex1, ex2, ex3, ex4, ex6, ex5, ex7, ex8, ex9);
        return stmts;

    }
}
