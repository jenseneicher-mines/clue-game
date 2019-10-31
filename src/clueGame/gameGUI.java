package clueGame;

import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;import javax.swing.JFrame;import javax.swing.JLabel;
import javax.swing.JPanel;import javax.swing.JTextField;import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class gameGUI extends JPanel {
    private JTextField name;

    public gameGUI(){
        setLayout(new GridLayout(2,1));
        JPanel panel1 = middlePanel();
        add(panel1);
        JPanel panel3 = bottomPanel();
        add(panel3);
    }


    private JPanel middlePanel() {
        JPanel panel = new JPanel();
        // Use a grid layout, 1 row, 2 elements (label, text)
        panel.setLayout(new GridLayout(1,3));
        JLabel nameLabel = new JLabel("Whose Turn?");
        panel.add(nameLabel);

        // no layout specified, so this is flow
        JButton nextPlayer = new JButton("Next Player");
        JButton makeAccusation = new JButton("Make an Accusation");
        panel.setBorder(new TitledBorder (new EtchedBorder(), ""));
        panel.add(nextPlayer);
        panel.add(makeAccusation);
        return panel;
    }

    private JPanel bottomPanel() {
        JPanel panel = new JPanel();
        // Use a grid layout, 1 row, 2 elements (label, text)
        panel.setLayout(new GridLayout(1,3));
        JLabel nameLabel = new JLabel("Die Roll: ");
        panel.add(nameLabel);
        panel.setBorder(new TitledBorder (new EtchedBorder(), ""));
        // no layout specified, so this is flow
        JTextField guess = new JTextField("Guess: ");
        panel.add(guess);
        JLabel guessResponse = new JLabel("Guess Response: ");
        panel.add(guessResponse);
        panel.setMaximumSize( new Dimension(10,15));
        return panel;
    }

    public static void main(String[] args) {
        // Create a JFrame with all the normal functionality
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Clue");
        frame.setSize(700, 200);
        // Create the JPanel and add it to the JFrame
        gameGUI gui = new gameGUI();
        frame.add(gui, BorderLayout.CENTER);
        // Now let's view it
        frame.setVisible(true);
    }


}


