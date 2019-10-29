package clueGame;

import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;import javax.swing.JFrame;import javax.swing.JLabel;
import javax.swing.JPanel;import javax.swing.JTextField;import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class gameGUI extends JPanel {
    private JTextField name;

    public gameGUI(){
        setLayout(new GridLayout(2,0));
        JPanel panel1 = mainPanel();
        add(panel1);
        JPanel panel2 = topPanel();
        add(panel2);
        JPanel panel3 = bottomPanel();
        add(panel3);
        JPanel panel4 = createButtonPanel();
        add(panel4);
    }


    private JPanel mainPanel() {
        JPanel panel = new JPanel();
        // Use a grid layout, 1 row, 2 elements (label, text)
        panel.setLayout(new GridLayout(2,1));
        panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
        panel.setMaximumSize(new Dimension(20,400));
        return panel;
    }

    private JPanel topPanel() {
        JPanel panel = new JPanel();
        // Use a grid layout, 1 row, 2 elements (label, text)
        panel.setLayout(new GridLayout(1,2));
        JLabel nameLabel = new JLabel("Name");
        panel.add(nameLabel);
        panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
        return panel;
    }

    private JPanel bottomPanel() {
        JPanel panel = new JPanel();
        // Use a grid layout, 1 row, 2 elements (label, text)
        panel.setLayout(new GridLayout(1,2));
        JLabel nameLabel = new JLabel("Name");
        panel.add(nameLabel);
        panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
        return panel;
    }

    private JPanel createButtonPanel() {
        // no layout specified, so this is flow
        JButton nextPlayer = new JButton("Next Player");
        JButton makeAccusation = new JButton("Make an Accusation");
        JPanel panel = new JPanel();
        panel.add(nextPlayer);
        panel.add(makeAccusation);
        return panel;
    }

    public static void main(String[] args) {
        // Create a JFrame with all the normal functionality
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Clue");
        frame.setSize(800, 650);
        // Create the JPanel and add it to the JFrame
        gameGUI gui = new gameGUI();
        frame.add(gui, BorderLayout.CENTER);
        // Now let's view it
        frame.setVisible(true);
    }


}


