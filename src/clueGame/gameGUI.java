// Jensen Eicher & Kai Mizuno

package clueGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private DetectiveNotes dNotes;

	public gameGUI(){
		board = Board.getInstance();
		board.setConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");
		board.initialize();
		board.calcTargets(7,5,1); // TESTING IF THE TARGETS ARE DRAWN CYAN AS EXPECTED
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(new Dimension(550, 900));
		
		// set up the menu
		dNotes = new DetectiveNotes();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		//set up each panel and arrange them in correct order
		//setLayout(new GridLayout(3,1));
		FlowLayout layout = new FlowLayout();
		layout.setHgap(50);
		layout.setVgap(250);
		board.setLayout(layout);
		add(board, BorderLayout.NORTH);
		//myCardsPanel c = new myCardsPanel();
		//add(c, BorderLayout.EAST);
		JPanel panel1 = middlePanel();
		add(panel1, BorderLayout.CENTER);
		JPanel panel3 = bottomPanel();
		add(panel3, BorderLayout.SOUTH);
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	private JMenuItem createDetectiveNotesItem() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dNotes.setVisible(true);
			}
			
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		}
		item.addActionListener(new MenuItemListener());
		return item;
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
	
	public class myCardsPanel extends JPanel {
		public myCardsPanel() {
			setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		}
	}
	

	public static void main(String[] args) {
		// Create a JFrame
		gameGUI gui = new gameGUI();
		// show it
		gui.pack();
		gui.setVisible(true);
	}


}


