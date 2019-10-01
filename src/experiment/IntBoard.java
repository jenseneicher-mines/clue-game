package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	private Set<BoardCell> visited;
	
	public IntBoard() {
		calcAdjacencies();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
	}
	
	// function to calculate the adjacency list for each grid cell and stores the result in adjMtx
	public void calcAdjacencies() { 
		
	}
	
	//return a board cell from adjMtx
	public Set<BoardCell> getAdjList(BoardCell boardCell) {
		
		return null;
	}
	
	//calculates targets that are pathLength distance from the startCell
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int x, int y) {
		BoardCell newCell = new BoardCell(x,y);
		return newCell;
	}
}
