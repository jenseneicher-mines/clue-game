package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {

	public DetectiveNotes() {

		Board board = Board.getInstance();
		setTitle("Detective Notes");
		setSize(500,500);
		setLayout(new GridLayout(1,2));
		
		// SET UP THE LEFT SIDE: THE CHECK BOXES
		JPanel checkBoxes = new JPanel();
		checkBoxes.setLayout(new GridLayout(3,1));
		
		// Create the people panel
		JPanel peopleOptions = new JPanel();
		peopleOptions.setBorder(new TitledBorder ( new EtchedBorder(), "People"));
		Player[] playerList = board.getPlayers();
		for ( Player person : playerList ) {
			JCheckBox personOption = new JCheckBox(person.getplayerName());
		    peopleOptions.add(personOption);
		}
		checkBoxes.add(peopleOptions);
		
		// Create the rooms panel
		JPanel roomOptions = new JPanel();
		roomOptions.setBorder(new TitledBorder ( new EtchedBorder(), "Rooms"));
		String[] rooms = board.getRooms();
		for ( String room : rooms ) {
			JCheckBox roomOption = new JCheckBox(room);
		    roomOptions.add(roomOption);
		}
		checkBoxes.add(roomOptions);
		
		// Create the rooms panel
		JPanel weaponOptions = new JPanel();
		weaponOptions.setBorder(new TitledBorder ( new EtchedBorder(), "Weapons"));
		String[] weapons = board.getWeapons();
		for ( String weapon : weapons ) {
			JCheckBox weaponOption = new JCheckBox(weapon);
		    weaponOptions.add(weaponOption);
		}
		checkBoxes.add(weaponOptions);
		
		// SET UP THE RIGHT SIDE: THE COMBO BOXES
		// make a JComboBox for players, rooms, and weapons
		JComboBox<String> playerCombo = new JComboBox<String>();
		playerCombo.addItem("Uncertain");
		for ( Player player : playerList ) {
			playerCombo.addItem(player.getplayerName());
		}
		JComboBox<String> roomCombo = new JComboBox<String>();
		roomCombo.addItem("Uncertain");
		for ( String room : rooms ) {
			roomCombo.addItem(room);
		}
		JComboBox<String> weaponCombo = new JComboBox<String>();
		weaponCombo.addItem("Uncertain");
		for ( String weapon : weapons ) {
			weaponCombo.addItem(weapon);
		}
		
		// Set up the JPanel for the guesses
		JPanel userGuesses = new JPanel();
		userGuesses.setLayout(new GridLayout(3,1));
		
		JPanel personGuess = new JPanel();
		personGuess.setBorder(new TitledBorder ( new EtchedBorder(), "Person Guess"));
		personGuess.add(playerCombo);
		
		JPanel roomGuess = new JPanel();
		roomGuess.setBorder(new TitledBorder ( new EtchedBorder(), "Room Guess"));
		roomGuess.add(roomCombo);
		
		JPanel weaponGuess = new JPanel();
		weaponGuess.setBorder(new TitledBorder ( new EtchedBorder(), "Weapon Guess"));
		weaponGuess.add(weaponCombo);
		
		userGuesses.add(personGuess);
		userGuesses.add(roomGuess);
		userGuesses.add(weaponGuess);
		
		add(checkBoxes, BorderLayout.WEST);
		add(userGuesses, BorderLayout.EAST);
		
	}
	
}
