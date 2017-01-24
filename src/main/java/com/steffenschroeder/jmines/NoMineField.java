package com.steffenschroeder.jmines;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NoMineField extends Field {

 
	
    private Deque<Field> minesToOpen;

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
		minesToOpen = new LinkedList<Field>();

		minesToOpen.push(this);
		while(!minesToOpen.isEmpty()){
			Field currentFieldToOpen = minesToOpen.pop();
			currentFieldToOpen.openNonRecurse();
			if(currentFieldToOpen.getNumerOfMinesAround()==0){
		            putNonOpenNeighborsToTheQueue(currentFieldToOpen.getNeighbors());
		        }
			}
		}

	private void putNonOpenNeighborsToTheQueue(List<Field> neigbors) {
		for (Field neighbor : neigbors) {
		    if (!neighbor.isOpen() && !neighbor.isFlagged()) {
		         if(!minesToOpen.contains(neighbor)){
		        	minesToOpen.push(neighbor);
		         }
		    	 
		    }
		}
	}
}
