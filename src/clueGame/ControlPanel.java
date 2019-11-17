package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ControlPanel extends JPanel {
	private String name = "";
	private int dieRoll = 0;
	private String lastGuess;
	private String guessResp = "";
	public static Board board;
	
	// Control Panel Data
	private JLabel whoseTurn;
	private JButton nextPlayer;
	private JButton makeAccusation;
	private JLabel die;
	private JLabel guess;
	private JLabel guessR;
	
	 public ControlPanel() {
	    	
	    	setLayout(new GridLayout(2,4));
	    	
	    	whoseTurn = new JLabel(name);
	    	whoseTurn.setBorder(new TitledBorder ( new EtchedBorder(), "Whose turn?"));
			add(whoseTurn);
			
			// Create a button for next player (will add listener)
			nextPlayer = new JButton("Next Player");
			nextPlayer.addActionListener(new NextPlayerListener());
			add(nextPlayer);
			
			//Create a button for making an accusation
			makeAccusation = new JButton("Make an Accusation");
			add(makeAccusation);
			makeAccusation.addActionListener(new AccusationListener());

			// display the die roll
			die = new JLabel("Roll:");
			die.setBorder(new TitledBorder ( new EtchedBorder(), "Die"));
			add(die);
			
			// Get Guess from user
			guess = new JLabel(lastGuess);
			add(guess);
			//Set a boarder for guess
			guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
	    	
			
			// Display Response
			guessR = new JLabel(guessResp);
			add(guessR);
			//set boarder for Guess Response
			guessR.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
			
	    	board = Board.getInstance();
	    }
	 // Make an action listener that calls the logic for when the Next Player button is pressed
	 // We want to make sure:
	 // if it's the human player's turn, make sure they finished their turn
	 // if we're okay to proceed: determine the next player, roll the die, determine targets, move, and update the gui (if the next player is a computer)
	 private class NextPlayerListener implements ActionListener {
		 public void actionPerformed(ActionEvent e) {
			 // have different logic depending on if the current player is human or computer
			 if ( board.getCurrentPlayer() instanceof HumanPlayer ) {
				 // if it's the human, make sure they finished their turn (the player has moved)
				 if ( board.getHasMoved() ) {
					 // go to the next player
					 board.nextPlayer();
				 }
				 // if they still haven't moved, display an error message
				 else {
					 JOptionPane.showMessageDialog(null, "You have to finish your turn!");
				 }
			 }
			 // if the player is a computer, go to the next player
			 else {
				 // go to the next player
				 board.nextPlayer();
			 }
			 
			 // update the control panel
			 updateControlPanel();
		 }
	 }
	 // Make an action listener that calls the logic for when the human wants to make an accusation
	 private class AccusationListener implements ActionListener {
		 public void actionPerformed(ActionEvent e) {
			 // call functions
			 //board.openGuessDialog();
			 board.openAccusationDialog();
			 updateControlPanel();
		 }
	 }
	 
	 private void updateControlPanel() {
		 // get the new information
		 this.name = board.getCurrentPlayer().getplayerName();
		 this.dieRoll = board.getDieRoll();
		 this.lastGuess = board.getLastGuess();
		 this.guessResp = board.getGuessResponse();
		 
		 // update the JPanel
		 whoseTurn.setText(name);
		 die.setText("Roll: " + Integer.toString(dieRoll));
		 guessR.setText(guessResp);
		 guess.setText(lastGuess);
	 }
	 

	 
	
}
