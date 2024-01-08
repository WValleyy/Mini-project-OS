package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuMonitor extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuMonitor.fxml"));
        Scene canvas = new Scene(root);
        stage.setScene(canvas);
        stage.setTitle("Monitor in Operating System");
        stage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}