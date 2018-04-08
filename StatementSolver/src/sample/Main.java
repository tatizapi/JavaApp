package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = (Pane)FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Programs");
        primaryStage.setScene(new Scene(root, 480, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
