/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;

import java.awt.Graphics;

import java.awt.Color;

public class BoardCell {
	public static final int PIXEL_SIZE_OF_CELL = 10;
	
	//instance variables
	private int row;
	private int column;
	private int pixelRow;
	private int pixelColumn;
	private char initial;
	private DoorDirection door;

	//constructor
	public BoardCell(int row, int column, String label ) {
		this.row = row;
		this.pixelRow = this.row * PIXEL_SIZE_OF_CELL;
		this.column = column;
		this.pixelColumn = this.column * PIXEL_SIZE_OF_CELL;
		this.initial = label.charAt(0);

		if ( label.length() > 1 ) {
			char direction = label.charAt(1);
			if ( direction == 'U' ) {
				door = DoorDirection.UP;
			}
			else if ( direction == 'D' ) {
				door = DoorDirection.DOWN;
			}
			else if ( direction == 'L' ) {
				door = DoorDirection.LEFT;
			}
			else if ( direction == 'R' ) {
				door = DoorDirection.RIGHT;
			}
			else {
				door = DoorDirection.NONE;
			}
		}
		else {
			door = DoorDirection.NONE;
		}
	}

	// functions
	public boolean isWalkway() {
		if ( initial == 'W' ) {
			return true;
		}
		return false;
	}

	public boolean isRoom() {
		if ( initial != 'W' && initial != 'X' ) {
			return true;
		}
		return false;
	}

	public boolean isDoorway() {
		if ( door != DoorDirection.NONE ) {
			return true;
		}
		return false;
	}
	public void draw(Graphics g) {
		// Draw a filled rectangle depending on cell status
		if ( !isWalkway() ) {
			g.setColor(Color.GRAY);
		} 
		else {
			g.setColor(Color.YELLOW);
		}
		g.fillRect(pixelColumn, pixelRow, PIXEL_SIZE_OF_CELL, PIXEL_SIZE_OF_CELL);
		if ( isWalkway() ) {
			g.setColor(Color.BLACK);
		}
		g.drawRect(pixelColumn, pixelRow, PIXEL_SIZE_OF_CELL, PIXEL_SIZE_OF_CELL);
		
		
		
		// draw door direction
		if ( isDoorway() ) {
			double doorWidth = 5/10;
			g.setColor(Color.BLUE);
			if ( door == DoorDirection.DOWN ) {
				//g.fillRect((int) (pixelRow + (1 - doorWidth)*PIXEL_SIZE_OF_CELL), pixelColumn, PIXEL_SIZE_OF_CELL, (int) (doorWidth)*PIXEL_SIZE_OF_CELL);
				g.fillRect(pixelColumn, pixelRow, PIXEL_SIZE_OF_CELL, PIXEL_SIZE_OF_CELL);
			}
			else if ( door == DoorDirection.LEFT ) {
				g.fillRect( pixelColumn , pixelRow, (int) doorWidth*PIXEL_SIZE_OF_CELL, PIXEL_SIZE_OF_CELL);
			}
			else if (door == DoorDirection.RIGHT) {
				g.fillRect( pixelColumn , (int) (pixelRow + (1 - doorWidth)*PIXEL_SIZE_OF_CELL), (int) doorWidth*PIXEL_SIZE_OF_CELL, PIXEL_SIZE_OF_CELL);
			}
			else {
				g.fillRect(pixelColumn, pixelRow, PIXEL_SIZE_OF_CELL, (int) (doorWidth)*PIXEL_SIZE_OF_CELL);
			}
		}
	}


	// getter functions
	public DoorDirection getDoorDirection() {
		return door;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public char getInitial() {
		return initial;
	}

}
