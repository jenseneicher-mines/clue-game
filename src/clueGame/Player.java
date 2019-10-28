package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;

public class Player {

	private String playerName;
	private Color color;
	private int row;
	private int col;


	// constructor
	public Player(String playerName, String color, int row, int col) {
		super();
		this.playerName = playerName;
		this.color = convertColor(color.toUpperCase());
		this.row = row;
		this.col = col;
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

}
