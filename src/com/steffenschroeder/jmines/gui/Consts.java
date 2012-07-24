package com.steffenschroeder.jmines.gui;
/*
    This file is part of JMines.

    JMines is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JMines.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Constants used in Minesweeper game
 * 
 * @author Juliana Peña
 *
 */
interface Consts 
{
	//flag to show if debug mode is on or off
	public static final boolean DEBUG = true;
	
	//characters to represent marks on grid
	public static final char BLANK = ' ';
	public static final char MINE  = '*';
	public static final char ONE   = '1';
	public static final char TWO   = '2';
	public static final char THREE = '3';
	public static final char FOUR  = '4';
	public static final char FIVE  = '5';
	public static final char SIX   = '6';
	public static final char SEVEN = '7';
	public static final char EIGHT = '8';
	
	//character to show if square is hidden
	public static final char HIDDEN = (char) 0x2588; // utf code of █ in hex
	
	//character to show if square has flag
	public static final char FLAG = 'F';
	
	//character to return if referencing walls or corners outside the grid, or just-created squares
	public static final char NONE = '\0';
	
	//numbers to represent directions
	//numbers start at topleft corner and cycle clockwise
	public static final int TOPLEFT  = 0;
	public static final int TOP      = 1;
	public static final int TOPRIGHT = 2;
	public static final int RIGHT    = 3;
	public static final int BOTRIGHT = 4;
	public static final int BOT      = 5;
	public static final int BOTLEFT  = 6;
	public static final int LEFT     = 7;
	
	//last direction, to use in for loops
	public static final int DIRMAX   = 7;
	
}
