/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
	
	public void calcAdjacencies() {
		
	}
	
	public void calctargets(BoardCell cell, int pathLength ) {
		
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
