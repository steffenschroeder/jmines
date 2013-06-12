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
        }else{
        	System.out.println("Mines around");
        }
    }

	private boolean noMinesAround() {
		int numerOfMinesAround = getNumerOfMinesAround();
		if(numerOfMinesAround>0)
			System.out.println("Mines around:" + numerOfMinesAround);
		return numerOfMinesAround == 0;
	}
}
