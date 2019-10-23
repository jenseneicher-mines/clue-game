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
	private Set<BoardCell> visited;
	private BoardCell nonExistantCell;
	private BoardCell currentCellFindingTargets;
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
	// initialize function to set up game board
	public void initialize() {
		try {
			nonExistantCell = new BoardCell(-1,-1, "X");
			currentCellFindingTargets = nonExistantCell;
			loadRoomConfig();
			loadBoardConfig();			// calls 3 load functions to properly set up game board
			calcAdjacencies();
		} catch (BadConfigFormatException e) {
			System.out.println("ERROR: " + e);
		}
	}

	// read the given txt file and convert the contents to the Map legend
	public void loadRoomConfig() throws BadConfigFormatException {
		legend = new HashMap<Character, String>();

		// read in the file
		FileReader read = null;
		Scanner in = null;
		try {
			read = new FileReader(roomConfigFile);
			in = new Scanner(read);
		}
		catch (FileNotFoundException e){
			System.out.println("File '" + boardConfigFile + "' was not found" + "ERROR: " + e);   // Catch and display file if incorrect format was used
		}

		// populate the map based off the first two entries of each line
		String currentLine;
		try {
			while (in.hasNext()) {
				currentLine = in.nextLine();
				String[] words = currentLine.split(", ");
				if ( !words[2].equals("Card") && !words[2].equals("Other") ) {
					throw new BadConfigFormatException(roomConfigFile + " contains a room type '" + words[2] + "'. However, the only vaild types are 'Card' or 'Other'");
				}
				legend.put(words[0].charAt(0), words[1]);
			}
		}
		catch (NullPointerException e){
			throw new BadConfigFormatException("This room layout does not match your legend");
		}

	}

	// read the given csv file for the board and save to the 2D array board
	public void loadBoardConfig() throws BadConfigFormatException {
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
			if ( numColumns == 0 ) {
				numColumns = keys.length;
			}
			if(keys.length != numColumns){
				throw new BadConfigFormatException(boardConfigFile + " has inconsistent columns");
			}
			for ( String space : keys ) {
				if (!legend.containsKey(space.charAt(0))) {
					throw new BadConfigFormatException(boardConfigFile + " contains the room '" + space + "', which is not in the legend.");
				}

				listOfSpaces.add(space);
			}
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

				BoardCell currentCell = board[row][col];
				if(!currentCell.equals('X')) {
					// check the tile to the left, add if it's they're both walkways or a rightward door
					// or if the current cell is a leftward door, add the walkway to the left
					if ( col > 0 ) {
						BoardCell leftCell = board[row][col - 1];
						if ( ( currentCell.getInitial() == 'W' && leftCell.getInitial() == 'W' ) || leftCell.getDoorDirection().equals(DoorDirection.RIGHT) || currentCell.getDoorDirection().equals(DoorDirection.LEFT) ) {
							currentAdjSet.add(leftCell);
						}
					}
					// check tile to the right, add if it's the same type of tile or a leftward door
					// or if the current cell is a rightward door, add the walkway to the right
					if ( col < numColumns - 1 ) {
						BoardCell rightCell = board[row][col + 1];
						if ( ( currentCell.getInitial() == 'W' && rightCell.getInitial() == 'W' ) || rightCell.getDoorDirection().equals(DoorDirection.LEFT) || currentCell.getDoorDirection().equals(DoorDirection.RIGHT) )  {
							currentAdjSet.add(rightCell);
						}
					}
					// check the tile above, add if it's the same type of tile or a downward door
					// or if the current cell is an upward door, add the walkway above
					if (row > 0 ) {
						BoardCell aboveCell = board[row - 1][col];
						if (  ( currentCell.getInitial() == 'W' && aboveCell.getInitial() == 'W' ) || aboveCell.getDoorDirection().equals(DoorDirection.DOWN) || currentCell.getDoorDirection().equals(DoorDirection.UP) ) {
							currentAdjSet.add(aboveCell);
						}
					}
					// check the tile below, add if it's the same type of tile or an upward door
					// or if the current cell is a downward door, add the walkway below
					if (row < numRows - 1) {
						BoardCell belowCell = board[row + 1][col];
						if (  ( currentCell.getInitial() == 'W' && belowCell.getInitial() == 'W' ) || belowCell.getDoorDirection().equals(DoorDirection.UP) || currentCell.getDoorDirection().equals(DoorDirection.DOWN) ) {
							currentAdjSet.add(belowCell);
						}
					}

					adjMatrix.put(currentCell, currentAdjSet);
				}
			}
		}

	}

	// Calculate targets that are accessible given a pathLength
	public void calcTargets(int row, int col, int pathLength ) {
		BoardCell currentCell = getCellAt(row,col);
		// if this is the first call of the recursive loop, then reset the target and visited list
		if ( currentCellFindingTargets.equals(nonExistantCell) ) {
			currentCellFindingTargets = currentCell;
			targets = new HashSet<BoardCell>();
			visited = new HashSet<BoardCell>();
		}

		visited.add( currentCell );
		Set<BoardCell> currentAdj = adjMatrix.get(currentCell);
		for (BoardCell adjCell : adjMatrix.get(currentCell) ) {
			if ( visited.contains(adjCell) )
				continue;
			visited.add(adjCell);
			if ( pathLength == 1 || adjCell.isDoorway() )
				targets.add(adjCell);
			else
				calcTargets(adjCell.getRow(), adjCell.getColumn(), pathLength-1);	// else recursively call function
			visited.remove(adjCell);
		}
		visited.remove(currentCell);

		//if we're reached the end of the first call, set it so there is no cell currently finding a target
		if ( currentCell.equals(currentCellFindingTargets) ) {
			currentCellFindingTargets = nonExistantCell;
		}
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
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}

	//Setter Functions
	public void setConfigFiles(String boardCSV, String legendTXT) {
		boardConfigFile = boardCSV;
		roomConfigFile = legendTXT;
	}





}