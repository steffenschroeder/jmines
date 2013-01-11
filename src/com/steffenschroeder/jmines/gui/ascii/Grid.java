package com.steffenschroeder.jmines.gui.ascii;
/*
    This file is part of JMines.

    JMines is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JMines.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Random;


/**
 * Minesweeper backend
 * @author Juliana Peña
 *
 */
class Grid implements Consts
{	
	/**
	 * 2D array of Squares to represent grid
	 */
	private Square[][] array;
	
	/**
	 * width of grid
	 */
	private final int width;
	
	/**
	 * height of grid
	 */
	private final int height;
	

	/**
	 * number of mines in grid
	 */
	private final int mines;
	
	/**
	 * Constructor
	 * @param mines in grid
	 * @param width of grid
	 * @param height of grid
	 * @throws UnableToCreateGrid 
	 */
	public Grid(int mines, int width, int height) throws UnableToCreateGrid
	{
		if (width <= 3)
			throw new UnableToCreateGrid("Width must be at least 3.");
		
		if (height <= 3)
			throw new UnableToCreateGrid("Height must be at least 3.");
		
		if (mines > (width * height) / 2)
			throw new UnableToCreateGrid("Too many mines; mines must be at most half the area of the grid.");
		
		if (mines < 3)
			throw new UnableToCreateGrid("Too little mines; there must be at least 3 mines.");
		
		//assigns final values to object
		this.mines  = mines;
		this.width  = width;
		this.height = height;
		
		//create the array that represents the grid
		this.array  = new Square[height][width];
		
		//fill the grid with mines and numbers
		this.fill();
	}
	
	/**
	 * Defualt constructor:
	 * 10 mines
	 * 10 rows
	 * 10 cols
	 * @throws UnableToCreateGrid 
	 */
	public Grid() throws UnableToCreateGrid
	{
		this(10, 10, 10);
	}
	
	/**
	 * Fills grid with mines
	 */
	private void fill()
	{
		/*
		 * 1. Place mines randomly
		 * 2. Fill remaining squares with corresponding numbers
		 */
		Random gen = new Random();
		
		//initialize the whole array
		for (int row = 0; row < getHeight(); row++)
		{
			for (int col = 0; col < getWidth(); col++)
			{
				this.array[row][col] = new Square();
			}
		}
		
		for (int i = 0; i < getMines(); )
		{
			//fill grid with mines
			int row = gen.nextInt(getHeight() - 1);
			int col = gen.nextInt(getWidth() - 1);
			
			if (DEBUG)
				System.out.println("Filling " + row + "," + col);
			
			if (getMark(row, col) != MINE)
			{
				getSqure(row, col).fill(MINE);
				i++;
			}
		}
		
		for (int i = 0; i < getHeight(); i++)
		{
			//fill grid in squares that are not mines
			for (int j = 0; j < getWidth(); j++)
			{
				if (getMark(i, j) != MINE)
				{
					int around;
					try 
					{
						around = this.minesAround(i, j);
					} 
					catch (DirDoesNotExist e) 
					{
						around = 0;
					} 
					catch (SquareDoesNotExist e) 
					{
						around = 0;
					}
					
					switch (around)
					{
					case 1:
						getSqure(i, j).fill(ONE);
						break;
					case 2:
						getSqure(i, j).fill(TWO);
						break;
					case 3:
						getSqure(i, j).fill(THREE);
						break;
					case 4:
						getSqure(i, j).fill(FOUR);
						break;
					case 5:
						getSqure(i, j).fill(FIVE);
						break;
					case 6:
						getSqure(i, j).fill(SIX);
						break;
					case 7:
						getSqure(i, j).fill(SEVEN);
						break;
					case 8:
						getSqure(i, j).fill(EIGHT);
						break;
					default:
						getSqure(i, j).fill(BLANK);
						break;
					}
				}
			}
		}
		
		if (DEBUG)
		{
			System.out.println(this.solutionToString());
			System.out.println(this);
		}
	}

	private int getHeight() {
		return this.height;
	}

	private int getMines() {
		return this.mines;
	}

	private int getWidth() {
		return this.width;
	}

	private Square getSqure(int i, int j) {
		return this.array[i][j];
	}
	
	/**
	 * Checks that row and col are within the valid range of this.array[row][col]
	 * @param row
	 * @param col
	 * @throws SquareDoesNotExist if row or col are outside the range
	 */
	private void checkRange(int row, int col) throws SquareDoesNotExist
	{
		if (row >= getHeight() || row < 0)
			throw new SquareDoesNotExist("Row out of range");
		
		if (col >= getWidth() || col < 0)
			throw new SquareDoesNotExist("Column out of range");
	}
	
	
	/**
	 * Detects how many mines are around the square in array[row][col]
	 * 
	 * @param row the square is in
	 * @param col the square is in
	 * @return number of mines around array[row][col]
	 * @throws DirDoesNotExist 
	 * @throws SquareDoesNotExist if row or col are invalid
	 */
	public int minesAround(int row, int col) throws DirDoesNotExist, SquareDoesNotExist
	{
		//asserts that row and col are valid
		this.checkRange(row, col);
		
		
		int around = 0;
		
		for (int dir = 0; dir <= DIRMAX; dir++)
		{
			if (this.getAdjacentMark(row, col, dir) == MINE)
				around++;
		}
		
		//return board.getField(row, col).getNumerOfMinesAround();
		return around;
	
	}
	
