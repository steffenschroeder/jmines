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

/**
 * Class to represent a sqare in a Minesweeper grid
 * @author Juliana Peña
 *
 */
class Square implements Consts
{
	private char mark;
	private boolean hidden;
	private boolean flagged;
	
	public void fill(char mark)
	{
		this.mark = mark;
	}
	
	public boolean isHidden()
	{
		return this.hidden;
	}
	
	public char reveal()
	{
		this.hidden = false;
		return this.mark;
	}
	
	public void hide()
	{
		this.hidden = true;
	}
	
	public char getMark()
	{
		return this.mark;
	}
	
	public void toggleFlag()
	{
		if (this.flagged)
		{
			if (DEBUG)
				System.out.println("Unflagging...");
			this.flagged = false;
		}
		else 
		{
			if (DEBUG)
				System.out.println("Flagging...");
			this.flagged = true;
		}
	}
	
	
	public boolean hasFlag()
	{
		return this.flagged;
	}
	
	/**
	 * Constructor
	 * @param mark
	 * @param hidden
	 */
	public Square(char mark, boolean hidden)
	{
		this.fill(mark);
		
		if (hidden)
			this.hide();
		else
			this.reveal();
		
		this.flagged = false;
	}
	
	/**
	 * Default constructos
	 * Initializes the Square to mark=BLANK and hidden=true
	 */
	public Square()
	{
		this(NONE, true);
	}
	
}
