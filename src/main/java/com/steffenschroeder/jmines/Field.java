/**
 * 
 */
package com.steffenschroeder.jmines;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steffen Schroeder
 */
public abstract class Field {
	
	private long minesAround = 0;
    
	private boolean isOpended = false;

	private boolean flagged;
    
	protected List<Field> neighbors = new ArrayList<Field>();

	

    public List<Field> getNeighbors() {
		return neighbors;
	}

	public long getNumerOfMinesAround() {
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
		minesAround = this.neighbors.parallelStream().filter(Field::isMine).count();
	}

    /**
     * the field was opened by the user
     */
	public void open() {
    	openNonRecurse();
    }
	
	public final void openNonRecurse(){
		isOpended = true;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void toggleFlag() {
		flagged = !flagged;
	}

	@Override
	public String toString() {
		return "Field [isOpended=" + isOpended + ", flagged=" + flagged + ", isMine()=" + isMine() + "]";
	}
}
