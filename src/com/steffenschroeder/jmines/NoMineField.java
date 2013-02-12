package com.steffenschroeder.jmines;

public class NoMineField extends Field {

	static int level = 0;
    @Override
    public boolean isMine() {
        return false;
    }

    @Override
    public void open() {
    	super.open();
        if (noMinesAround()) {
            for (Field neighbor : neighbors) {
                if (!neighbor.isOpen() && !neighbor.isFlagged()) {
      	               	neighbor.open();
                }
            }
        }
    }

	private boolean noMinesAround() {
		return getNumerOfMinesAround() == 0;
	}
}
