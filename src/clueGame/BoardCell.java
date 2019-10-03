/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;

public class BoardCell {
	//instance variables
	private int row;
	private int column;
	private char initial;
	
	//constructor
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	// functions
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}

	
	// getter functions
	
	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getInitial() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
