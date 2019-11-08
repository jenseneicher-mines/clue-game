// Jensen Eicher & Kai Mizuno

package clueGame;

import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;import javax.swing.JFrame;import javax.swing.JLabel;
import javax.swing.JPanel;import javax.swing.JTextField;import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class gameGUI extends JFrame {
	//TEST VARIABLES ONLY FOR SHOWING DISPLAY
	private String name = "Placeholder";
	private int dieRoll = -1;
	private String guessResp = "Placeholder";
	private static Board board;

	public gameGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(550, 700);
		//set up each panel and arrange them in correct order
		//setLayout(new GridLayout(3,1));
		board = Board.getInstance();
		board.setConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");
		// Initialize will load BOTH config files 
		board.initialize();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(50);
		layout.setVgap(250);
		board.setLayout(layout);
		add(board, BorderLayout.NORTH);
		JPanel panel1 = middlePanel();
		add(panel1, BorderLayout.CENTER);
		JPanel panel3 = bottomPanel();
		add(panel3, BorderLayout.SOUTH);
	}


	private JPanel middlePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,4));
		//Create label for displaying whose turn and their name
		JLabel Label = new JLabel("<html> Whose turn? &nbsp" + "<br>" + name + "<html>");
		panel.add(Label);
		//set boarder layout for formatting
		Label.setBorder(new TitledBorder (new EtchedBorder(), ""));
		// Create a button for next player (will add listener)
		JButton nextPlayer = new JButton("Next Player");
		//Create a button for making an accusation
		JButton makeAccusation = new JButton("Make an Accusation");
		panel.setBorder(new TitledBorder (new EtchedBorder(), ""));
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		//Create layout for easy formatting
		FlowLayout layout = new FlowLayout();
		layout.setHgap(50);
		layout.setVgap(10);
		panel.setLayout(layout);
		return panel;
	}

	private JPanel bottomPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 6 elements (label, text)
		panel.setLayout(new GridLayout(1,6));
		JLabel die = new JLabel("Die Roll: " + dieRoll + " ");
		panel.add(die);
		//set a boarder for die
		die.setBorder(new TitledBorder (new EtchedBorder(), ""));
		// Get Guess from user
		JTextField guess = new JTextField("Enter Your Guess Here");
		panel.add(guess);
		//Set a boarder for guess
		guess.setBorder(new TitledBorder (new LineBorder(Color.BLACK), "Guess"));
		// Display Response
		JLabel guessR = new JLabel("<html> Guess Response: &nbsp" + "<br>" + guessResp + " <html>");
		panel.add(guessR);
		//set boarder for Guess Response
		guessR.setBorder(new TitledBorder (new EtchedBorder(), ""));
		//Layout for easy formatting
		FlowLayout layout = new FlowLayout();
		layout.setHgap(50);
		layout.setVgap(10);
		panel.setLayout(layout);
		return panel;
	}

	public static void main(String[] args) {
		// Create a JFrame
		gameGUI gui = new gameGUI();
		// show it
		gui.pack();
		gui.setVisible(true);
	}


}


