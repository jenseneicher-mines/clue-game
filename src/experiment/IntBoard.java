/* Software Engineering: Clue Game
 * Code by: Kai Mizuno and Jensen Eicher
 */

package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {

	private int totalRows;
	private int totalCols;
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	public IntBoard() {
		totalRows = 4;
		totalCols = 4;
		grid = new BoardCell[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++) {
			for(int col = 0; col < totalCols; col++) {
				grid[row][col] = new BoardCell(row,col);
			}
		}
		
		calcAdjacencies();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
	}
	
	// function to calculate the adjacency list for each grid cell and stores the result in adjMtx
	public void calcAdjacencies() { 
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		
		//CURRENTLY BEING SET UP AS A 4x4 GRID
		
		for(int row = 0; row < totalRows; row++) {
			for(int col = 0; col < totalCols; col++) {
				
				Set<BoardCell> currentAdjSet = new HashSet<BoardCell>();
				
				if ( col > 0 ) {
					currentAdjSet.add( grid[row][col-1] );
				}
				if ( col < totalCols - 1 ) {
					currentAdjSet.add( grid[row][col+1] );
				}
				if ( row > 0 ) {
					currentAdjSet.add( grid[row-1][col] );
				}
				if ( row < totalRows - 1 ) {
					currentAdjSet.add( grid[row+1][col] );
				}
				
				adjMtx.put(grid[row][col], currentAdjSet);
				
			}
		}
		
	}
	
	//return a board cell from adjMtx
	public Set<BoardCell> getAdjList(BoardCell boardCell) {
		Set<BoardCell> currentAdjSet = new HashSet<BoardCell>();
		currentAdjSet = adjMtx.get(boardCell);
		return currentAdjSet;
	}
	
	//calculates targets that are pathLength distance from the startCell
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		for ( BoardCell adjCell : adjMtx.get(startCell) ) {
			if ( visited.contains(adjCell) )
				continue;
			
			visited.add(adjCell);
			if ( pathLength == 1 )
				targets.add(adjCell);
			else
				calcTargets(adjCell, pathLength-1);
			visited.remove(adjCell);
		}
		visited.remove(startCell);
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int x, int y) {
		return grid[x][y];
	}
}
