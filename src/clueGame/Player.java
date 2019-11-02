package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {

	private String playerName;
	private Color color;
	private int row;
	private int col;
	private Set<Card> currentHand;
	protected ArrayList<String> peopleNotSeen;
	protected ArrayList<String> weaponsNotSeen;
	protected ArrayList<String> roomsNotSeen;
	protected char lastVisitedRoom;

	// constructor
	public Player(String playerName, String color, int row, int col) {
		super();
		this.playerName = playerName;
		this.color = convertColor(color.toUpperCase());
		this.row = row;
		this.col = col;
		this.currentHand = new HashSet<Card>();
		this.peopleNotSeen = new ArrayList<String>();
		this.weaponsNotSeen = new ArrayList<String>();
		this.roomsNotSeen = new ArrayList<String>();
	}
	
	// when a suggestion is made, a matching card is picked from each player's hand to show to the player who gave the suggestion
	public Card disproveSuggestion( Card[] suggestionList ) {
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		// find all of the matching cards
		for ( Card possibleMatch : suggestionList ) {
			if ( currentHand.contains(possibleMatch) ) {
				matchingCards.add(possibleMatch);
			}
		}
		
		// if there is more than one matching card, return a random card
		int numMatchingCards = matchingCards.size();
		if ( numMatchingCards > 1 ) {
			Random rand = new Random();
			return matchingCards.get(rand.nextInt(numMatchingCards));
		}
		// if there is only 1 matching card, return that card
		else if ( numMatchingCards == 1 ) {
			return matchingCards.get(0);
		}
		// if there is no matching cards, return null
		else {
			return null;
		}
	}

	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}


	// getter functions
	public String getplayerName() {
		return playerName;
	}
	public Color getColor() {
		return color;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public Set<Card> getCurrentHand() {
		return currentHand;
	}
	public char getLastVisitedRoom() {
		return lastVisitedRoom;
	}
	
	// setter functions
	public void addNewCardTohand(Card newCard) {
		currentHand.add(newCard);
	}
	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public void setLastVisitedRoom( char room ) {
		this.lastVisitedRoom = room;;
	}
	public void addToPeopleNotSeen ( String person ) {
		peopleNotSeen.add(person);
	}
	public void addToWeaponsNotSeen ( String weapon ) {
		weaponsNotSeen.add(weapon);
	}
	public void addToRoomsNotSeen ( String room ) {
		roomsNotSeen.add(room);
	}
	// J-Unit functions:
	public void setUnseenPlayersAndWeapons(ArrayList<String> player, ArrayList<String> weapon) {
		this.peopleNotSeen = player;
		this.weaponsNotSeen = weapon;
	}
	public void setCurrentHand(Set<Card> newHand) {
		this.currentHand = newHand;
	}

}
