package wormgame.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import wormgame.game.WormGame;

public class UserInterface implements Runnable {

	private JFrame frame;
	private WormGame game;
	private int pieceLength;
	DrawingBoard board;

	public UserInterface(WormGame game, int pieceLength) {
		this.game = game;
		this.pieceLength = pieceLength;
	}

	@Override
	public void run() {
		frame = new JFrame("Snake");
		frame.setPreferredSize(new Dimension(pieceLength * (game.getBoardLength()+1), pieceLength * (game.getBoardHeight()+2)));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		createComponents(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);

	}

	private void createComponents(Container container) {
		board = new DrawingBoard(game, pieceLength);
		frame.addKeyListener(new KeyboardListener(game));
		container.add(board);
	}
	
	public Component getUpdatable() {
		return board;
	}
	
}

