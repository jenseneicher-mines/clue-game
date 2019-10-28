package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;
import java.util.Set;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
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
		board.initializeFour();
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
		Player playerSix = listOfPlayers[5];
		assertEquals("Kramp", playerSix.getplayerName());
		assertEquals(Color.WHITE, playerSix.getColor());
		assertTrue(playerSix instanceof HumanPlayer);
		assertEquals(2, playerSix.getRow());
		assertEquals(18, playerSix.getCol());
	}
	
	// Test loading deck of cards
	// Program should have loaded in weapon config, and have lists full of every possible player, weapon and room
	// we will have a list of cards for the Deck
	// check for the correct amount of cards in the deck, the correct number of each type of card, and test the deck has some of the expected cards
	@Test
	public void testDeckCreation() {
		Set<Card> deck = board.getDeck();
		
		// test the total cards in the deck to make sure it is MAX_DECK_SIZE ( MAX_PLAYERS + MAX_WEAPONS + MAX_ROOMS) (21)
		assertEquals(deck.size(), Board.MAX_DECK_SIZE);
		
		// test to make sure we have the correct number of each type of card
		int numPeople = 0;
		int numWeapons = 0;
		int numRooms = 0;
		for ( Card card : deck ) {
			if ( card.getType() == CardType.PERSON ) {
				numPeople++;
			}
			else if ( card.getType() == CardType.WEAPON ) {
				numWeapons++;
			}
			else if ( card.getType() == CardType.ROOM ) {
				numRooms++;
			}
		}
		assertEquals(numPeople, Board.MAX_PLAYERS);
		assertEquals(numWeapons, Board.MAX_WEAPONS);
		assertEquals(numRooms, Board.MAX_ROOMS);
		
		
		// check 1 room, player, and weapon and check if the deck has each of those
		// check if Baldwin is in the deck
		Card testPlayer = new Card( "Baldwin", CardType.PERSON );
		Boolean foundPlayer = false;
		for ( Card card : deck ) {
			if ( card.equals(testPlayer) ) {
				foundPlayer = true;
				break;
			}
		}
		assertTrue( foundPlayer );
		
		
		// check if Library is in the deck
		Card testRoom = new Card("Library", CardType.ROOM);
		Boolean foundRoom = false;
		for ( Card card : deck ) {
			if ( card.equals(testRoom) ) {
				foundRoom = true;
				break;
			}
		}
		assertTrue( foundRoom );
		
		// check if Clicker is in the deck
		Card testWeapon = new Card("Clicker", CardType.WEAPON);
		Boolean foundWeapon = false;
		for ( Card card : deck ) {
			if ( card.equals(testWeapon) ) {
				foundWeapon = true;
				break;
			}
		}
		assertTrue( foundWeapon );
	}
	

}
