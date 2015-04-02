package com.steffenschroeder.jmines;

import com.steffenschroeder.jmines.gui.ascii.ASCIIPrintableBoard;

public class ACSCIIBoardBuilder extends MineBoardBuilder{

	@Override
	protected MineBoard instanciate(int rows, int columns) {
		
		return new ASCIIPrintableBoard(rows, columns);
	}

}
