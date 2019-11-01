package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Player {

	private String playerName;
	private Color color;
	private int row;
	private int col;
	private Set<Card> currentHand;
	protected char lastVisitedRoom;

	// constructor
	public Player(String playerName, String color, int row, int col) {
		super();
		this.playerName = playerName;
		this.color = convertColor(color.toUpperCase());
		this.row = row;
		this.col = col;
		this.currentHand = new HashSet<Card>();
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

}
