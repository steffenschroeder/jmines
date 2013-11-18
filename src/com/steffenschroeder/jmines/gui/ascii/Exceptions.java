package com.steffenschroeder.jmines.gui.ascii;


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
	
	public SquareHasFlag()
	{
		super("Square has a flag, cannot reveal");
	}
}

