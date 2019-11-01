package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class gameActionTests {

	private static Board board;
	@BeforeClass
	public static void setUpBeforeClass() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setFourConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");		
		// Initialize will load BOTH config files 
		board.initializeFour();
	}

	// Test the logic behind what space computer players choose to move to
	// Make sure if no rooms can be moved to, or can move to a room they just visited, the computer chooses a random space
	// Test if the computer can move to a room they did not just visit, they move to that room
	@Test
	public void testComputerTargets() {
		ComputerPlayer computerPlayer = (ComputerPlayer) board.getPlayers()[0];
		int currentRow = computerPlayer.getRow();
		int currentCol = computerPlayer.getCol();
		
		// this computer player starts at (14,17), which is not in range of any doors
		// Test: Make sure the computer chooses a random location while it does not have access to any rooms
		board.calcTargets(currentRow, currentCol, 1); // calculate the targets for when the computer moves 1 space (4 options)
		Set<BoardCell> targets = board.getTargets();
		BoardCell startLocation = board.getCellAt(currentRow, currentCol);
		BoardCell upwardCell = board.getCellAt(currentRow - 1, currentCol );
		BoardCell downwardCell = board.getCellAt(currentRow + 1, currentCol );
		BoardCell leftCell = board.getCellAt(currentRow, currentCol - 1);
		BoardCell rightCell = board.getCellAt(currentRow , currentCol + 1 );
		int countMoveUp = 0;
		int countMoveDown = 0;
		int countMoveLeft = 0;
		int countMoveRight = 0;
		for ( int i = 0; i < 200; i++ ) {
			BoardCell newLocation = computerPlayer.getTarget(targets);
			// ensure no other space is chosen
			if ( newLocation != upwardCell && newLocation != downwardCell && newLocation != leftCell && newLocation != rightCell  ) {
				fail("Invalid cell selected");
			}
			// counters to ensure every valid option is chosen
			if ( newLocation == upwardCell ) {
				countMoveUp++;
			}
			else if ( newLocation == downwardCell ) {
				countMoveDown++;
			}

			else if ( newLocation == rightCell ) {
				countMoveRight++;
			}

			else if ( newLocation == leftCell ) {
				countMoveLeft++;
			}
		}
		// ensure each option is randomly chosen some number of times
		int countGoal = 35;
		assert(countMoveUp > countGoal);
		assert(countMoveDown > countGoal);
		assert(countMoveRight > countGoal);
		assert(countMoveLeft > countGoal);
		
		
		// Move the player to a location where they have access to a room
		// Test: Since the player has not visited this room before, make sure the player moves to the room
		computerPlayer.setLocation(18,7); // set location to 18,7 which has a door in the downward cell
		currentRow = computerPlayer.getRow();
		currentCol = computerPlayer.getCol();
		board.calcTargets(currentRow, currentCol, 1); // calculate the targets for when the computer moves 1 space (4 options)
		targets = board.getTargets();
		startLocation = board.getCellAt(currentRow, currentCol);
		BoardCell doorCell = board.getCellAt(currentRow + 1, currentCol );
		for ( int i = 0; i < 200; i++ ) {
			BoardCell newLocation = computerPlayer.getTarget(targets);
			// ensure no other space is chosen
			if ( newLocation != doorCell ) {
				fail("Computer player did not move to a door when it should have");
			}
		}
		
		// Keep the player at the last location, but set the lastVisitedRoom for it to be the kitchen
		// Test: Make sure the player selects a random location since the only door it has access to goes to a room it already visited
		computerPlayer.setLastVisitedRoom( 'K' );
		upwardCell = board.getCellAt(currentRow - 1, currentCol );
		leftCell = board.getCellAt(currentRow, currentCol - 1);
		rightCell = board.getCellAt(currentRow , currentCol + 1 );
		int countMoveDoor = 0;
		countMoveLeft = 0;
		countMoveRight = 0;
		countMoveUp = 0;
		for ( int i = 0; i < 200; i++ ) {
			BoardCell newLocation = computerPlayer.getTarget(targets);
			// ensure no other space is chosen
			if ( newLocation != doorCell && newLocation != upwardCell && newLocation != leftCell && newLocation != rightCell  ) {
				fail("Invalid cell selected");
			}
			// counters to ensure every valid option is chosen
			if ( newLocation == doorCell ) {
				countMoveDoor++;
			}
			else if ( newLocation == upwardCell ) {
				countMoveUp++;
			}

			else if ( newLocation == rightCell ) {
				countMoveRight++;
			}

			else if ( newLocation == leftCell ) {
				countMoveLeft++;
			}
		}
		// ensure each option is randomly chosen some number of times
		countGoal = 35;
		assert(countMoveDoor > countGoal);
		assert(countMoveUp > countGoal);
		assert(countMoveRight > countGoal);
		assert(countMoveLeft > countGoal);
	}
	
	// Test Checking an Accusation
	// Test that expected accusations are correct, have wrong person, weapon, and room
	@Test
	public void testAccusation() {
		// set an expected solution
		String correctPerson = "Baldwin";
		String correctWeapon = "Clicker";
		String correctRoom = "Kitchen";
		board.setSolution( correctPerson, correctWeapon, correctRoom );
		String[] correctSolution = board.getSolution();
		
		// test an accusation that is correct
		assertTrue(board.checkAccusation(correctPerson, correctWeapon, correctRoom));
		// test an accusation with a wrong person
		assertFalse(board.checkAccusation("Strong", correctWeapon, correctRoom));
		// test an accusation with a wrong weapon
		assertFalse(board.checkAccusation(correctPerson, "Keyboard", correctRoom));
		// test an accusation with a wrong room
		assertFalse(board.checkAccusation(correctPerson, correctWeapon, "Hall"));
		
	}

}
