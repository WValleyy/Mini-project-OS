package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import monitor.OutputListener;
import monitor.PhilosopherMonitor;
import monitor.Main;

public class PhilosopherMonitorController implements OutputListener{
    @FXML
    private TextArea outputTextArea;

    @FXML
    public Circle philosopher1Circle;

    @FXML
    public Circle philosopher2Circle;

    @FXML
    public Circle philosopher3Circle;

    @FXML
    public Circle philosopher4Circle;

    @FXML
    public Circle philosopher5Circle;

    @FXML
    private void handleRestartButton(ActionEvent event) {
        outputTextArea.clear();
        Thread mainThread = new Thread(() -> {
            try {
                Main.setOutputListener(this);
                Main.main(new String[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mainThread.start();
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
        Platform.runLater(() -> outputTextArea.appendText(message + "\n"));
    }

    @Override
    public void onEventLogged(String event) {
        updateOutput(event);

        PhilosopherMonitor monitor = Main.getPhilosopherMonitor();
        if (monitor == null) {
            System.out.println(">> Monitor is Null");
        }

        if (monitor != null) {
            String[] philosopherStates = monitor.getPhilosopherStates();
            Platform.runLater(() -> {
                // outputTextArea.clear();
                for (int i = 0; i < philosopherStates.length; i++) {
                    String state = philosopherStates[i];
                    // outputTextArea.appendText(state + "\n");

                    Circle philosopherCircle = getPhilosopherCircle(i);

                    if (state.contains("THINKING")) {
                        philosopherCircle.setFill(javafx.scene.paint.Color.valueOf("#b2a2e5"));
                    } else if (state.contains("EATING")) {
                        philosopherCircle.setFill(javafx.scene.paint.Color.GREEN);
                    } else if (state.contains("HUNGRY")) {
                        philosopherCircle.setFill(javafx.scene.paint.Color.RED);
                    }
                }
            });
        }
    }

    public Circle getPhilosopherCircle(int philosopherIndex) {
        // Lấy Circle dựa vào chỉ số của triết gia
        switch (philosopherIndex) {
            case 0:
                return philosopher1Circle;
            case 1:
                return philosopher2Circle;
            case 2:
                return philosopher3Circle;
            case 3:
                return philosopher4Circle;
            case 4:
                return philosopher5Circle;
            default:
                return null;
        }
    }
}