	/**
	 * Returns the mark adjacent to this.array[row][col] in direction dir
	 * @param row
	 * @param col
	 * @param dir
	 * @return the mark adjacent to this.array[row][col], NONE ('\0') if a wall or corner
	 * @throws DirDoesNotExist
	 * @throws SquareDoesNotExist
	 */
	private char getAdjacentMark(int row, int col, int dir) throws DirDoesNotExist, SquareDoesNotExist
	{
		return this.getAdjacentSquare(row, col, dir).getMark();
	
	}

	/**
	 * Returns the square next to this.array[row][col] in direction dir
	 * If [row][col] references a wall or a corner, return a new blank square
	 * 
	 * @param row of origin square
	 * @param col of origin square
	 * @param dir of adjacent square relative to origin square
	 * @return Square adjacent to origin square in direction dir
	 * @throws SquareDoesNotExist when row,col refers to coords outside the Grid
	 * @throws DirDoesNotExist when parameter dir is not a direction in Consts (ie, not  0 <= dir <= 7)
	 */
	private Square getAdjacentSquare(int row, int col, int dir) throws SquareDoesNotExist, DirDoesNotExist
	{
		this.checkRange(row, col);
		
		int offsetlr = getLeftTofset(dir);
		int offsettb = getTopOffset(dir);

			
		this.checkRange(row + offsettb, col + offsetlr);
		return this.getSqure(row + offsettb,col + offsetlr);

	}

	private int getTopOffset(int dir) {
		int offsettb = 0;
		switch (dir) {
			case TOPLEFT:
			case TOP:
			case TOPRIGHT: offsettb = -1;	
				break;
			case BOTLEFT:
			case BOT:
			case BOTRIGHT: offsettb = +1;	
				break;
			default:
				break;
			}
		return offsettb;
	}

	private int getLeftTofset(int dir) {
		int offsetlr = 0;
		switch (dir) {
			case TOPLEFT:
			case LEFT:
			case BOTLEFT: offsetlr = -1;	
				break;
			case TOPRIGHT:
			case RIGHT:
			case BOTRIGHT: offsetlr = +1;	
				break;
			default:
				break;
			}
		return offsetlr;
	}
	

	
	/**
	 * Reveales the Square at coords row,col 
	 * If revealed square is blank, reveals adjacent squares
	 * 
	 * @param row
	 * @param col
	 * @throws SquareDoesNotExist when row,col is invalid
	 * @throws SquareHasFlag if row,col has a flag and cannot be revealed
	 * @throws SquareAlreadyRevealed 
	 */
	public void reveal(int row, int col) throws SquareDoesNotExist, SquareHasFlag, SquareAlreadyRevealed
	{
		/*
		 * Checks if row,col is valid; else, throws SquareDoesNotExist
		 */
		this.checkRange(row, col);
		
		if (DEBUG)
			System.out.println("revealing " + row + "," + col);
		
		
		if (this.hasFlag(row, col))
		{
			throw new SquareHasFlag("Square has a flag, cannot reveal");
		}
		
		if (this.isRevealed(row, col))
		{
			throw new SquareAlreadyRevealed("Square is already revealed");
		}
		
		
		
		reveal2(row, col);
		
		
		if (getMark(row, col) == BLANK)
		{
			
			for(int direction = TOPLEFT;direction<=LEFT;direction++){
				try //top
				{
					this.reveal(row+getTopOffset(direction), col+getLeftTofset(direction));
				} catch (SquareDoesNotExist e) {}
				  catch (SquareAlreadyRevealed e) {}
				
			}
		}
		
		
		if (DEBUG)
		{
			System.out.println(this);
		}
		
	}

	private char reveal2(int row, int col) {
		return getSqure(row, col).reveal();
	}
	
	/**
	 * Returns if the square in coords row, col is hidden or not
	 * 
	 * @param row
	 * @param col
	 * @return true if it is hidden, false otherwise
	 * @throws SquareDoesNotExist
	 * 
	 * @see {@link #isRevealed(int, int)}
	 * @see Square#isHidden()
	 */
	public boolean isHidden(int row, int col) throws SquareDoesNotExist
	{
		this.checkRange(row, col);
		return getSqure(row, col).isHidden();
	}
	
	/**
	 * Returns if the square in coords row, col is revealed or not
	 * 
	 * @param row
	 * @param col
	 * @return true if it is revealed, false otherwise
	 * @throws SquareDoesNotExist
	 * 
	 * @see {@link #isHidden(int, int)}
	 * @see Square#isRevealed()
	 */
	public boolean isRevealed(int row, int col) throws SquareDoesNotExist
	{
		this.checkRange(row, col);
		return !getSqure(row, col).isHidden();
	}
	
