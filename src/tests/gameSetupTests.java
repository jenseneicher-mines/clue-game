package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
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
		board.setConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
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
		// loop through the deck, and update the boolean if one of the 3 cards is found
		// in the end all 3 should be found
		Card testPlayer = new Card( "Baldwin", CardType.PERSON );
		Card testRoom = new Card("Library", CardType.ROOM);
		Card testWeapon = new Card("Clicker", CardType.WEAPON);
		Boolean foundPlayer = false;
		Boolean foundRoom = false;
		Boolean foundWeapon = false;
		for ( Card card : deck ) {
			if ( card.equals(testPlayer) ) {
				foundPlayer = true;
			}
			else if ( card.equals(testRoom) ) {
				foundRoom = true;
			}
			else if ( card.equals(testWeapon) ) {
				foundWeapon = true;
			}
		}
		// check if Baldwin (Player) is in the deck
		assertTrue( foundPlayer );
		// check if Library (room) is in the deck
		assertTrue( foundRoom );
		// check if Clicker (weapon) is in the deck
		assertTrue( foundWeapon );
	}
	
	
	// Test Dealing cards
	// loop through every player, checking each of their cards and populating variables for the following tests
	// Check to make sure each player has roughly the same number of cards
	// Make sure all cards have been delt (counting all player's cards adds up to the deck size)
	// there is no more than 1 instance of each card
	@Test
	public void testDealing() {
		Player[] playerList = board.getPlayers();
		
		int totalCards = 0;
		Map<Card, Integer> instancesOfEachCard = new HashMap<Card, Integer>();  // associating each card with how many instances of the card we find
		
		// variables for checking player card counts being close to each other
		// players should have roughly the same amount of cards, so we need to check to make sure there are at most 2 different amounts of cards in players hands and that they are within 1 of each other
		// Ex: If there are 21 cards and 6 players, the amount of cards any player can have is either 3 or 4. There are only 2 options and they have a difference of 1
		int firstPlayerCardCount = -1; // every player should be with
		int secondPlayerCardCount = -1;
		Boolean playersHaveSameNumberOfCards = true;
		
		// loop through every player, storing information used for all 3 tests
		for ( Player currentPlayer : playerList ) {
			int currentPlayerCardCount = currentPlayer.getCurrentHand().size();
			
			// if it's the first player being checked, store their card count
			if ( firstPlayerCardCount == -1 ) {
				firstPlayerCardCount = currentPlayerCardCount;
			}
			// if we find a second card count, store it
			else if ( secondPlayerCardCount == -1 && currentPlayerCardCount != firstPlayerCardCount ) {
				secondPlayerCardCount = currentPlayerCardCount;
			}
			// check to make sure every difference between our card counts is only 1 or 0
			else if ( secondPlayerCardCount != -1 ) {
				if ( Math.abs( firstPlayerCardCount - secondPlayerCardCount ) > 1 || Math.abs( currentPlayerCardCount - secondPlayerCardCount ) > 1 || Math.abs( currentPlayerCardCount - firstPlayerCardCount ) > 1 ) {
					playersHaveSameNumberOfCards = false;
				}
			}
			
			// loop through each card in the player's deck and populate the map for card instances (for card count check)
			for ( Card currentCard : currentPlayer.getCurrentHand() ) {
				if ( instancesOfEachCard.containsKey(currentCard) ) {
					int count = instancesOfEachCard.get(currentCard);
					instancesOfEachCard.put(currentCard, count++ );
				}
				else {
					instancesOfEachCard.put(currentCard, 1 );
				}
			}
			
			// increment total cards and players checked
			totalCards += currentPlayerCardCount;
		}
		
		// check our boolean for if the players have roughly the same number of cards
		assertTrue(playersHaveSameNumberOfCards);
		
		// make sure totalCards calculated from the previous for loop equals total cards in deck
		assertEquals(totalCards, Board.MAX_DECK_SIZE - Board.CARDS_IN_SOLUTION_HAND);
		
		// loop through our map of card instances, and if any have a value > 1, set boolean to false
		Boolean oneOfEachCard = true;
		for ( Map.Entry<Card, Integer> entry : instancesOfEachCard.entrySet() ) {
			if ( entry.getValue() > 1 ) {
				oneOfEachCard = false;
			}
		}
		assertTrue(oneOfEachCard);
	}
	
	

}
