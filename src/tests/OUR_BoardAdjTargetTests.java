/* Clue Board Part III
 * Testing adjacencies and targets
 * Kai Mizuno
 * Jensen Eicher
 */

package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class OUR_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("OurInputFiles/GameBoardFinal.csv", "OurInputFiles/Rooms.txt", "OurInputFiles/PlayerConfig.txt", "OurInputFiles/WeaponConfig.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(5, 13);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(6, 7);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(17, 21);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(21, 12);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(19, 6);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(24, 21);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(6, 10);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 11)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(19, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 10)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(14, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 2)));
		//TEST DOORWAY UP
		testList = board.getAdjList(19, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 2)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(6, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 4)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(6, 4);
		assertTrue(testList.contains(board.getCellAt(6,5)));
		assertTrue(testList.contains(board.getCellAt(5,4)));
		assertTrue(testList.contains(board.getCellAt(7,4)));
		assertTrue(testList.contains(board.getCellAt(6,3)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(6, 16);
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertTrue(testList.contains(board.getCellAt(7, 16)));
		assertTrue(testList.contains(board.getCellAt(6, 17)));
		assertTrue(testList.contains(board.getCellAt(5,16)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(3, 5);
		assertTrue(testList.contains(board.getCellAt(3, 6)));
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 5)));
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(18, 7);
		assertTrue(testList.contains(board.getCellAt(19, 7)));
		assertTrue(testList.contains(board.getCellAt(18, 6)));
		assertTrue(testList.contains(board.getCellAt(18, 8)));
		assertTrue(testList.contains(board.getCellAt(17, 7)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(24, 4);
		assertTrue(testList.contains(board.getCellAt(23, 4)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(16, 0);
		assertTrue(testList.contains(board.getCellAt(15, 0)));
		assertTrue(testList.contains(board.getCellAt(16, 1)));
		assertTrue(testList.contains(board.getCellAt(17, 0)));
		assertEquals(3, testList.size());
		
		// Test between two rooms, walkways right and left
		testList = board.getAdjList(23, 4);
		assertTrue(testList.contains(board.getCellAt(24, 4)));
		assertTrue(testList.contains(board.getCellAt(22, 4)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(11,5);
		assertTrue(testList.contains(board.getCellAt(12, 5)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(24, 15);
		assertTrue(testList.contains(board.getCellAt(23, 15)));
		assertTrue(testList.contains(board.getCellAt(24, 16)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(16, 21);
		assertTrue(testList.contains(board.getCellAt(15, 21)));
		assertTrue(testList.contains(board.getCellAt(16, 20)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(7, 3);
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		assertTrue(testList.contains(board.getCellAt(7, 4)));
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(24, 10, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 10)));
		assertTrue(targets.contains(board.getCellAt(24, 9)));	
		
		board.calcTargets(17, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 0)));
		assertTrue(targets.contains(board.getCellAt(18, 0)));	
		assertTrue(targets.contains(board.getCellAt(17, 1)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(24, 10, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 9)));
		assertTrue(targets.contains(board.getCellAt(22, 10)));
		
		board.calcTargets(17, 0, 2);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 0)));
		assertTrue(targets.contains(board.getCellAt(16, 1)));	
		assertTrue(targets.contains(board.getCellAt(18, 1)));	
		assertTrue(targets.contains(board.getCellAt(17, 2)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(24, 10, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 10)));
		assertTrue(targets.contains(board.getCellAt(21, 9)));
		assertTrue(targets.contains(board.getCellAt(22, 10)));
		assertTrue(targets.contains(board.getCellAt(23, 9)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(21, 4, 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 4)));
		assertTrue(targets.contains(board.getCellAt(18, 3)));	
		assertTrue(targets.contains(board.getCellAt(18, 5)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(22, 4, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(18, 2)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(21, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// directly right
		assertTrue(targets.contains(board.getCellAt(21, 18)));
		// directly left
		assertTrue(targets.contains(board.getCellAt(21, 14)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		assertTrue(targets.contains(board.getCellAt(23, 16)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(20, 15)));
		assertTrue(targets.contains(board.getCellAt(20, 17)));
		assertTrue(targets.contains(board.getCellAt(22, 15)));
		assertTrue(targets.contains(board.getCellAt(22, 17)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(10, 19, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(7, 19)));
		assertTrue(targets.contains(board.getCellAt(13, 19)));
		// directly left (can't go right)
		assertTrue(targets.contains(board.getCellAt(10, 16)));
		// left then down
		assertTrue(targets.contains(board.getCellAt(12, 18)));
		assertTrue(targets.contains(board.getCellAt(11, 17)));
		// left then up
		assertTrue(targets.contains(board.getCellAt(8, 18)));
		assertTrue(targets.contains(board.getCellAt(9, 17)));

		// into the rooms
		assertTrue(targets.contains(board.getCellAt(9, 20)));
		assertTrue(targets.contains(board.getCellAt(8, 20)));
		// 
		assertTrue(targets.contains(board.getCellAt(9, 19)));
		assertTrue(targets.contains(board.getCellAt(11, 19)));
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(12, 21, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 21)));
		// Take two steps
		board.calcTargets(12, 21, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 21)));
		assertTrue(targets.contains(board.getCellAt(13, 20)));
	}

}
