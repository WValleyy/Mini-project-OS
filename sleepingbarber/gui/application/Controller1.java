package sleepingbarber.gui.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller1 implements Initializable {
    @FXML
    private Spinner<Integer> Spin1;
    @FXML
    private Spinner<Integer> Spin3;
    @FXML
    private Button myButton;

    int chairs;
    int customers;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        Spin1.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        Spin3.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
    }
    public void start(ActionEvent e) throws IOException{
        chairs = Spin1.getValue();
        customers = Spin3.getValue();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sleepingbarber/gui/resources/Inside.fxml"));
        root = loader.load();
        Controller2 controller2 = loader.getController();
        controller2.SB(chairs, customers);
        controller2.setChairs(chairs);
        controller2.setChair();
        controller2.displayState();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
