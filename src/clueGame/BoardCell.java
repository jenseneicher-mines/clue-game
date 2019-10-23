/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;

public class BoardCell {
	//instance variables
	private int row;
	private int column;
	private char initial;
	private DoorDirection door;

	//constructor
	public BoardCell(int row, int column, String label ) {
		this.row = row;
		this.column = column;
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
			else if( direction == 'N')
				door = DoorDirection.NONE;
		}
		else {
			door = DoorDirection.NONE;
		}
	}

	// functions
	public boolean isWalkway() {
		if ( initial == 'W' )
			return true;
		return false;
	}

	public boolean isRoom() {
		if ( initial != 'W' && initial != 'X' )
			return true;
		return false;
	}

	public boolean isDoorway() {
		if ( door != DoorDirection.NONE )
			return true;
		return false;
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
