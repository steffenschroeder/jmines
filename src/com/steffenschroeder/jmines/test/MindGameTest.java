package com.steffenschroeder.jmines.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.steffenschroeder.jmines.GamestateObserver;
import com.steffenschroeder.jmines.MineBoard;
import com.steffenschroeder.jmines.MineBoardBuilder;
import com.steffenschroeder.jmines.MineGame;

@RunWith(MockitoJUnitRunner.class)
public class MindGameTest {

	private MineGame game;
	
	private MineBoard gameBoard;
	
	@Mock
	private GamestateObserver gameStateSpy;
	
	@Before
	public void setUp() throws Exception {
		MineBoardBuilder builder = new MineBoardBuilder();
		builder.createCustomBoard(4, 4, 0);
		builder.addMine(1, 0);
		builder.addMine(1, 2);
		builder.addMine(2, 1);
		gameBoard = builder.getBoard();

		game = new MineGame(gameBoard);

		gameStateSpy = mock(GamestateObserver.class);
		game.addGameListiner(gameStateSpy);
	}
	
	@Test
	 public void testChanged()
	 {
		game.open(2,0);
		verify(gameStateSpy).nofifyBoardChanged();
	 }
	
	@Test
	 public void testChangedOnFlag()
	 {
		game.toggleFlag(2,0);
		verify(gameStateSpy).nofifyBoardChanged();
	 }
	
	
	@Test
	 public void testOpenNotChanged()
	 {

		game.open(2,0);
		reset(gameStateSpy); //forget the call on notifyChanged()
		game.open(2,0);
		// an open field shall not be touched again
		verify(gameStateSpy,never()).nofifyBoardChanged();
	 }
	
	@Test
	 public void testLost()
	 {
		game.open(1,0);

		verify(gameStateSpy).nofifyBoardChanged();
		verify(gameStateSpy).notifyGameLost();
		verify(gameStateSpy,never()).nofifyGameWon();
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

		verify(gameStateSpy, times(13)).nofifyBoardChanged();
		verify(gameStateSpy).nofifyGameWon();
	 }
}


 