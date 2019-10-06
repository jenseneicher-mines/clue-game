/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Board {

	public static final int MAX_BOARD_SIZE = 50;
	//instance variables
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
	}
	
	public void loadRoomConfig() {
		try {
			FileReader read = new FileReader("Rooms.txt");
			BufferedReader buffRead = new BufferedReader(read);
			String str;
			int i = 0;
			int j = 0;
			int k = 0;
			while((str = buffRead.readLine()) != null) {
				String[] character = str.split(",");
				//if(character[0])
				//	board[i][j] = character[0];

			}
		}
		catch (FileNotFoundException e){
			System.out.println("File specified was not found:" + e);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calctargets(BoardCell cell, int pathLength ) {
		
	}
	
	// Getter Functions
	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
		return legend;
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}
	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return board[i][j];
	}
	
	//Setter Functions
	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
	
}
