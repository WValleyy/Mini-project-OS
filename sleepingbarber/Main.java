package sleepingbarber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sleeping Barber game ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,530);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setPreferredSize(new Dimension(500,150));
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("src//2.jpg"));
        leftPanel.add(imageLabel);
        frame.add(leftPanel,BorderLayout.WEST); // Adds Button to content pane of frame
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setPreferredSize(new Dimension(500,150));
        rightPanel.setBackground(Color.BLACK);

        JLabel maintext = new JLabel("The sleeping Barber ");
        maintext.setFont(new Font("Georgia", Font.PLAIN, 30));
        maintext.setForeground(Color.RED);
        rightPanel.add(maintext);
        maintext.setLocation(107, 150);
        maintext.setSize(806, 104);
        JButton Start = new JButton("Play Now");
        Start.setFont(new Font("Georgia", Font.PLAIN, 15));
        Start.setForeground(Color.BLACK);
        rightPanel.add(Start);
        Start.setLocation(170, 300);
        Start.setSize(150, 50);
        rightPanel.setLayout(null);
        frame.add(rightPanel,BorderLayout.EAST);
        frame.setVisible(true);
        Start.addActionListener(new AbstractAction() {
            // Calling the form page to be viewed
            @Override
            public void actionPerformed(ActionEvent e) {
                InputFrame I = new InputFrame();
                I.setSize(1000,530);
                I.setBackground(Color.BLACK);
                I.setVisible(true);
            }
        });
    }
}