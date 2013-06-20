package com.steffenschroeder.jmines.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.steffenschroeder.jmines.MineBoard;
import com.steffenschroeder.jmines.MineBoardBuilder;

public class MineBoardBuilderTest {

    private MineBoardBuilder builder;
    
    @Before
    public void setUp() throws Exception {
        builder = new MineBoardBuilder();
    }

    @Test(expected=IllegalArgumentException.class)
    public void tooManyMines() {

            builder.createCustomBoard(9, 9, 81);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void rowsNegative()
    {
    	builder.createCustomBoard(-1, 9, 10);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void columnsNegative()
    {
    	builder.createCustomBoard(9, -1, 10);
    }


    @Test
    public void testCreation() {
        testCreationByNumbers(9,8,10);
    }

	private void testCreationByNumbers(int rows, int cols, int mines) {
		builder.createCustomBoard(rows, cols, mines);
        MineBoard board = builder.getBoard();
        assertEquals(rows,  board.getRows());
        assertEquals(cols,  board.getColumns());
        assertEquals(mines, board.getNumberOfMines());
	}

    @Test
    public void testMuchMines() {
        testCreationByNumbers(9, 9, 80);
    }
    
    @Test
    public void addMinesNumberOfMines() {
        MineBoard board = build3x3boardWithOneMineInMiddle();
        assertEquals(1, board.getNumberOfMines());
    }
    
	private MineBoard build3x3boardWithOneMineInMiddle() {
		builder.createCustomBoard(3, 3, 0);
        builder.addMine(1,1);
		return builder.getBoard();
	}

    
    @Test
    public void addMinesPostionOfMine() {
        MineBoard board = build3x3boardWithOneMineInMiddle();
        
        // check if number of mines was updated
        assertEquals(1, board.getField(0, 0).getNumerOfMinesAround());
        assertEquals(1, board.getField(0, 1).getNumerOfMinesAround());
        assertEquals(1, board.getField(0, 2).getNumerOfMinesAround());

        assertEquals(1, board.getField(1, 0).getNumerOfMinesAround());
        assertEquals(0, board.getField(1, 1).getNumerOfMinesAround()); // here's the mine
        assertEquals(1, board.getField(1, 2).getNumerOfMinesAround());
        
        assertEquals(1, board.getField(2, 0).getNumerOfMinesAround());
        assertEquals(1, board.getField(2, 1).getNumerOfMinesAround());
        assertEquals(1, board.getField(2, 2).getNumerOfMinesAround());
    }



    
    
}
