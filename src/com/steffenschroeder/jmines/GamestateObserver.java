package com.steffenschroeder.jmines;

public interface GamestateObserver {
	public void nofifyGameWon();
	public void notifyGameLost();
	public void nofifyBoardChanged();
}
