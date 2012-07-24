/**
 * 
 */
package com.steffenschroeder.jmines;

import java.util.Iterator;

class TwoDimensionArrayIterator<T> implements Iterator<T>
{
	private T[][] array;
	private int   currentRow    = 0;
	private int   currentColumn = 0;
	
	public TwoDimensionArrayIterator(T[][] array)
	{ 
	 this.array = array;
	}
	
	@Override
	public boolean hasNext() {
		return currentRow < array.length;
	}

	@Override
	public T next(){
		T returnField = array[currentRow][currentColumn];
		currentColumn++;
		if(currentColumn>=array[currentRow].length)
		{
			currentColumn = 0;
			currentRow++;
		}

		return returnField;
	}

	@Override
	public void remove() {
		array[currentRow][currentColumn] = null;
	}
	
	
	
}