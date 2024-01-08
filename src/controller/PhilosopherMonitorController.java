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

    private PhilosopherMonitor monitor;

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

        if (monitor != null) {
            String[] philosopherStates = monitor.getPhilosopherStates();
            System.out.println("Before");
            Platform.runLater(() -> {
                System.out.println("Updating GUI from Platform.runLater");
                outputTextArea.clear();
                // for (String state : philosopherStates) {
                //     outputTextArea.appendText(state + "\n");
                // }
                for (int i = 0; i < philosopherStates.length; i++) {
                    String state = philosopherStates[i];
                    outputTextArea.appendText(state + "\n");

                    // Lấy Circle tương ứng với triết gia i
                    Circle philosopherCircle = getPhilosopherCircle(i);

                    // Đặt màu sắc dựa trên trạng thái
                    if (state.contains("thinking")) {
                        philosopherCircle.setFill(javafx.scene.paint.Color.valueOf("#b2a2e5"));
                    } else if (state.contains("eating")) {
                        philosopherCircle.setFill(javafx.scene.paint.Color.GREEN);
                    } else if (state.contains("hungry")) {
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
                System.out.println("Returning philosopher1Circle");
                return philosopher1Circle;
            case 1:
                System.out.println("Returning philosopher2Circle");
                return philosopher2Circle;
            case 2:
                System.out.println("Returning philosopher3Circle");
                return philosopher3Circle;
            case 3:
                System.out.println("Returning philosopher4Circle");
                return philosopher4Circle;
            case 4:
                System.out.println("Returning philosopher5Circle");
                return philosopher5Circle;
            default:
                return null;
        }
    }
}
