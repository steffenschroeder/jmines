package com.steffenschroeder.jmines.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.steffenschroeder.jmines.GamestateObserver;
import com.steffenschroeder.jmines.MineBoard;
import com.steffenschroeder.jmines.MineBoardBuilder;
import com.steffenschroeder.jmines.MineGame;

public class MindGameTest {

	private MineGame game;
	private GameSpy gameListinerSpy;
	private MineBoard gameBoard;
	
	@Before
	public void setUp() throws Exception {
		MineBoardBuilder builder = new MineBoardBuilder();
		builder.createCustomBoard(4, 4, 0);
		builder.addMine(1, 0);
		builder.addMine(1, 2);
		builder.addMine(2, 1);
		gameBoard = builder.getBoard();

		game = new MineGame(gameBoard);
		gameListinerSpy = new GameSpy();
		game.addGameListiner(gameListinerSpy);
	}
	
	@Test
	 public void testChanged()
	 {
		gameListinerSpy.exptectchanged = 1; 
		game.open(2,0);
		gameListinerSpy.verify();
	 }
	
	@Test
	 public void testChangedOnFlag()
	 {
		gameListinerSpy.exptectchanged = 1; 
		game.toggleFlag(2,0);
		gameListinerSpy.verify();
	 }
	
	
	@Test
	 public void testOpenNotChanged()
	 {

		game.open(2,0);
		gameListinerSpy = new GameSpy();
		game.addGameListiner(gameListinerSpy);
		game.open(2,0);
		// an open field shall not be touched again
		gameListinerSpy.exptectchanged = 0;
		gameListinerSpy.verify();
	 }
	
	@Test
	 public void testLost()
	 {
		gameListinerSpy.exptectchanged = 1;
		gameListinerSpy.exptectlost    = true;
		game.open(1,0);
		gameListinerSpy.verify();
	 }
	
	@Test
	 public void testWon()
	 {
		for (int row = 0; row < gameBoard.getRows(); row++) {
			for (int column = 0; column < gameBoard.getColumns(); column++) {
				if (!gameBoard.getField(row, column).isMine()) {
					game.open(row, column);
				}
			}
			
		}


		gameListinerSpy.exptectchanged = 13;
		gameListinerSpy.exptectwon     = true;
		gameListinerSpy.verify();
	 }
	
	

	

	private class GameSpy implements GamestateObserver
	{
		public boolean exptectwon;
		public boolean exptectlost;
		public int exptectchanged;
		
		public boolean actualwon;
		public boolean actuallost;
		public int actualchanged;

		
		@Override
		public void nofifyBoardChanged() {
			actualchanged++;
		}

		@Override
		public void nofifyGameWon() {
			actualwon = true;
			
		}

		@Override
		public void notifyGameLost() {
			actuallost = true;
		}
		
		public void verify()
		{
			assertEquals(exptectlost, actuallost);
			assertEquals(exptectwon, actualwon);
			assertEquals(exptectchanged, actualchanged);
		}
	}
}


 