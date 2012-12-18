/**
 * 
 */
package com.steffenschroeder.jmines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Steffen Schroeder
 */
public abstract class Field {
	
	private int minesAround = 0;
    
	private boolean isOpended = false;

	private boolean flagged;
    
	protected List<Field> neighbors = new ArrayList<Field>();

	

    public int getNumerOfMinesAround() {
        return minesAround;
    }

    public abstract boolean isMine();
    
   
    public boolean isOpen() {
        return isOpended;
    }

    public void setNeighborhood(List<Field> neigbors) {
    	this.neighbors = neigbors;
    	countMinesInNeighborhood();
    }


	private void countMinesInNeighborhood() {
		minesAround = 0;
    	for (Field field : this.neighbors) {
    		minesAround += (field.isMine()? 1 : 0 );
		}
	}

    /**
     * the field was opened by the user
     */
    public void open() {
    	isOpended = true;
    }

	public boolean isFlagged() {
		return flagged;
	}

	public void toggleFlag() {
		flagged = !flagged;
	}
}
