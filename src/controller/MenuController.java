package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Node;
import Reader_Writer.MainRW;

public class MenuController {
    @FXML
    private void handleDiningPhilosophers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PhilosopherMonitor.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dining Philosophers");
        stage.show();
    }

    @FXML
    private void handleSleepingBarber(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SleepingBarberGUI/gui/resources/Outer.fxml"));
            Image image = new Image(getClass().getResourceAsStream("/SleepingBarberGUI/gui/resources/sic.jpg"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.setTitle("Sleeping Barber");
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReaderWriter(ActionEvent event) throws IOException {
        Thread mainThread = new Thread(() -> {
            MainRW.main(new String[0]);
        });
        mainThread.start();
    }
}
