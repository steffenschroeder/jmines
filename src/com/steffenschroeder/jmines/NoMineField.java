package com.steffenschroeder.jmines;

import java.util.Deque;
import java.util.LinkedList;

public class NoMineField extends Field {

 
	
    @Override
    public boolean isMine() {
        return false;
    }

    @Override
    public void open() {
    	super.open();
        openMines();
    }

	private void openMines() {
		Deque<Field> minesToOpen = new LinkedList<Field>();

		minesToOpen.push(this);
		while(!minesToOpen.isEmpty()){
			Field currentFieldToOpen = minesToOpen.pop();
			currentFieldToOpen.openNonRecurse();
			if(currentFieldToOpen.getNumerOfMinesAround()==0){
		            putNonOpenNeighborsToTheQueue(minesToOpen, currentFieldToOpen);
		        }
			}
		}

	private void putNonOpenNeighborsToTheQueue(Deque<Field> minesToOpen,
			Field currentFieldToOpen) {
		for (Field neighbor : currentFieldToOpen.getNeighbors()) {
		    if (!neighbor.isOpen() && !neighbor.isFlagged()) {
		         if(!minesToOpen.contains(neighbor)){
		        	minesToOpen.push(neighbor);
		         }
		    	 
		    }
		}
	}
}
