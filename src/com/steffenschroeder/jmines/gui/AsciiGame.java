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

import java.util.Scanner;

/**
 * Minesweeper Ascii Frontent
 * @author Juliana Peña
 *
 */
public class AsciiGame implements Consts
{
	private int mines;
	private int width;
	private int height;
	private Grid grid;
	
	/**
	 * Has current game been lost?
	 */
	private boolean lost;
	
	/**
	 * Has current game been won?
	 */
	private boolean won;
	
	/**
	 * Does player want to stop playing?
	 */
	private boolean done;
	
	
	public AsciiGame()
	{
		this.welcome();
		this.newGame();
		this.play();
		
	}
	
	private void welcome()
	{
		System.out.println("Welcome to JMines!");
	}
	
	private void newGame()
	{
		this.done = false;
		this.lost = false;
		this.won  = false;
		
		this.chooseSize();
		try 
		{
			this.grid = new Grid(mines, width, height);
		} 
		catch (UnableToCreateGrid e) 
		{
			System.out.println("Error:");
			System.out.println(e.getMessage());
			System.out.println("Game will now quit.");
			System.exit(0);
		}
	}
	
	private void chooseSize()
	{
		Scanner s = new Scanner(System.in);
		
		//ask if custom size
		boolean done = false;
		
		while (!done)
		{
			System.out.print("Do you want to choose a custom size? (y/n) ");
			char y = s.nextLine().toLowerCase().charAt(0);
		
			switch (y)
			{
				case 'y':
					System.out.print("Width: ");
					this.width = s.nextInt();
					s.nextLine();
					System.out.print("Height: ");
					this.height = s.nextInt();
					s.nextLine();
					System.out.print("Number of mines: ");
					this.mines = s.nextInt();
					s.nextLine();
					done = true;
					break;
					
				case 'n':
					System.out.println("Defaulting to a 10x10 grid with 10 mines.");
					this.mines = 10;
					this.width = 10;
					this.height = 10;
					
					done = true;
					break;
				
				default:
					System.out.println("Incorrect input. Try again.");
					break;				
			}
		}
	}
	
	private void checkWon()
	{
		for (int i = 0; i < this.height; i++)
		{
			for (int j = 0; j < this.width; j++)
			{
				try 
				{
					if (this.grid.getMarkAt(i, j) != MINE && this.grid.isHidden(i, j))
					{
						return;
					}
				} 
				catch (SquareDoesNotExist e) 
				{
					continue;
				}
			}
		}
		this.won = true;
	}
	
	private void wonMessage()
	{
		System.out.println("Congratulations! You won!");
	}
	
	private void lostMessage()
	{
		System.out.println("Sorry, you lost.");
	}
	
	private void takeInput()
	{
		boolean chosen = false;
		while (!chosen)
		{
			Scanner s = new Scanner(System.in);
			int row;
			int col;
			
			System.out.print("(F)lag/unflag, (R)eveal or (Q)uit: ");
			char choice = s.nextLine().toLowerCase().charAt(0);
			
			switch (choice)
			{
			case 'f':
				chosen = true;
				
				System.out.print("Which column? ");
				col = s.nextLine().toLowerCase().charAt(0) - 'a';
				
				System.out.print("Which row? ");
				row = s.nextInt();
				row = row - 1;
				s.nextLine();
				
				try
				{
					this.grid.toggleFlag(row, col);
					chosen = true;
				}
				catch (SquareAlreadyRevealed e)
				{
					System.out.println("Can't flag, square has been revealed!");
				}
				catch (SquareDoesNotExist e)
				{
					System.out.println("Square coordinates " + row + "," + col + " are incorrect, try again.");
				}
				break;
								
			case 'r':
				
				System.out.print("Which column? ");
				col = s.nextLine().toLowerCase().charAt(0) - 'a';
				
				System.out.print("Which row? ");
				row = s.nextInt();
				row = row - 1;
				s.nextLine();
				
				try
				{
					this.grid.reveal(row, col);
					chosen = true;
					if (this.grid.getMarkAt(row, col) == MINE)
					{
						this.lost = true;
					}
				}
				catch (SquareHasFlag e)
				{
					System.out.println("Cannot reveal square, has a flag! Unflag it first.");
				}
				catch (SquareAlreadyRevealed e)
				{
					System.out.println("Square has already been revealed!");
				} 
				catch (SquareDoesNotExist e) 
				{
					System.out.println("Square coordinates are incorrect, try again.");
				} 
				break;
			
			case 'q':
				this.done = true;
				return;
			
			default:
				System.out.println("Incorrect choice, try again");
				break;
			}
			
		}
	}
	
	
	public void play()
	{
		while (!this.done)
		{
			/*
			 * 1. Print grid
			 * 2. Take input
			 * 3. Check if done
			 */
			while (!this.lost && !this.won)
			{
				if (this.done)
					break;
				
				System.out.println(this.grid);
				this.takeInput();
				
				this.checkWon();
				
			}
			
			if (this.won)
				this.wonMessage();
			else if (this.lost)
				this.lostMessage();
			System.out.println("The solution was:");
			System.out.println(this.grid.solutionToString());
			
			this.askAnotherGame();
		}
		System.out.println("Thanks for playing!");
	}

	private void askAnotherGame() 
	{
		Scanner s = new Scanner(System.in);
		
		System.out.print("Want to play again? (y/n) ");
		char choice;
		while (true)
		{
			choice = s.nextLine().toLowerCase().charAt(0);
			switch (choice)
			{
				case 'y':
					this.newGame();
					return;
				case 'n':
					this.done = true;
					return;
				default:
					System.out.print("Incorrect choice, try again: ");
					break;
			}
		}
	}

	public static void main(String[] args) 
	{
		new AsciiGame();
	}
	

}
