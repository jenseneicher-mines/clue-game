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
import javax.swing.plaf.basic.BasicOptionPaneUI;

public class gameGUI extends JFrame {
	private static Board board;
	private DetectiveNotes dNotes;
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 900;

	public gameGUI(){
		board = Board.getInstance();
		board.setConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");
		board.initialize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		// set up the menu
		dNotes = new DetectiveNotes();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		add(board, BorderLayout.CENTER);
		add(myCardsPanel(), BorderLayout.EAST);
		ControlPanel cp = new ControlPanel();
		cp.setPreferredSize(new Dimension(board.getBoardWidth(), SCREEN_HEIGHT - board.getBoardHeight()));
		add(cp, BorderLayout.SOUTH);
		board.setControlPanel(cp);
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

	public JPanel myCardsPanel() {
		JPanel cards = new JPanel();
		cards.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		cards.setPreferredSize(new Dimension(SCREEN_WIDTH - board.getBoardWidth(), board.getBoardHeight()));
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

	private class GameControl implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if(actionEvent.getSource() == "Next Player");{
				//call functions
				board.setHasMoved(false);
			}
			if(actionEvent.getSource() == "Make an Accusation");{
				//call functions
			}
		}
	}

	public static void main(String[] args) {
		// Create a JFrame
		gameGUI gui = new gameGUI();
		JOptionPane.showMessageDialog(gui,"Your are " + board.getHumanPlayer().getplayerName() + " (" + board.getHumanPlayer().getColorName() + "), press OK then click Next Player to begin", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		// show it
		gui.pack();
		gui.setVisible(true);
	}


}


