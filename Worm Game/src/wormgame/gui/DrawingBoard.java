package wormgame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import wormgame.domain.Apple;
import wormgame.domain.Piece;
import wormgame.domain.Worm;
import wormgame.game.WormGame;

public class DrawingBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	private WormGame game;
	private int pieceLength;
	
	public DrawingBoard(WormGame game, int pieceLength) {
		this.game = game;
		this.pieceLength = pieceLength;
		setBackground(new Color(26, 36, 50));
	}
	
	public void changeMode(boolean wrapAroundMode) {
		if(wrapAroundMode) {
			setBackground(new Color(26, 50, 32));
		} else {
			setBackground(new Color(26, 36, 50));
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Worm worm = game.getWorm();
		
		Color snakeColor, snakeHeadColor;
		if(!game.isGameOver() || game.firstGame()) {
			snakeColor = new Color(113, 188, 201);
			snakeHeadColor = new Color(63, 151, 167);
		} else {
			snakeColor = new Color(226, 29, 29);
			snakeHeadColor = new Color(141, 18, 18);
		}
		new Color(26, 50, 32);
		graphics.setColor(snakeColor);
		
		List<Piece> wormPieces = worm.getPieces();
		Iterator<Piece> snakePieceIterator = wormPieces.iterator();
		while(snakePieceIterator.hasNext()) {
			Piece piece = snakePieceIterator.next();
			if(!snakePieceIterator.hasNext()) {
				graphics.setColor(snakeHeadColor);
			}
			graphics.fillRect(piece.getX()*pieceLength, piece.getY()*pieceLength, pieceLength, pieceLength);
		}

		
		Color appleColor = new Color(51, 213, 42);
		graphics.setColor(appleColor);
		Apple apple = game.getApple();
		graphics.fillOval(apple.getX()*pieceLength, apple.getY()*pieceLength, pieceLength, pieceLength);
	}
	
}
