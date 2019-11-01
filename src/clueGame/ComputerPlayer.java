package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String playerName, String color, int row, int col) {
		super(playerName, color, row, col);
	}
	
	public String[] makeSuggestion() {
		Random rand = new Random();
		String[] suggestion = new String[Board.CARDS_IN_SOLUTION_HAND];
		
		// if there's only 1 unknown person, set it to the suggestion, otherwise pick a random unknown person
		if ( peopleNotSeen.size() == 1 ) {
			suggestion[0] = peopleNotSeen.get(0);
		}
		else {
			suggestion[0] = peopleNotSeen.get(rand.nextInt( peopleNotSeen.size() ));
		}
		
		// if there's only 1 unknown person, set it to the suggestion, otherwise pick a random unknown person
		if ( weaponsNotSeen.size() == 1 ) {
			suggestion[1] = weaponsNotSeen.get(0);
		}
		else {
			suggestion[1] = weaponsNotSeen.get(rand.nextInt( peopleNotSeen.size() ));
		}
		
		// always set the room to be the currently visited room
		suggestion[2] = Character.toString(lastVisitedRoom); 
		
		return suggestion;
	}
	
	// getTarget
	// if no rooms in target list, select random target location
	// if there's a room that was not just visited, go to that room
	// if there's a room that was just visited, target is randomly selected
	public BoardCell getTarget(Set<BoardCell> targets) {
		Boolean foundNewRoom = false;
		BoardCell newLocation = new BoardCell(-1,-1,"X");
		ArrayList<BoardCell> targetListForRandomSelection = new ArrayList<BoardCell>(); // populate an array list with targets so that we can easily pick a random target later
		// loop through the potential targets, looking for if there is a room
		for ( BoardCell potentialTarget : targets ) {
			if ( potentialTarget.isDoorway() && potentialTarget.getInitial() != lastVisitedRoom ) {
				foundNewRoom = true;
				newLocation = potentialTarget;
			}
			targetListForRandomSelection.add(potentialTarget);
		}
		
		// if we found a new room, return it as the new target without finding a new random location
		if ( foundNewRoom ) {
			lastVisitedRoom = newLocation.getInitial();
			return newLocation;
		}
		// otherwise we want to pick a random new location from the targets
		else {
			Random rand = new Random();
			int randomIndex = rand.nextInt(targetListForRandomSelection.size());
			newLocation = targetListForRandomSelection.get(randomIndex);
			lastVisitedRoom = newLocation.getInitial();
			return newLocation;
		}
	}

	

}
