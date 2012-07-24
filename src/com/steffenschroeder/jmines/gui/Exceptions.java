package com.steffenschroeder.jmines.gui;
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
 * Exception thrown when a direction is not in the range specified in Consts
 * @author Juliana Peña
 * @see Consts
 *
 */
class DirDoesNotExist extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DirDoesNotExist(String s)
	{
		super(s);
	}
}

/**
 * Exception thrown when a mark is not described in Consts
 * @author Juliana Peña
 * @see Consts
 *
 */
class MarkDoesNotExist extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MarkDoesNotExist(String s)
	{
		super(s);
	}
}

/**
 * Exception thrown when calling a Square coordinate not in the Grid
 * @author Juliana Peña
 * @see Grid
 *
 */
class SquareDoesNotExist extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SquareDoesNotExist(String s)
	{
		super(s);
	}
}

/**
 * Exception thrown when creation of Grid failed because of improper dimensions
 * 
 * @author Juliana Peña
 * @see Grid#Grid()
 *
 */
class UnableToCreateGrid extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnableToCreateGrid(String s)
	{
		super(s);
	}
}

/**
 * Exception thrown when trying to reveal a Square that has already been revealed
 * @author Juliana Peña
 *
 */
class SquareAlreadyRevealed extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SquareAlreadyRevealed(String s)
	{
		super(s);
	}
}

/**
 * Exception thrown when trying to reveal a Square that has a flag
 * @author Juliana Peña
 *
 */
class SquareHasFlag extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SquareHasFlag(String s)
	{
		super(s);
	}
}

