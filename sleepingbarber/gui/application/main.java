package sleepingbarber.gui.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(main.class.getResource("/sleepingbarber/gui/resources/Outer.fxml"));
            primaryStage.setTitle("Sleeping Barber");
            primaryStage.setScene(new javafx.scene.Scene(root));
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
