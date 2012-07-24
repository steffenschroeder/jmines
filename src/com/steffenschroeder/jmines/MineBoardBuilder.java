package com.steffenschroeder.jmines;

import java.util.Random;

public class MineBoardBuilder {

	private MineBoard boardToCreate;

	public MineBoardBuilder addMine(int row, int column) {
		boardToCreate.setField(new MineField(), row, column);
		boardToCreate.updateAllNeighbors();
		return this;
	}

	public MineBoard getBoard() {
		return boardToCreate;
	}

	public MineBoardBuilder createCustomBoard(int rows, int columns, int minesToAdd) {

		validateInput(rows, columns, minesToAdd);
		boardToCreate = new MineBoard(rows, columns);
		initilizeBoard();
		addRandomNumberOfMines(minesToAdd);
		return this;
	}

	private void validateInput(int rows, int columns, int minesToAdd) {
		if (rows < 0) {
			throw new IllegalArgumentException("Row must be >= 0");
		}
		if (columns <= 1) {
			throw new IllegalArgumentException("Columns must be > 0");
		}
		if (minesToAdd >= rows * columns) {
			throw new IllegalArgumentException("Too Many mines (" + minesToAdd
					+ "). Must be less than" + rows * columns);
		}
	}

	/**
	 * Fills all positions of the board with NoMineFields
	 */
	private void initilizeBoard() {
		for (int row = 0; row < boardToCreate.getRows(); row++) {
			for (int column = 0; column < boardToCreate.getColumns(); column++) {
				boardToCreate.setField(new NoMineField(), row, column);
			}
		}
	
	}

	private void addRandomNumberOfMines(int minesToAdd) {
		int seed = boardToCreate.getColumns() * boardToCreate.getRows();
		while (minesToAdd > 0) {
			Random rand = new Random();
			int pos = rand.nextInt(seed);
			int row = pos / boardToCreate.getColumns();
			int col = pos - (row * boardToCreate.getColumns());
			if (boardToCreate.getField(row, col).isMine()) {
				continue;
			} else {
				addMine(row, col);
				minesToAdd--;
			}
		}
	}
}
