package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monitor.OutputListener;

import monitor.Main;

public class PhilosopherMonitorController implements OutputListener, Initializable {
    @FXML
    private TextArea outputTextArea;

    @FXML
    private void handleRestartButton(ActionEvent event) {
        Thread mainThread = new Thread(() -> {
            try {
                System.out.println("Loading FXML...");
                Main.main(new String[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mainThread.start();

        Main.setOutputListener(this);
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MenuMonitor.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dining Philosophers");
        stage.show();
    }

    @FXML
    private void handleAnalyzeButton(ActionEvent event) {

    }

    public void updateOutput(String message) {
        // outputTextArea.appendText(message + "\n");
        Platform.runLater(() -> outputTextArea.appendText(new Text(message + "\n").getText()));
        // Platform.runLater(() -> {
        //     System.out.println("Uptating UI...");
        //     outputTextArea.appendText(message + "\n");});
    }

    @Override
    public void onEventLogged(String event) {
        updateOutput(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.setOutputListener(this);
    }
}
