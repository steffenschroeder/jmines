package com.steffenschroeder.jmines.gui.ascii;

import com.steffenschroeder.jmines.Field;
import com.steffenschroeder.jmines.MineBoard;

public class ASCIIPrintableBoard extends MineBoard {

	public static final char HIDDEN = (char) 0x2588;
	
	//character to show if square has flag
	public static final char FLAG = 'F';
	
	//character to return if referencing walls or corners outside the grid, or just-created squares
	public static final String NONE = ""+'\0';

	private static final String MINE = "*";

	public ASCIIPrintableBoard(int rows, int cols) {
		super(rows, cols);
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
		String s = "";
		
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
		for (int i = 0; i < getColumns(); i++)
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
		for (int i = 0; i < getColumns(); i++)
		{
			s += "- ";
		}
		s += '+';
		s += '\n';
		return s;
	}

	private String printInnerField(String s, boolean printSolution) throws SquareDoesNotExist {
		for (int i = 0; i < getRows(); i++)
		{
			s = left(s, i);
			
			for (int j = 0; j < getColumns(); j++)
			{
				
				Field f = mines[i][j];
				
				if (f.isOpen() || printSolution)
				{
					s += getMark(f);
				}
				else if (f.isFlagged())
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

	private String getMark(Field f) {
		if(f.isMine()){
			return MINE;
		}
		int numberOfMines = f.getNumerOfMinesAround();
		if(0==numberOfMines){
			return NONE;
			
		}else{
		return "" + f.getNumerOfMinesAround();
		}
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

}
