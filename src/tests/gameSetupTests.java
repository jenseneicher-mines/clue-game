package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;

import clueGame.Board;
import clueGame.Player;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

public class gameSetupTests {
	
	
	private static Board board;
	@BeforeClass
	public static void setup() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setFourConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Before
	public void setUpTest() {
		
	}

	// Test Loading People
	// Player config should have the format:
	// Name, Color, PlayerType(Human/Computer), xStart, yStart
	// Test to make sure expected Players are loaded in and have the expected parameters
	@Test
	public void testPeople() {
		// get the list of players
		Player[] listOfPlayers = board.getPlayers();
		
		// ensure there is 6 players
		assertEquals(listOfPlayers.length, board.MAX_PLAYERS);
		
		// test 1st player
		Player playerOne = listOfPlayers[0];
		assertEquals("Baldwin", playerOne.getplayerName());
		assertEquals(Color.GREEN, playerOne.getColor());
		assertTrue(playerOne instanceof ComputerPlayer);
		assertEquals(14, playerOne.getRow());
		assertEquals(17, playerOne.getCol());
		
		// test 3rd player
		Player playerThree = listOfPlayers[2];
		assertEquals("Camp", playerThree.getplayerName());
		assertEquals(Color.BLUE, playerThree.getColor());
		assertTrue(playerThree instanceof ComputerPlayer);
		assertEquals(17, playerThree.getRow());
		assertEquals(1, playerThree.getCol());
		
		// test 6th player
		Player playerSix = listOfPlayers[2];
		assertEquals("Kramp", playerSix.getplayerName());
		assertEquals(Color.WHITE, playerSix.getColor());
		assertTrue(playerSix instanceof HumanPlayer);
		assertEquals(2, playerThree.getRow());
		assertEquals(18, playerThree.getCol());
	}

}
