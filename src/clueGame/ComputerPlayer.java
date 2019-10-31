package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String playerName, String color, int row, int col) {
		super(playerName, color, row, col);
	}
	
	// getTarget
	// if no rooms in target list, select random target location
	// if there's a room that was not just visited, go to that room
	// if there's a room that was just visited, target is randomly selected
	public BoardCell getTarget(Set<BoardCell> targets) {
		return null;
	}

}
