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
	//constants
	public static final int MAX_BOARD_SIZE = 50;
	public static final int MAX_PLAYERS = 6;
	public static final int MAX_WEAPONS = 6;
	public static final int MAX_ROOMS = 9;
	public static final int MAX_DECK_SIZE = MAX_PLAYERS + MAX_WEAPONS + MAX_ROOMS;
	public static final int CARDS_IN_SOLUTION_HAND = 3;
	
	//instance variables
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Player[] playerList;
	private String[] weaponList;
	private String[] roomList;
	private Set<Card> deck;
	private Solution correctSolution;
	private int currentPlayer;
	
	// calcTargets variables
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private BoardCell nonExistantCell;
	private BoardCell currentCellFindingTargets;
	
	// config variables
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;

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
		
		nonExistantCell = new BoardCell(-1,-1, "X");
		currentCellFindingTargets = nonExistantCell;
		currentPlayer = 0;
		loadConfigFiles();
		calcAdjacencies();
		
	}
	public void initializeFour() {
		nonExistantCell = new BoardCell(-1,-1, "X");
		currentCellFindingTargets = nonExistantCell;
		loadFourConfigs();
		calcAdjacencies();
		createDeck();
		dealCards();
	}
	
	// load function that calls all load config functions
	public void loadConfigFiles() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		}
		catch ( BadConfigFormatException e ) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	public void loadFourConfigs() {
		loadConfigFiles();
		try {
			loadPlayerConfig();
			loadWeaponConfig();
		}
		catch (BadConfigFormatException e ) {
			System.out.println("ERROR: " + e.getMessage());
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
			System.out.println("File '" + roomConfigFile + "' was not found" + "ERROR: " + e);   // Catch and display file if incorrect format was used
		}

		// populate the map based off the first two entries of each line and create room list
		String currentLine;
		roomList = new String[MAX_ROOMS];
		int index = 0;
		try {
			while (in.hasNext()) {
				currentLine = in.nextLine();
				String[] words = currentLine.split(", ");
				if ( !words[2].equals("Card") && !words[2].equals("Other") ) {
					throw new BadConfigFormatException(roomConfigFile + " contains a room type '" + words[2] + "'. However, the only vaild types are 'Card' or 'Other'");
				}
				legend.put(words[0].charAt(0), words[1]);
				if ( index < MAX_ROOMS ) {
					roomList[index] = words[1];
				}
				index++;
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
	
	
	// load the player config into an array containing players
	public void loadPlayerConfig() {
		playerList = new Player[MAX_PLAYERS];
		
		// read in the file
		FileReader read = null;
		Scanner in = null;
		try {
			read = new FileReader(playerConfigFile);
			in = new Scanner(read);
		}
		catch (FileNotFoundException e){
			System.out.println("File '" + playerConfigFile + "' was not found" + "ERROR: " + e);   // Catch and display file if incorrect format was used
		}
		
		// loop through the config file making new players
		String currentLine;
		int index = 0;
		while (in.hasNext()) {
			currentLine = in.nextLine();
			String[] words = currentLine.split(", ");
			
			String name = words[0];
			String color = words[1];
			String playerType = words[2];
			int row = Integer.parseInt(words[3]);
			int col = Integer.parseInt(words[4]);
			// make a new player based off the given type
			Player newPlayer;
			if ( playerType.equals("Computer") ) {
				newPlayer = new ComputerPlayer(name, color, row, col);
			} else {
				newPlayer = new HumanPlayer(name,color,row,col);
			}
			
			// add to player list
			playerList[index] = newPlayer;
			index++;
			
		}
		
	}
	
	// load the weapon config into an array containing all the weapons
	public void loadWeaponConfig() throws BadConfigFormatException {
		weaponList = new String[MAX_WEAPONS];
		FileReader read = null;
		Scanner in = null;
		try {
			read = new FileReader(weaponConfigFile);
			in = new Scanner(read);
		}
		catch (FileNotFoundException e){
			System.out.println("File '" + weaponConfigFile + "' was not found" + "ERROR: " + e);   // Catch and display file if incorrect format was used
		}

		// create a list of weapons from given config file
		int index = 0;
		try {
			while (in.hasNext()) {
				String words = in.nextLine();
				if ( words.length() == 0 ) {
					throw new BadConfigFormatException(weaponConfigFile + " has incorrect weapons ");
				}
				weaponList[index] = words;
				index++;
			}
		}
		catch (NullPointerException e){
			throw new BadConfigFormatException("This weapon file has incorrect format");
		}
	}

	// create a deck of cards containing players, rooms, and weapons
	public void createDeck(){
		deck = new HashSet<Card>();
		
		// loop through all the players creating cards of them
		for ( Player person : playerList ) {
			Card newPerson = new Card( person.getplayerName(), CardType.PERSON );
			deck.add(newPerson);
		}
		// loop through all the weapons creating cards of them
		for ( String weapon : weaponList ) {
			Card newWeapon = new Card( weapon, CardType.WEAPON );
			deck.add(newWeapon);
		}
		// loop through all the rooms creating cards of them
		for ( String room : roomList ) {
			Card newRoom = new Card( room, CardType.ROOM );
			deck.add(newRoom);
		}
	
	}
	
	// deal an even amount of cards to everyone with no duplicates
	public void dealCards() {
		correctSolution = new Solution();
		int playerIndex = 0;
		Boolean dealtSolutionPerson = false;
		Boolean dealtSolutionWeapon = false;
		Boolean dealtSolutionRoom = false;
		// loop until every card is dealt
		// the first player, weapon, and room should be added to the solution hand
		for ( Card currentCard : deck ) {
			// if it's the first person/weapon/room, add it to the solution hand and to every player's unseen sets
			if ( !dealtSolutionPerson && currentCard.getType() == CardType.PERSON ) {
				correctSolution.person = currentCard;
				dealtSolutionPerson = true;
				for ( Player player : playerList ) {
					player.addToPeopleNotSeen(currentCard);
				}
			} 
			else if ( !dealtSolutionRoom && currentCard.getType() == CardType.ROOM ) {
				correctSolution.room = currentCard;
				dealtSolutionRoom = true;
				for ( Player player : playerList ) {
					player.addToRoomsNotSeen(currentCard);
				}
			}
			else if ( !dealtSolutionWeapon && currentCard.getType() == CardType.WEAPON ) {
				correctSolution.weapon = currentCard;
				dealtSolutionWeapon = true;
				for ( Player player : playerList ) {
					player.addToWeaponsNotSeen(currentCard);
				}
			}
			// if the card was not put in the solution hand, add it to a player's hand
			else {
				playerList[playerIndex].addNewCardTohand(currentCard);
				// add card to the corresponding NotSeen list for everyone else
				for ( int i = 0; i < MAX_PLAYERS; i++ ) {
					if ( i != playerIndex ) {
						Player player = playerList[i];
						if ( currentCard.getType() == CardType.PERSON ) {
							player.addToPeopleNotSeen(currentCard);
						} 
						else if ( currentCard.getType() == CardType.ROOM ) {
							player.addToRoomsNotSeen(currentCard);
						}
						else if ( currentCard.getType() == CardType.WEAPON ) {
							player.addToWeaponsNotSeen(currentCard);
						}
					}
				}
				playerIndex++;
				if ( playerIndex >= MAX_PLAYERS ) {
					playerIndex = 0;
				}
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
			if ( visited.contains(adjCell) ) {
				continue;
			}
			visited.add(adjCell);
			if ( pathLength == 1 || adjCell.isDoorway() ) {
				targets.add(adjCell);
			}
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
	
	// check the player's accusation against the correct solution set and return true if it's a correct guess and false if not
	public boolean checkAccusation(Solution guess) {
		if ( correctSolution.person == guess.person && correctSolution.weapon == guess.weapon && correctSolution.room == guess.room ) {
			return true;
		}
		return false;
	}
	
	// have the board loop through every player when a suggestion is made
	// once it finds a match, it returns the card to show the player who made the suggestion
	public Card handleSuggestion(Solution suggestion) {
		int playerBeingChecked = currentPlayer + 1;
		Card matchingCard;
		while ( playerBeingChecked != currentPlayer ) {
			matchingCard = playerList[playerBeingChecked].disproveSuggestion(suggestion);
			if ( matchingCard != null ) {
				return matchingCard;
			}
			
			if ( playerBeingChecked == playerList.length - 1 ) {
				playerBeingChecked = 0;
			} 
			else {
				playerBeingChecked++;
			}
		}
		
		return null;
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
	public Player[] getPlayers() {
		return playerList;
	}
	public Set<Card> getDeck() {
		return deck;
	}
	public Solution getSolution() {
		return this.correctSolution;
	}

	//Setter Functions
	public void setConfigFiles(String boardCSV, String legendTXT) {
		boardConfigFile = boardCSV;
		roomConfigFile = legendTXT;
	}
	public void setFourConfigFiles(String boardCSV, String legendTXT, String peopleTXT, String weaponsTXT) {
		setConfigFiles(boardCSV,legendTXT);
		playerConfigFile = peopleTXT;
		weaponConfigFile = weaponsTXT;
	}
	
	
	
	// Functions currently only being using for J-Unit tests
	public void setSolution( Solution newSolution ) {
		this.correctSolution = newSolution;
	}
	public void setPlayers ( Player[] newPlayers ) {
		this.playerList = newPlayers;
	}
	public void setCurrentPlayer(int player) {
		this.currentPlayer = player;
	}
	
	
}