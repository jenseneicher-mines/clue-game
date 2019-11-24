package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {

	private String playerName;
	private String colorName;
	private Color color;
	private int row;
	private int column;
	private int pixelRow;
	private int pixelColumn;
	private Set<Card> currentHand;
	protected ArrayList<Card> peopleNotSeen;
	protected ArrayList<Card> weaponsNotSeen;
	protected ArrayList<Card> roomsNotSeen;
	protected String lastVisitedRoom = "X";

	// constructor
	public Player(String playerName, String color, int row, int column) {
		super();
		this.playerName = playerName;
		this.colorName = color;
		this.color = convertColor(color.toUpperCase());
		this.row = row;
		this.column = column;
		this.pixelRow = BoardCell.PIXEL_SIZE_OF_CELL * row;
		this.pixelColumn = BoardCell.PIXEL_SIZE_OF_CELL * column;
		this.lastVisitedRoom = "X";
		this.currentHand = new HashSet<Card>();
		this.peopleNotSeen = new ArrayList<Card>();
		this.weaponsNotSeen = new ArrayList<Card>();
		this.roomsNotSeen = new ArrayList<Card>();
	}
	
	// when a suggestion is made, a matching card is picked from each player's hand to show to the player who gave the suggestion
	public Card disproveSuggestion( Solution suggestionList ) {
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		// find all of the matching cards
		for ( Card card : currentHand ) {
			if ( card.equals(suggestionList.person) ) {
				matchingCards.add(card);
			}
			else if ( card.equals(suggestionList.room) ) {
				matchingCards.add(card);
			}
			else if ( card.equals(suggestionList.weapon) ) {
				matchingCards.add(card);
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
	
	// Draw function so the player is shown on screen
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval(pixelColumn, pixelRow, BoardCell.PIXEL_SIZE_OF_CELL, BoardCell.PIXEL_SIZE_OF_CELL);
		g.setColor(Color.BLACK);
		g.drawOval(pixelColumn, pixelRow, BoardCell.PIXEL_SIZE_OF_CELL, BoardCell.PIXEL_SIZE_OF_CELL);
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
	public String getColorName() {
		return colorName;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return column;
	}
	public Set<Card> getCurrentHand() {
		return currentHand;
	}
	public String getLastVisitedRoom() {
		return lastVisitedRoom;
	}
	
	// setter functions
	public void addNewCardTohand(Card newCard) {
		currentHand.add(newCard);
	}
	public void setLocation(int row, int col) {
		this.row = row;
		this.column = col;
		this.pixelRow = BoardCell.PIXEL_SIZE_OF_CELL * row;
		this.pixelColumn = BoardCell.PIXEL_SIZE_OF_CELL * column;
	}
	public void setLastVisitedRoom( String room ) {
		this.lastVisitedRoom = room;;
	}
	public void addToPeopleNotSeen ( Card person ) {
		peopleNotSeen.add(person);
	}
	public void addToWeaponsNotSeen ( Card weapon ) {
		weaponsNotSeen.add(weapon);
	}
	public void addToRoomsNotSeen ( Card room ) {
		roomsNotSeen.add(room);
	}
	// J-Unit functions:
	public void setUnseenPlayersAndWeapons(ArrayList<Card> player, ArrayList<Card> weapon) {
		this.peopleNotSeen = player;
		this.weaponsNotSeen = weapon;
	}
	public void setCurrentHand(Set<Card> newHand) {
		this.currentHand = newHand;
	}

}
