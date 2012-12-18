package com.steffenschroeder.jmines;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MineBoard implements Iterable<Field>{
    protected Field[][] mines;

    private MineBoard() {

    }

    public MineBoard(int rows, int cols) {
        new MineBoard();
        mines = new Field[rows][cols];
    }

    public int getColumns() {
        return mines[0].length;
    }

    public Field getField(int row, int column) {
        return mines[row][column];
    }

    public int getNumberOfMines() {
        int numberOfMines = 0;
        for (int row = 0; row < getRows(); row++) {

            for (int col = 0; col < getColumns(); col++) {
                numberOfMines += getField(row, col).isMine() ? 1 : 0;
            }
        }
        return numberOfMines;
    }

    public int getRows() {
        return mines.length;
    }

    public void setField(Field field, int row, int column) {
        mines[row][column] = field;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int row = 0; row < getRows(); row++) {

            for (int col = 0; col < getColumns(); col++) {
                char c = getField(row, col).isMine() ? 'M' : '_';
                buf.append(c);
            }
            buf.append('\n');
        }
        return buf.toString();
    }

    public void updateAllNeighbors() {
        for (int currentRow = 0; currentRow < getRows(); currentRow++) {
            for (int currentColumn = 0; currentColumn < getColumns(); currentColumn++) {
                updateNeigbors(currentRow, currentColumn);
            }
        }
    }

    private void updateNeigbors(final int row, final int column) {
        List<Field> fieldsAround = new LinkedList<Field>();
        for (int currentRow = row - 1; //row above current 
        	     currentRow <= row + 1; //row below current
        	     currentRow++) {
            if (currentRow < 0 || currentRow >= getRows()) {
                continue;
            }
            for (int currentCol = column - 1;  // column left to current 
                     currentCol <= column + 1; // column right to current
                     currentCol++) {
                if (currentCol < 0 || currentCol >= getColumns() || (currentCol == column && currentRow == row)) {
                    continue;
                }
                fieldsAround.add(mines[currentRow][currentCol]);
            }
        }
        mines[row][column].setNeighborhood(fieldsAround);
    }

	@Override
	public Iterator<Field> iterator() {
		return new TwoDimensionArrayIterator<Field>(mines);
	}
	
}
