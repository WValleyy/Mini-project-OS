package SleepingBarberGUI.gui.application;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Controller2 {

    @FXML
    private Label myLabel1;
    @FXML
    private Label myLabel2;
    @FXML
    private ImageView imageView;
    @FXML
    private FlowPane flowPane;
    // private PrintStream originalOut;
    // private ByteArrayOutputStream outputStream;
    private List<String> capturedTexts;
    private Image image1 = new Image(getClass().getResourceAsStream("/SleepingBarberGUI/gui/resources/sleep.png"));
    private Image image2 = new Image(getClass().getResourceAsStream("/SleepingBarberGUI/gui/resources/working.jpg"));
    private int availableChairs;
    private int chairs;
    public void setChairs(int chairs) {
        this.chairs = chairs;
        availableChairs = chairs;
    }
    public void setChair() {
        flowPane.getChildren().clear(); // Clear existing rectangles before adding new ones
        for (int i = 0; i < chairs; i++) {
            Rectangle rectangle = new Rectangle(60, 60, getDefaultChairColor(i,(int)chairs-availableChairs));
            flowPane.getChildren().add(rectangle);
        }
    }
    private Color getDefaultChairColor(int chairIndex,int m) {
        // You can customize the color logic based on the chair index
        if (chairIndex < m) {
            return Color.RED;
        } else {
            return Color.GREEN;
        }
    }

    public void SB(int chairs, int customers) {
        SleepingBarberProblem sbp = new SleepingBarberProblem(chairs, customers);
        capturedTexts = new ArrayList<>();

        // Redirect standard output to a custom stream
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream customOut = new PrintStream(outputStream);
        System.setOut(customOut);

        sbp.start();

        System.setOut(originalOut);

        // Extract captured text from the custom stream
        String[] capturedLines = outputStream.toString().split(System.lineSeparator());
        for (String line : capturedLines) {
            capturedTexts.add(line);
        }

        // Print the captured texts
        System.out.println("Captured Texts:");
        for (String text : capturedTexts) {
            System.out.println(text);
        }
    }
    public void displayState() {
        SequentialTransition sequentialTransition = new SequentialTransition();

        for (String text : capturedTexts) {
            sequentialTransition.getChildren().add(createKeyFrame(text));
        }

        // Play the animation
        sequentialTransition.play();
    }

    private PauseTransition createKeyFrame(String text) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5)); // Adjust the duration as needed

        pause.setOnFinished(event -> {
            // Update UI based on the current captured text
            updateUI(text);
        });

        return pause;
    }

    private void updateUI(String text) {
        if (text.contains("Barber is sleeping.") ) {
            imageView.setImage(image1);
            myLabel1.setText("Barber is sleeping.");
        } else if(text.contains("Barber is done for the day")){
            imageView.setImage(image1);
            myLabel1.setText(text);
        } else if (text.contains("Barber calls Customer")) {
            imageView.setImage(image2);
            myLabel1.setText("Barber is working.");
            myLabel2.setText(text);
            availableChairs++;
            setChair();
        }else if (text.contains("enters the waiting room")) {
            if (availableChairs > 0) {
                myLabel2.setText(text);
                availableChairs--;
                setChair();
            } else {
                myLabel2.setText("Customer leaves since all chairs are occupied.");
            }
        }else if (text.contains("finished cutting hair")) {
            myLabel2.setText(text);
        }else if (text.contains("leaves since all chairs are occupied")) {
            myLabel2.setText(text);
        }

    }
}

