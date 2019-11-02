package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {

	private static Board board;
	private static ComputerPlayer computerPlayer1;
	private static Card computer1_PersonCard;
	private static Card computer1_WeaponCard;
	private static Card computer1_RoomCard;
	private static Card personCard;
	private static Card roomCard;
	private static Card weaponCard;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setFourConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");		
		// Initialize will load BOTH config files 
		board.initializeFour();
		
		// initialize some cards we'll use multiple times for tests
		computer1_PersonCard = new Card( "Baldwin", CardType.PERSON);
		computer1_WeaponCard = new Card( "Clicker", CardType.WEAPON );
		computer1_RoomCard = new Card( "Kitchen", CardType.ROOM );
		personCard = new Card("Strong", CardType.PERSON);
		roomCard = new Card("Den", CardType.ROOM);
		weaponCard = new Card("Red Pen", CardType.WEAPON);
		
		// Make a computer player we'll be reusing a lot for tests
		Set<Card> knownHand = new HashSet<Card>();
		computerPlayer1 = (ComputerPlayer) board.getPlayers()[0];
		knownHand.add(  computer1_PersonCard );
		knownHand.add( computer1_WeaponCard );
		knownHand.add( computer1_RoomCard );
		computerPlayer1.setCurrentHand(knownHand);
	}

	// Test the logic behind what space computer players choose to move to
	// Make sure if no rooms can be moved to, or can move to a room they just visited, the computer chooses a random space
	// Test if the computer can move to a room they did not just visit, they move to that room
	@Test
	public void testComputerTargets() {
		int currentRow = computerPlayer1.getRow();
		int currentCol = computerPlayer1.getCol();
		
		// this computer player starts at (14,17), which is not in range of any doors
		// Test: Make sure the computer chooses a random location while it does not have access to any rooms
		board.calcTargets(currentRow, currentCol, 1); // calculate the targets for when the computer moves 1 space (4 options)
		Set<BoardCell> targets = board.getTargets();
		BoardCell upwardCell = board.getCellAt(currentRow - 1, currentCol );
		BoardCell downwardCell = board.getCellAt(currentRow + 1, currentCol );
		BoardCell leftCell = board.getCellAt(currentRow, currentCol - 1);
		BoardCell rightCell = board.getCellAt(currentRow , currentCol + 1 );
		int countMoveUp = 0;
		int countMoveDown = 0;
		int countMoveLeft = 0;
		int countMoveRight = 0;
		for ( int i = 0; i < 200; i++ ) {
			BoardCell newLocation = computerPlayer1.pickLocation(targets);
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
		int countGoal = 20;
		assert(countMoveUp > countGoal);
		assert(countMoveDown > countGoal);
		assert(countMoveRight > countGoal);
		assert(countMoveLeft > countGoal);
		
		
		// Move the player to a location where they have access to a room
		// Test: Since the player has not visited this room before, make sure the player moves to the room
		computerPlayer1.setLocation(18,7); // set location to 18,7 which has a door in the downward cell
		currentRow = computerPlayer1.getRow();
		currentCol = computerPlayer1.getCol();
		board.calcTargets(currentRow, currentCol, 1); // calculate the targets for when the computer moves 1 space (4 options)
		targets = board.getTargets();
		BoardCell doorCell = board.getCellAt(currentRow + 1, currentCol );
		for ( int i = 0; i < 200; i++ ) {
			BoardCell newLocation = computerPlayer1.pickLocation(targets);
			// ensure no other space is chosen
			if ( newLocation != doorCell ) {
				fail("Computer player did not move to a door when it should have");
			}
		}
		
		// Keep the player at the last location, but set the lastVisitedRoom for it to be the kitchen
		// Test: Make sure the player selects a random location since the only door it has access to goes to a room it already visited
		computerPlayer1.setLastVisitedRoom( "Kitchen" );
		upwardCell = board.getCellAt(currentRow - 1, currentCol );
		leftCell = board.getCellAt(currentRow, currentCol - 1);
		rightCell = board.getCellAt(currentRow , currentCol + 1 );
		int countMoveDoor = 0;
		countMoveLeft = 0;
		countMoveRight = 0;
		countMoveUp = 0;
		for ( int i = 0; i < 200; i++ ) {
			BoardCell newLocation = computerPlayer1.pickLocation(targets);
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
		countGoal = 20;
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
		Solution correctSolution = new Solution(personCard, roomCard, weaponCard);
		Solution otherSolution = correctSolution;
		board.setSolution( correctSolution );
		
		// test an accusation that is correct
		assertTrue(board.checkAccusation( otherSolution ));
		// test an accusation with a wrong person
		otherSolution.person = computer1_PersonCard;
		assertTrue(board.checkAccusation( otherSolution ));
		// test an accusation with a wrong room
		otherSolution = correctSolution;
		otherSolution.room = computer1_RoomCard;
		assertTrue(board.checkAccusation( otherSolution ));
		// test an accusation with a wrong weapon
		otherSolution = correctSolution;
		otherSolution.weapon = computer1_WeaponCard;	
		assertTrue(board.checkAccusation( otherSolution ));
		
	}
	
	// Test having the computer create a suggestion
	@Test
	public void testComputerSuggestion() {
		computerPlayer1.setLastVisitedRoom("Kitchen");
		
		// TEST: Make sure expected suggestions are made with multiple people/weapons still not seen
		// loop with initial sets of unknown cards (everything but the player's hand)
		// make sure the current room is always picked, and all the other unknowns are picked evenly
		Map<Card, Integer> suggestionsFound = new HashMap<Card,Integer>();
		for ( int i = 0; i < 200; i++ ) {
			Solution suggestionList = computerPlayer1.createSuggestion();
			Card personSuggested = suggestionList.person;
			Card weaponSuggested = suggestionList.weapon;
			// add the found person suggestion to the map to keep track of how many times it's picked
			if ( suggestionsFound.containsKey(personSuggested) ) {
				suggestionsFound.put(personSuggested, suggestionsFound.get(personSuggested)+1);
			}
			else {
				suggestionsFound.put(personSuggested, 1);
			}
			// add the found weapon suggestion to the map to keep track of how many times it's picked
			if ( suggestionsFound.containsKey(weaponSuggested) ) {
				suggestionsFound.put(weaponSuggested, suggestionsFound.get(weaponSuggested)+1);
			}
			else {
				suggestionsFound.put(weaponSuggested, 1);
			}
			
			// make sure the room is the one the player is in
			String playerRoom = computerPlayer1.getLastVisitedRoom();
			if ( playerRoom != suggestionList.room.getCardName() ) {
				fail("The room the player is ('" + playerRoom +"') was not chosen for the suggestion. " + suggestionList.room + " was incorrectly chosen.");
			}
		}
		// check each person and weapon not seen before to make sure they have all been randomly chosen evenly
		for ( Map.Entry<Card, Integer> suggestion : suggestionsFound.entrySet() ) {
			assertTrue( suggestion.getValue() > 15 );
		}
		
		// TEST: Make sure when there is only 1 person and 1 weapon not seen, it is selected
		// set the unseen sets in Player to be sets with only 1 value in them
		ArrayList<Card> unseenPerson = new ArrayList<Card>();
		unseenPerson.add(personCard);
		ArrayList<Card> unseenWeapon = new ArrayList<Card>();
		unseenWeapon.add(weaponCard);
		computerPlayer1.setUnseenPlayersAndWeapons(unseenPerson, unseenWeapon);
		// make sure every time a suggestion is made, the expected person and weapon is selected
		for ( int i = 0; i < 200; i++ ) {
			Solution suggestionList = computerPlayer1.createSuggestion();
			if ( !suggestionList.person.equals(personCard) ) {
				fail("Even though there is only 1 unseen person, that person was not selected for the suggestion.");
			}
			if ( !suggestionList.weapon.equals(weaponCard) ) {
				fail("Even though there is only 1 unseen person, that person was not selected for the suggestion.");
			}
		}
	}
	
	
	// test having players disprove a suggestion
	// depending on how many cards the player has of the suggested set, the player will return the appropriate card
	@Test
	public void testDisprove() {
		
		// TEST that the card returned when there is only 1 matching card is the matching card
		Solution oneMatchingCard = new Solution(computer1_PersonCard, roomCard, weaponCard) ;
		Card disprovedCard = computerPlayer1.disproveSuggestion( oneMatchingCard );
		assertTrue( computer1_PersonCard.equals(disprovedCard) );
		
		// TEST that when there are multiple matching cards, a random one of the matching cards is returned
		Solution multipleMatchingCards = new Solution( computer1_PersonCard, roomCard, computer1_WeaponCard );
		int personCardCount = 0;
		int weaponCardCount = 0;
		int otherCardCount = 0; // this one should remain 0 since the 3rd card being passed is not is the suggested set
		for ( int i = 0; i < 200; i++ ) {
			disprovedCard = computerPlayer1.disproveSuggestion( multipleMatchingCards );
			if ( computer1_PersonCard.equals(disprovedCard) ) {
				personCardCount++;
			}
			else if ( computer1_WeaponCard.equals(disprovedCard) ) {
				weaponCardCount++;
			}
			else {
				otherCardCount++;
			}
		}
		assertTrue( personCardCount > 50 );
		assertTrue( weaponCardCount > 50 );
		assertTrue( otherCardCount == 0 );
		
		// TEST that when there are no matching cards, null is returned
		Solution noMatchingCards = new Solution ( personCard, roomCard, weaponCard );
		disprovedCard = computerPlayer1.disproveSuggestion( noMatchingCards );
		assertEquals( disprovedCard, null );
	}
	
	// make sure the behavior to handle a suggestion is working correctly
	// The board function handleSuggestion passes in a solution has the player create a suggestion, then makes each other player respond in order until a card is found
	// make sure the expected card is returned for each handling (or null if that's expected)
	@Test
	public void testHandlingSuggestion() {
		// create a few players with known cards
		Player humanPlayer = (HumanPlayer) board.getPlayers()[5];
		Set<Card> knownHand = new HashSet<Card>();
		knownHand.add(  personCard );
		knownHand.add( weaponCard );
		knownHand.add( roomCard );
		humanPlayer.setCurrentHand(knownHand);

		// create 3 more cards for the 3rd player
		Card computer2_PersonCard = new Card( "Camp", CardType.PERSON);
		Card computer2_WeaponCard = new Card( "Disappointment", CardType.WEAPON );
		Card computer2_RoomCard = new Card( "Porch", CardType.ROOM );
		
		Player computerPlayer2 = (ComputerPlayer) board.getPlayers()[1];
		knownHand.clear();
		knownHand.add(  computer2_PersonCard );
		knownHand.add( computer2_WeaponCard );
		knownHand.add( computer2_RoomCard );
		computerPlayer2.setCurrentHand(knownHand);
		
		// set the board to only have these 3 players
		board.setPlayers(new Player[] { humanPlayer, computerPlayer1, computerPlayer2 });
		
		// create 3 cards that nobody can disprove
		Card unknownPerson = new Card( "Dave", CardType.PERSON);
		Card unknownRoom = new Card( "Tackroom", CardType.ROOM );
		Card unknownWeapon = new Card( "Keyboard", CardType.WEAPON );
		
		
		// TEST: Have the board handle a suggestion that no players can disprove (returns null)
		board.setCurrentPlayer(0); // set the current player to be human so they are the one making the suggestion
		Solution suggestion = new Solution(unknownPerson, unknownRoom, unknownWeapon);
		Card returnedCard = board.handleSuggestion( suggestion );
		assertEquals( returnedCard, null );
		
		// TEST: handle a suggestion where only the human player can disprove while they are making the suggestion (returns null)
		suggestion = new Solution(personCard, unknownRoom, unknownWeapon);
		returnedCard = board.handleSuggestion( suggestion );
		assertEquals( returnedCard, null );
		
		// TEST: handle a suggestion where both the computers can disprove when the player makes the suggestion (computer player 1's card should be returned)
		suggestion = new Solution(computer1_PersonCard, computer2_RoomCard, unknownWeapon);
		returnedCard = board.handleSuggestion( suggestion );
		assertEquals( returnedCard, computer1_PersonCard );
		
		// TEST: handle a suggestion where only the accusing computer player can disprove (returns null)
		board.setCurrentPlayer(1); // set the current player to be computer1 so they are the one making the suggestion
		suggestion = new Solution(computer1_PersonCard, unknownRoom, unknownWeapon);
		returnedCard = board.handleSuggestion( suggestion );
		assertEquals( returnedCard, null );
		
		// TEST: handle a suggestion only the human can disprove, with a computer as the current player (returns the human's card)
		suggestion = new Solution(personCard, unknownRoom, unknownWeapon);
		returnedCard = board.handleSuggestion( suggestion );
		assertEquals( returnedCard, personCard );
		
		// TEST: handle a suggestion where computer 2 makes a suggestion that both computer 1 and the human can disprove (human's card should be returned)
		board.setCurrentPlayer(2); // set the current player to be computer2 so they are the one making the suggestion
		suggestion = new Solution(computer1_PersonCard, roomCard, unknownWeapon);
		returnedCard = board.handleSuggestion( suggestion );
		assertEquals( returnedCard, roomCard );
		
	}

}
