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
		for (int row = 0; row < this.height; row++)
		{
			for (int col = 0; col < this.width; col++)
			{
				this.array[row][col] = new Square();
			}
		}
		
		for (int i = 0; i < this.mines; )
		{
			//fill grid with mines
			int row = gen.nextInt(this.height - 1);
			int col = gen.nextInt(this.width - 1);
			
			if (DEBUG)
				System.out.println("Filling " + row + "," + col);
			
			if (this.array[row][col].getMark() != MINE)
			{
				this.array[row][col].fill(MINE);
				i++;
			}
		}
		
		for (int i = 0; i < this.height; i++)
		{
			//fill grid in squares that are not mines
			for (int j = 0; j < this.width; j++)
			{
				if (this.array[i][j].getMark() != MINE)
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
						this.array[i][j].fill(ONE);
						break;
					case 2:
						this.array[i][j].fill(TWO);
						break;
					case 3:
						this.array[i][j].fill(THREE);
						break;
					case 4:
						this.array[i][j].fill(FOUR);
						break;
					case 5:
						this.array[i][j].fill(FIVE);
						break;
					case 6:
						this.array[i][j].fill(SIX);
						break;
					case 7:
						this.array[i][j].fill(SEVEN);
						break;
					case 8:
						this.array[i][j].fill(EIGHT);
						break;
					default:
						this.array[i][j].fill(BLANK);
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
	
	/**
	 * Checks that row and col are within the valid range of this.array[row][col]
	 * @param row
	 * @param col
	 * @throws SquareDoesNotExist if row or col are outside the range
	 */
	private void checkRange(int row, int col) throws SquareDoesNotExist
	{
		if (row >= this.height || row < 0)
			throw new SquareDoesNotExist("Row out of range");
		
		if (col >= this.width || col < 0)
			throw new SquareDoesNotExist("Column out of range");
	}
	
	/**
	 * Returns the size of the grid
	 * To use: getSize()[0] is width and getSize()[1] is height
	 * 
	 * @return {width, height}
	 */
	public int[] getSize()
	{
		return new int[] {this.width, this.height};
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
		
		return around;
		
		
		//below is the old version of the method, before the return NONE was implemented in getAdjacentMark()
		//I keep this for referencing purpose
		
		/*
		//if on a wall
		if (row == 0) //left wall
		{
			//if on corner
			if (col == 0) //top-left corner
			{
				//test BOT, BOTRIGHT, RIGHT
			}
			else if (col == this.width - 1) //bottom-left corner
			{
				//test TOP, TOPRIGHT, RIGHT
			}
			else //on wall w/o corner
			{
				//test TOP, TOPRIGHT, RIGHT, BOTRIGHT, BOT
			}
		}
		else if (row == this.height - 1) //right wall
		{
			//if on corner
			if (col == 0) //top-right corner
			{
				//test BOT, BOTLEFT, LEFT
				if (this.getAdjacentMark(row, col, BOT) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, BOTLEFT) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, LEFT) == MINE)
					around++;
			}
			else if (col == this.width - 1) //bottom-right corner
			{
				//test TOP, TOPLEFT, LEFT
				if (this.getAdjacentMark(row, col, TOPLEFT) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, TOP) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, LEFT) == MINE)
					around++;
			}
			else //on wall w/o corner
			{
				//test TOP, TOPLEFT, LEFT, BOTLEFT, BOT
				if (this.getAdjacentMark(row, col, TOPLEFT) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, TOP) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, LEFT) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, BOT) == MINE)
					around++;
				if (this.getAdjacentMark(row, col, BOTLEFT) == MINE)
					around++;
			}
		}
		else if (col == 0) //top wall
		{
			//test RIGHT, BOTRIGHT, BOT, BOTLEFT, LEFT
			if (this.getAdjacentMark(row, col, RIGHT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, BOTRIGHT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, BOT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, BOTLEFT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, LEFT) == MINE)
				around++;
		}
		else if (col == this.height - 1) //bottom wall
		{
			//test RIGHT, TOPRIGHT, TOP, TOPLEFT, LEFT
			if (this.getAdjacentMark(row, col, TOPLEFT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, TOP) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, TOPRIGHT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, RIGHT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, LEFT) == MINE)
				around++;
		}
		else //inside grid
		{
			//test TOP, TOPRIGHT, RIGHT, BOTRIGHT, BOT, BOTLEFT, LEFT, TOPLEFT
			if (this.getAdjacentMark(row, col, TOPLEFT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, TOP) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, TOPRIGHT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, RIGHT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, BOTRIGHT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, BOT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, BOTLEFT) == MINE)
				around++;
			if (this.getAdjacentMark(row, col, LEFT) == MINE)
				around++;
		}
		
		
		return around;
		*/
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
		
		
		//old version, replaced when getAdjacentSquare was added
		/*this.checkRange(row, col);
		
		switch (dir)
		{
			case TOPLEFT:
				try
				{
					this.checkRange(row-1, col-1);
					return this.array[row-1][col-1].getMark();
				}
				catch (SquareDoesNotExist e)	
				{
					//if on topleft corner
					return NONE;
				}
					
			case TOP:
				try
				{
					this.checkRange(row-1, col);
					return this.array[row-1][col].getMark();
				}
				catch (SquareDoesNotExist e)
				{
					//if on top wall
					return NONE;
				}
					
			case TOPRIGHT:
				try
				{
					this.checkRange(row-1, col+1);
					return this.array[row-1][col+1].getMark();
				}
				catch (SquareDoesNotExist e)
				{
					//if on topright corner
					return NONE;
				}
				
			case RIGHT:
				try
				{
					this.checkRange(row, col+1);
					return this.array[row][col+1].getMark();
				}
				catch (SquareDoesNotExist e)
				{
					//if on right wall
					return NONE;
				}
				
			case BOTRIGHT:
				try
				{
					this.checkRange(row+1, col+1);
					return this.array[row+1][col+1].getMark();
				}
				catch (SquareDoesNotExist e)
				{
					//if on botright corner
					return NONE;
				}
				
			case BOT:
				try
				{
					this.checkRange(row+1, col);
					return this.array[row+1][col].getMark();
				}
				catch (SquareDoesNotExist e)
				{
					//if on bottom wall
					return NONE;
				}
				
			case BOTLEFT:
				try
				{
					this.checkRange(row+1, col-1);
					return this.array[row+1][col-1].getMark();
				}
				catch (SquareDoesNotExist e)
				{
					//if on botleft corner
					return NONE;
				}
				
			case LEFT:
				try
				{
					this.checkRange(row, col-1);
					return this.array[row][col-1].getMark();
				}
				catch (SquareDoesNotExist e)
				{
					//if on left wall
					return NONE;
				}
				
			default:
				throw new DirDoesNotExist("Direction " + dir + " does not exist");
		}*/
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
		
		switch (dir)
		{
			case TOPLEFT:
				try
				{
					this.checkRange(row-1, col-1);
					return this.array[row-1][col-1];
				}
				catch (SquareDoesNotExist e)	
				{
					//if on topleft corner
					return new Square();
				}
					
			case TOP:
				try
				{
					this.checkRange(row-1, col);
					return this.array[row-1][col];
				}
				catch (SquareDoesNotExist e)
				{
					//if on top wall
					return new Square();
				}
					
			case TOPRIGHT:
				try
				{
					this.checkRange(row-1, col+1);
					return this.array[row-1][col+1];
				}
				catch (SquareDoesNotExist e)
				{
					//if on topright corner
					return new Square();
				}
				
			case RIGHT:
				try
				{
					this.checkRange(row, col+1);
					return this.array[row][col+1];
				}
				catch (SquareDoesNotExist e)
				{
					//if on right wall
					return new Square();
				}
				
			case BOTRIGHT:
				try
				{
					this.checkRange(row+1, col+1);
					return this.array[row+1][col+1];
				}
				catch (SquareDoesNotExist e)
				{
					//if on botright corner
					return new Square();
				}
				
			case BOT:
				try
				{
					this.checkRange(row+1, col);
					return this.array[row+1][col];
				}
				catch (SquareDoesNotExist e)
				{
					//if on bottom wall
					return new Square();
				}
				
			case BOTLEFT:
				try
				{
					this.checkRange(row+1, col-1);
					return this.array[row+1][col-1];
				}
				catch (SquareDoesNotExist e)
				{
					//if on botleft corner
					return new Square();
				}
				
			case LEFT:
				try
				{
					this.checkRange(row, col-1);
					return this.array[row][col-1];
				}
				catch (SquareDoesNotExist e)
				{
					//if on left wall
					return new Square();
				}
				
			default:
				throw new DirDoesNotExist("Direction " + dir + " does not exist");
		}
				
		
		
	}
	
	/**
	 * Returns the mark at this.array[row][col]
	 * @param row
	 * @param col
	 * @return
	 * @throws SquareDoesNotExist
	 */
	public char getMarkAt(int row, int col) throws SquareDoesNotExist
	{
		this.checkRange(row, col);
		
		return this.array[row][col].getMark();
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
		
		
		
		if (this.array[row][col].isRevealed())
			return;
		
		this.array[row][col].reveal();
		
		
		if (this.array[row][col].getMark() == BLANK)
		{
			//reveal all adjacent if square is blank
			
			/*Square next;
			for (int dir = 0; dir <= DIRMAX; dir++)
			{
				try
				{
					next = this.getAdjacentSquare(row, col, dir);
					next.reveal();
				}
				catch (SquareDoesNotExist e) {return;} 
				catch (DirDoesNotExist e) {return;}
				catch (SquareAlreadyRevealed e) {return;}
			}*/
			
			/*
			for (int r = -1; r > 1; r++)
			{
				for (int c = -1; c > 1; c++)
				{
					if (r != 0 && c != 0)
					{
						try //topleft
						{
							this.reveal(row+r, col+c);
						} catch (SquareDoesNotExist e) {continue;}
						  catch (SquareAlreadyRevealed e) {continue;}
					}
				}
			}*/
			
			
			try //topleft
			{
				this.reveal(row-1, col-1);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			try //top
			{
				this.reveal(row-1, col);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			try //topright
			{
				this.reveal(row-1, col+1);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			try //right
			{
				this.reveal(row, col+1);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			try //botright
			{
				this.reveal(row+1, col+1);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			try	//bot
			{
				this.reveal(row+1, col);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			try //botleft
			{
				this.reveal(row+1, col-1);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			try //left
			{
				this.reveal(row,   col-1);
			} catch (SquareDoesNotExist e) {}
			  catch (SquareAlreadyRevealed e) {}
			
			  /*catch (SquareDoesNotExist e) {return;}
			  catch (SquareAlreadyRevealed e) {return;}*/
		}
		
		
		if (DEBUG)
		{
			System.out.println(this);
		}
		
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
		return this.array[row][col].isHidden();
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
		return this.array[row][col].isRevealed();
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
		
		this.array[row][col].toggleFlag();
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
		
		return this.array[row][col].hasFlag();
	}
	
	
	public int flagsAround(int row, int col)
	{
		//asserts that row and col are valid
		try 
		{
			this.checkRange(row, col);
		} 
		catch (SquareDoesNotExist e1) {
			return 0;
		}
		
		
		int around = 0;
		
		for (int dir = 0; dir <= DIRMAX; dir++)
		{
			try
			{
				if (this.getAdjacentSquare(row, col, dir).hasFlag())
					around++;
			} 
			catch (SquareDoesNotExist e) 
			{} 
			catch (DirDoesNotExist e) 
			{}
		}
		
		return around;
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
		String s = new String();
		
		//add letters on top
		s += "     "; //5 spaces for padding
		char letter = 'A';
		for (int i = 0; i < this.width; i++)
		{
			s += letter;
			s += ' '; //space for padding
			letter++;
		}
		s += '\n';
		
		//add top border
		s += "   + ";
		for (int i = 0; i < this.width; i++)
		{
			s += "- ";
		}
		s += '+';
		s += '\n';
		
		for (int i = 0; i < this.height; i++)
		{
			//add number at start of line
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
			
			
			for (int j = 0; j < this.width; j++)
			{
				if (this.array[i][j].isRevealed())
				{
					s += this.array[i][j].getMark();
				}
				else if (this.array[i][j].hasFlag())
				{
					s += FLAG;
				}
				else
				{
					s += HIDDEN;
				}
				s += ' ';
			}
			s += "| ";
			
			//add number at end of line
			s += (i + 1);
			
			s += '\n';
		}
		
		//add bottom border
		s += "   + ";
		for (int i = 0; i < this.width; i++)
		{
			s += "- ";
		}
		s += '+';
		s += '\n';
		
		//add letters on bottom
		s += "     "; //5 spaces for padding
		letter = 'A';
		for (int i = 0; i < this.width; i++)
		{
			s += letter;
			s += ' '; //space for padding
			letter++;
		}
		s += '\n';
		
		
		
		return s;
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
		String s = new String();
		
		//add letters on top
		s += "     "; //5 spaces for padding
		char letter = 'A';
		for (int i = 0; i < this.width; i++)
		{
			s += letter;
			s += ' '; //space for padding
			letter++;
		}
		s += '\n';
		
		//add top border
		s += "   + ";
		for (int i = 0; i < this.width; i++)
		{
			s += "- ";
		}
		s += '+';
		s += '\n';
		
		for (int i = 0; i < this.height; i++)
		{
			//add number at start of line
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
			
			for (int j = 0; j < this.width; j++)
			{
				s += this.array[i][j].getMark();
				s += ' ';
			}
			
			s += "| ";
			//add number at end of line
			s += (i + 1);
			
			s += '\n';
		}
		
		//add bottom border
		s += "   + ";
		for (int i = 0; i < this.width; i++)
		{
			s += "- ";
		}
		s += '+';
		s += '\n';
		
		//add letters on bottom
		s += "     "; //5 spaces for padding
		letter = 'A';
		for (int i = 0; i < this.width; i++)
		{
			s += letter;
			s += ' '; //space for padding
			letter++;
		}
		s += '\n';
		
		
		
		return s;
	}
	
}
