package com.steffenschroeder.jmines;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
		board.forEach(Field::open);
	}

	private void notifyBoardChanged() {
		notifyListener(GamestateObserver::nofifyBoardChanged);
	}

	private void nofifyGameLost() {
		notifyListener(GamestateObserver::notifyGameLost);
	}

	private void notifyGameWon() {
		notifyListener(GamestateObserver::nofifyGameWon);
	}
	
	private void notifyListener(Consumer<GamestateObserver> action){
		gameListiner.forEach(action);
	}

	private boolean gameWon() {
			
		long nonMineFieldsOnBoard = board.getRows() * board.getColumns()
				- board.getNumberOfMines();
		return getNumbeOfOpenedNonMineFields() == nonMineFieldsOnBoard;
	}

	private long getNumbeOfOpenedNonMineFields() {
		return board.stream().filter(Field::isOpenNonMineField).count();
	}
}
