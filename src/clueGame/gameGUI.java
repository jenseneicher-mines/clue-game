// Jensen Eicher & Kai Mizuno

package clueGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

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
	public static int SCREEN_WIDTH = 550;
	public static int SCREEN_HEIGHT = 900;

	public gameGUI(){
		board = Board.getInstance();
		board.setConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");
		board.initialize();
		board.calcTargets(7,5,1); // TESTING IF THE TARGETS ARE DRAWN CYAN AS EXPECTED
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		// set up the menu
		dNotes = new DetectiveNotes();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		//set up each panel and arrange them in correct order
		//FlowLayout layout = new FlowLayout();
		//layout.setHgap(50);
		//layout.setVgap(250);
		//board.setLayout(layout);
		
		
		add(board, BorderLayout.CENTER);
		add(myCardsPanel(), BorderLayout.EAST);
		add(controlPanel(), BorderLayout.SOUTH);
		//JPanel panel1 = middlePanel();
		//add(panel1);
		//JPanel panel3 = bottomPanel();
		//add(panel3);
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


    private JMenuBar menueBar() {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem notesMenuItem = new JMenuItem("Detective Notes");
        file.add(notesMenuItem);
        setJMenuBar(menu);
        return menu;
    }
    
    public JPanel controlPanel() {
    	
    	JPanel control = new JPanel();
    	control.setLayout(new GridLayout(2,4));
    	
    	JLabel whoseTurn = new JLabel(name);
    	whoseTurn.setBorder(new TitledBorder ( new EtchedBorder(), "Whose turn?"));
		control.add(whoseTurn);
		
		// Create a button for next player (will add listener)
		JButton nextPlayer = new JButton("Next Player");
		control.add(nextPlayer);
		//Create a button for making an accusation
		JButton makeAccusation = new JButton("Make an Accusation");
		control.add(makeAccusation);
		
		// display the die roll
		JLabel die = new JLabel("Roll: " + dieRoll + " ");
		die.setBorder(new TitledBorder ( new EtchedBorder(), "Die"));
		control.add(die);
		
		// Get Guess from user
		JTextField guess = new JTextField("Enter Your Guess Here");
		control.add(guess);
		//Set a boarder for guess
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
    	
		
		// Display Response
		JLabel guessR = new JLabel(guessResp);
		control.add(guessR);
		//set boarder for Guess Response
		guessR.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		
    	return control;
    }
    
    
	public JPanel myCardsPanel() {
		JPanel cards = new JPanel();
		cards.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		cards.setPreferredSize(new Dimension(100, 200));
		cards.setLayout(new GridLayout(3,1));
		
		// get the player's cards
		Set<Card> playerHand = board.getHumanPlayer().getCurrentHand();
		
		// add the person card
		String personCards = "<html>";
		for ( Card c : playerHand ) {
			if ( c.getType() == CardType.PERSON ) {
				personCards = personCards + c.getCardName() + "<br>";
			}
		}
		personCards += "<html>";
		JLabel personList = new JLabel(personCards);
		personList.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		cards.add(personList);
		
		// add the room cards
		String roomCards = "<html>";
		for ( Card c : playerHand ) {
			if ( c.getType() == CardType.ROOM ) {
				roomCards = roomCards + c.getCardName() + "<br>";
			}
		}
		roomCards += "<html>";
		JLabel roomList = new JLabel(roomCards);
		roomList.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		cards.add(roomList);
		
		// add the weapon cards
		String weaponCards = "<html>";
		for ( Card c : playerHand ) {
			if ( c.getType() == CardType.WEAPON ) {
				weaponCards = weaponCards + c.getCardName() + "<br>";
			}
		}
		weaponCards += "<html>";
		JLabel weaponList = new JLabel(weaponCards);
		weaponList.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		cards.add(weaponList);
		
		
		
		return cards;
	}
	
	

	public static void main(String[] args) {
		// Create a JFrame
		gameGUI gui = new gameGUI();
		JOptionPane.showMessageDialog(gui,"Your are " + board.getHumanPlayer().getplayerName() + " press OK then click Next Player to begin", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		// show it
		gui.pack();
		gui.setVisible(true);
	}


}


