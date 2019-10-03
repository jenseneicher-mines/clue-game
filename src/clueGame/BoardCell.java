/* Software Engineering: Clue Game
 * Code by: Kai Mizuno and Jensen Eicher
 */

package clueGame;

public class BoardCell {
	//instance variables
	private int row;
	private int column;
	private char initial;
		
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
}
