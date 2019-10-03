package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {

	public final int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board() {
		
	}
	
	public Board getInstance() {
		return null;
	}
	
	public void initialize() {
		
	}
	
	public void loadRoomConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calctargets(BoardCell cell, int pathLength ) {
		
	}
	
}
