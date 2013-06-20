package com.steffenschroeder.jmines.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.steffenschroeder.jmines.Field;
import com.steffenschroeder.jmines.MineBoard;
import com.steffenschroeder.jmines.MineBoardBuilder;
import com.steffenschroeder.jmines.MineField;
import com.steffenschroeder.jmines.NoMineField;

public class MineBoardTest {

	private MineBoard board;

	private void allequals(final boolean[][] expected) {

		for (int ii = 0; ii < expected.length; ii++) {
			for (int jj = 0; jj < expected[0].length; jj++) {
				assertEquals(expected[ii][jj], board.getField(ii, jj)
						.isOpen());
			}
}
	}

	@Before
	public void setUp() throws Exception {
		board = new MineBoard(3, 4);
		board.setField(new NoMineField(), 0, 0);
		board.setField(new NoMineField(), 0, 1);
		board.setField(new NoMineField(), 0, 2);
		board.setField(new MineField(), 0, 3);

		board.setField(new NoMineField(), 1, 0);
		board.setField(new NoMineField(), 1, 1);
		board.setField(new MineField(), 1, 2);
		board.setField(new NoMineField(), 1, 3);

		board.setField(new NoMineField(), 2, 0);
		board.setField(new NoMineField(), 2, 1);
		board.setField(new MineField(), 2, 2);
		board.setField(new NoMineField(), 2, 3);
	}

	@Test
	public void testDimensions() {
		assertEquals(3, board.getRows());
		assertEquals(4, board.getColumns());
	}

	@Test
	public void testGetNumerOfMines() {
		assertEquals(3, board.getNumberOfMines());
	}

	@Test
	public void testToString() {
		String s = board.toString();
		assertEquals("___M\n__M_\n__M_\n", s);
	}

	@Test
	public void testTouchField() {
		board.updateAllNeighbors();
		// all has to be closed
		boolean[][] expected = { { false, false, false, false },
				{ false, false, false, false }, { false, false, false, false } };
		allequals(expected);
	}

	@Test
	public void testTouchFieldRecurse() {
		board.updateAllNeighbors();

		board.getField(0, 0).open();
		boolean[][] expected3 = { { true, true, false, false },
				{ true, true, false, false }, { true, true, false, false } };
		allequals(expected3);
	}

	@Test
	public void testTouchFieldSingle() {
		board.updateAllNeighbors();
		// open some field
		board.getField(0, 2).open();
		boolean[][] expected2 = { { false, false, true, false },
				{ false, false, false, false }, { false, false, false, false } };
		allequals(expected2);
	}

	@Test
	public void testUpdateNeigbors() {
		board.updateAllNeighbors();
		int[][] minesToTest = { { 0, 1, 2, 1 }, { 0, 2, 2, 3 }, { 0, 2, 1, 2 } };
		for (int row = 0; row < board.getRows(); row++) {
			for (int col = 0; col < board.getColumns(); col++) {
				 assertEquals("Row: " + row + " col: " + col,
						minesToTest[row][col], board.getField(row, col)
								.getNumerOfMinesAround());
			}
		}
	}

	@Test
	public void testIteratorNumber() {
		int count = 0;
		for (@SuppressWarnings("unused") Field field : board) {
			count++;
		}
		assertEquals(12, count);

	}
	
	@Test
	public void testIteratorContent() {
		int count = 0;
		for (Field field : board) {
			if(field.isMine())
			{
				count++;
			}
			
		}
		assertEquals(board.getNumberOfMines(), count);
	}
	
	@Test
	public void StackOverFlow(){
		MineBoard board = (new MineBoardBuilder()).createCustomBoard(100, 100, 10).getBoard();
		board.getField(0, 0).open();
	}

	
	
}
