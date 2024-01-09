package SleepingBarberGUI.gui.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class MainSB extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(MainSB.class.getResource("/SleepingBarberGUI/gui/resources/Outer.fxml"));
            primaryStage.setTitle("Sleeping Barber");
            Image image = new Image(getClass().getResourceAsStream("/SleepingBarberGUI/gui/resources/sic.jpg"));
            primaryStage.getIcons().add(image);
            primaryStage.setScene(new javafx.scene.Scene(root));
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