	/**
	 * Adds a flag in row,col
	 * 
	 * If row,col does not exist, doens't do anthing.
	 * 
	 * @param row
	 * @param col
	 * @throws SquareDoesNotExist if row,col are incorrect
	 * @throws SquareAlreadyRevealed 
	 */
	public void toggleFlag(int row, int col) throws SquareDoesNotExist, SquareAlreadyRevealed
	{
		this.checkRange(row, col);
		
		if (this.isRevealed(row, col))
		{
			//cannot flag a revealed square
			throw new SquareAlreadyRevealed("Cannot be flagged, square is revealed");
		}
		
		getSqure(row, col).toggleFlag();
	}
	
	public boolean hasFlag(int row, int col)
	{
		try
		{
			this.checkRange(row, col);
		}
		catch (SquareDoesNotExist e)
		{
			return false;
		}
		
		return getSqure(row, col).hasFlag();
	}
	
	
	
	
	/**
	 * Returns a string representation of the grid, with hidden squares in black
	 * 
	 * Example:
	 * 
		A B C D E F
	  + - - - - - - +
	1 | █ █ █ █ █ █ | 1
	2 | █ █ █ █ █ █ | 2
	3 | █ █ █ █ █ █ | 3
	4 | █ █ █ █ █ █ | 4
	5 | █ █ █ █ █ █ | 5
	  + - - - - - - +
		A B C D E F
	
		A B C D E F
	  + - - - - - - +
	1 | █ 1 █ █ █ █ | 1
	2 | █ █ █ █ █ █ | 2
	3 | █ █ █ █ █ █ | 3
	4 | █ █ █ █ █ █ | 4
	5 | █ █ █ █ █ █ | 5
	  + - - - - - - +
		A B C D E F
	
	    A B C D E F
	  + - - - - - - +
	1 | █ 1 █ █ 1   | 1
	2 | █ █ █ 2     | 2
	3 | █ █ █ █ 1   | 3
	4 | █ █ █ █ █ 2 | 4
	5 | █ █ █ █ █ █ | 5
	  + - - - - - - +
		A B C D E F
	* 
	* etc....
	* 
	* @return Grid as a string, with hidden Squares in black
	* 
	* @see #solutionToString()
	*/	
	@Override
	public String toString()
	{
		return printGrid(false);
	}
	
	/**
	 * Returns a string representing the grid with all squares revealed
	 * 
	 * Example:
	 * 
	        A B C D E F
	      + - - - - - - +
	  	1 | * 1 2 * 1   | 1
		2 | 1 2 * 2     | 2
		3 | 1 * 3   1	| 3
		4 | 2 3 * 2 * 2 | 4
		5 | * * 2   2 *	| 5
		  + - - - - - - +
			A B C D E F
			
	 * 
	 * @return string of revealed grid
	 * 
	 * @see #toString()
	 */
	public String solutionToString()
	{
		return printGrid(true);
	}

	private String printGrid(boolean solution){
		String s = new String();
		
		s = topLetters(s);
		s = topborder(s);
		
		try {
			s = printInnerField(s, solution);
		} catch (SquareDoesNotExist e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s = topborder(s);
		s = topLetters(s);
		
		return s;
	}
	
	

	private String topLetters(String s) {
		//add letters on top
		s += "     "; //5 spaces for padding
		char letter = 'A';
		for (int i = 0; i < getWidth(); i++)
		{
			s += letter;
			s += ' '; //space for padding
			letter++;
		}
		s += '\n';
		return s;
	}

	private String topborder(String s) {
		//add top border
		s += "   + ";
		for (int i = 0; i < getWidth(); i++)
		{
			s += "- ";
		}
		s += '+';
		s += '\n';
		return s;
	}

	private String printInnerField(String s, boolean printSolution) throws SquareDoesNotExist {
		for (int i = 0; i < getHeight(); i++)
		{
			s = left(s, i);
			
			for (int j = 0; j < getWidth(); j++)
			{
				
				if (isRevealed(i, j) || printSolution)
				{
					s += getMark(i, j);
				}
				else if (hasFlag(i, j))
				{
					s += FLAG;
				}
				else
				{
					s += HIDDEN;
				}
				s += ' ';
			}
			s = right(s, i);
		}
		return s;
	}

	private String left(String s, int i) {
		if ((i + 1) / 10  == 0) //if only one digit, pad with one space
		{
			s += ' ';
			s += (i + 1);
		}
		else
		{
			s += (i + 1);
		}
		s += " | ";
		return s;
	}

	private String right(String s, int i) {
		s += "| ";
		
		//add number at end of line
		s += (i + 1);
		
		s += '\n';
		return s;
	}

	public char getMark(int i, int j) {
		return getSqure(i, j).getMark();
	}
	
}
