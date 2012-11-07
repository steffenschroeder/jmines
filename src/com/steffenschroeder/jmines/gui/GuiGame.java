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


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.steffenschroeder.jmines.Field;
import com.steffenschroeder.jmines.GamestateObserver;
import com.steffenschroeder.jmines.MineBoard;
import com.steffenschroeder.jmines.MineBoardBuilder;
import com.steffenschroeder.jmines.MineGame;

/**
 * Minesweeper Gui Frontend
 * 
 * @author Juliana Pena
 * 
 */
public class GuiGame implements GamestateObserver {
	private JFrame window;

	private JButton face;

	private JPanel boardPanel;

	private JButton[][] buttons;
	
	private int pressedRow;
	private int pressedColumn;

	private Icon exploded;
	private Icon cool;
	private Icon sad;
	private Icon mine;
	private Icon win;
	private Icon flag;
	private Icon badFlag;
	private Icon smile;
	private Icon worried;

	// private Grid grid;
	private int width  = 9;
	private int height = 9;
	private int mines  = 10;

	private MineBoard board;
	private MineGame game;

	public GuiGame() {
		preLoadIcons();
		this.reset();
	}

	private void preLoadIcons() {
		ClassLoader cl = this.getClass().getClassLoader();
		exploded = new ImageIcon(cl.getResource("img/bang.png"));
		cool = new ImageIcon(cl.getResource("img/face-cool.png"));
		sad = new ImageIcon(cl.getResource("img/face-sad.png"));
		mine = new ImageIcon(cl.getResource("img/mine.png"));
		win = new ImageIcon(cl.getResource("img/face-win.png"));
		badFlag = new ImageIcon(cl.getResource("img/warning.png"));
		flag = new ImageIcon(cl.getResource("img/flag.png"));
		worried = new ImageIcon(cl.getResource("img/face-worried.png"));
		smile = new ImageIcon(cl.getResource("img/face-smile.png"));
	}

	private void reset() {
		if (null!=window)
		{
			this.window.setVisible(false);
		}
		
		this.window = new JFrame("JMines");
		createWindow();
	}

	private void createWindow() {
		
		board = new MineBoardBuilder().createCustomBoard(height, width, mines).getBoard();;
		game = new MineGame(board);
		game.addGameListiner(this);


		face = new JButton(smile);
		face.addActionListener(new FaceManager());

		boardPanel = this.createBoard();

		JMenuBar menuBar = buildMenu();
		window.setJMenuBar(menuBar);
		window.add(this.face, BorderLayout.NORTH);
		window.add(this.boardPanel, BorderLayout.CENTER);

		int pxwidth = this.width * 40;
		int pxheight = this.height * 40 + 50;

		window.setSize(pxwidth, pxheight); // in px
		window.setVisible(true);
	}

	private JMenuBar buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = new JMenu();
		mainMenu.setText("Level");
		mainMenu.setMnemonic('l');
		
		ButtonGroup sizeGroup = new ButtonGroup();
		
		JRadioButtonMenuItem item = buildMenuItem("Easy", true, 'e', 9, 9, 10); 
		mainMenu.add(item);
		sizeGroup.add(item);
		
		item = buildMenuItem("Medium", false, 'm', 16, 16, 40); 
		mainMenu.add(item);
		sizeGroup.add(item);
		
		item = buildMenuItem("Hard", false, 'h', 16, 30, 99); 
		mainMenu.add(item);
		sizeGroup.add(item);
		
		menuBar.add(mainMenu);
		return menuBar;
	}
	
	private JRadioButtonMenuItem buildMenuItem( String text, boolean selected, char mnemonic, final int i_height, final int i_width, final int i_mines)
	{
		JRadioButtonMenuItem rmbi_small;
		rmbi_small = new JRadioButtonMenuItem(text,selected);
		rmbi_small.setMnemonic(mnemonic);

		
		rmbi_small.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			 mines = i_mines;
			 height = i_height;
			 width = i_width;
			 reset();
				
			}
		});
		
		return rmbi_small;
	}
	

	private JPanel createBoard() {
		this.buttons = new JButton[this.height][this.width];
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(this.height, this.width));

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.buttons[i][j] = new JButton();
				panel.add(this.buttons[i][j]);

				this.buttons[i][j].addMouseListener(new ButtonManager(i, j));
			}
		}

		return panel;
	}

	class FaceManager implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GuiGame.this.reset();
		}

	}

	class ButtonManager extends MouseAdapter {
		private int row;
		private int col;

		public ButtonManager(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			face.setIcon(cool);
			if (e.getButton() == MouseEvent.BUTTON1) {
				// right mouse button uncovers that button

				pressedRow = row;
				pressedColumn = col;
				game.open(row, col);
				buttons[row][col].setEnabled(false);
			}


			else if (e.getButton() == MouseEvent.BUTTON3) {
				// right mouse button toggles flag

				// if button is disabled, do nothing
				if (!buttons[row][col].isEnabled()) {
					return;
				}

				game.toggleFlag(row, col);

			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(board.getField(row, col).isOpen())
			{
				return;
			}
			if (e.getButton() == MouseEvent.BUTTON1	|| e.getButton() == MouseEvent.BUTTON2) {
				face.setIcon(worried);
			}
		}

	}

	public static void main(String args[]) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

		new GuiGame();
	}

	@Override
	public void nofifyGameWon() {
//		for (Field field : board) {
//			field.open();
//		}
		this.face.setIcon(win);

	}

	@Override
	public void notifyGameLost() {
		this.face.setIcon(sad);
	}

	@Override
	public void nofifyBoardChanged() {
		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				Field f = board.getField(row, column);
				JButton b = buttons[row][column];
				if (f.isOpen()) {
					if(f.isMine())
					{
						Icon i;
						if (pressedRow == row && pressedColumn == column) {
							i = exploded;
						} else if(f.isFlagged()){
							i = badFlag;
						} else {
							i = mine;
						}
						b.setIcon(i);
						b.setDisabledIcon(i);
					}
					else
					{
						if (f.getNumerOfMinesAround() == 0) {
							b.setText("");
						} else {
							b.setForeground(Color.RED);
							b.setText(f.getNumerOfMinesAround() + "");
						}
					}

					b.setEnabled(false);
				}
				else
				{
					Icon i = null;
					if (f.isFlagged())
					{
						i = flag;
					}
					b.setIcon(i);
					b.setDisabledIcon(i);
					
				}
			}

		}

	}
}
