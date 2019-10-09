/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


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
		loadRoomConfig();
		loadBoardConfig();
	}
	
	// read the given txt file and convert the contents to the Map legend
	public void loadRoomConfig() {
		legend = new HashMap<Character, String>();
		
		// read in the file
		FileReader read = null;
		Scanner in = null;
		try {
			read = new FileReader(roomConfigFile);
			in = new Scanner(read);
		}
		catch (FileNotFoundException e){
			System.out.println("File specified was not found:" + e);
		} 
		
		// populate the map based off the first two entries of each line
		String currentLine;
		while( in.hasNext() ) {
			currentLine = in.nextLine();
			String[] words = currentLine.split(", ");
			legend.put( words[0].charAt(0) , words[1]);
		}
		
	}
	
	// read the given csv file for the board and save to the 2D array board
	public void loadBoardConfig() {
		numRows = 0;
		numColumns = 0;
		
		// read in the file
		FileReader read = null;
		Scanner in = null;
		try {
			read = new FileReader(boardConfigFile);
			in = new Scanner(read);
		}
		catch (FileNotFoundException e){
			System.out.println("File specified was not found:" + e);
		}
		
		// go through the file and gather needed information (numRows, numColumns, and the entries)
		String currentLine;
		ArrayList<String> listOfSpaces = new ArrayList<String>();
		while( in.hasNext() ) {
			currentLine = in.nextLine();
			numRows++;
			String[] keys = currentLine.split(",");
			if ( numColumns == 0 )
				numColumns = keys.length;
			for ( String space : keys ) 
				listOfSpaces.add(space);
		}
		
		// make the board array with the known information
		int index = 0;
		board = new BoardCell[numRows][numColumns];
		for ( int row = 0; row < numRows; row++ ) {
			for ( int col = 0; col < numColumns; col++ ) {
				BoardCell currentCell = new BoardCell(row, col, listOfSpaces.get(index));
				board[row][col] = currentCell;
				index++;
			}
		}
		
	}

	// Calculates adjacency list for each grid cell and stores the result in a hashMap adjMatrix
	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();

		//Nested for loop with checks to find eligible cells

		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numColumns; col++) {

				Set<BoardCell> currentAdjSet = new HashSet<BoardCell>();

				if ( col > 0 ) {
					currentAdjSet.add( board[row][col-1] );
				}
				if ( col < numColumns - 1 ) {
					currentAdjSet.add( board[row][col+1] );
				}
				if ( row > 0 ) {
					currentAdjSet.add( board[row-1][col] );
				}
				if ( row < numRows - 1 ) {
					currentAdjSet.add( board[row+1][col] );
				}

				adjMatrix.put(board[row][col], currentAdjSet);
			}
		}

	}

	// Calculate targets that are accessible given a pathLength
	public void calctargets(BoardCell cell, int pathLength ) {
		targets.add(cell);
		for (BoardCell adjCell : adjMatrix.get(cell) ) {
			if ( targets.contains(adjCell) )
				continue;
			targets.add(adjCell);
			if ( pathLength == 1 )
				targets.add(adjCell);
			else
				calctargets(adjCell, pathLength-1);	// else recursively call function
			targets.remove(adjCell);
		}
		targets.remove(cell);
	}
	
	// Getter Functions
	public Map<Character, String> getLegend() {
		return legend;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	
	//Setter Functions
	public void setConfigFiles(String boardCSV, String legendTXT) {
		boardConfigFile = boardCSV;
		roomConfigFile = legendTXT;
	}
	
	
}
