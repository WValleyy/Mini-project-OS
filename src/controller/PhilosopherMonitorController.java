package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PhilosopherMonitorController {
    @FXML
    private TextArea outputTextArea;

    @FXML
    private void handleRestartButton(ActionEvent event) {
        updateOutput("Restarted");
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
        outputTextArea.appendText(message + "\n");
    }
}
