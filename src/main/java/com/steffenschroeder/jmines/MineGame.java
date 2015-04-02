package com.steffenschroeder.jmines;

import java.util.ArrayList;
import java.util.List;

public class MineGame {

	private final MineBoard board;
	private final List<GamestateObserver> gameListiner = new ArrayList<GamestateObserver>();

	public MineGame(MineBoard gameBoard) {
		board = gameBoard;
	}

	public void addGameListiner(GamestateObserver observer) {
		gameListiner.add(observer);
	}

	public void toggleFlag(int row, int column) {
		Field fieldToFlag = board.getField(row, column);
		fieldToFlag.toggleFlag();
		notifyBoardChanged();
	}

	public void open(int row, int column) {
		Field fieldToOpen = board.getField(row, column);
		if (!fieldToOpen.isOpen()) {
			fieldToOpen.open();
			if (fieldToOpen.isMine()) {
				openAllFields();
				nofifyGameLost();

			} else if (gameWon()) {
				notifyGameWon();
			}
			notifyBoardChanged();
		}
	}

	private void openAllFields() {
		for (Field field : board) {
			field.open();
		}

	}

	private void notifyBoardChanged() {
		for (GamestateObserver listener : gameListiner) {
			listener.nofifyBoardChanged();
		}
	}

	private void nofifyGameLost() {
		for (GamestateObserver listener : gameListiner) {
			listener.notifyGameLost();
		}
	}

	private void notifyGameWon() {
		for (GamestateObserver listener : gameListiner) {
			listener.nofifyGameWon();
		}
	}

	private boolean gameWon() {
			
		int nonMineFieldsOnBoard = board.getRows() * board.getColumns()
				- board.getNumberOfMines();
		return getNumbeOfOpenedNonMineFields() == nonMineFieldsOnBoard;
	}

	private int getNumbeOfOpenedNonMineFields() {
		int openendNoneMineFields = 0;
		for (Field field : board) {
			if (isOpenNonMineField(field)) {
				openendNoneMineFields++;
			}
		}
		return openendNoneMineFields;
	}

	private boolean isOpenNonMineField(Field currentField) {
		return !currentField.isMine() && currentField.isOpen();
	}
}
