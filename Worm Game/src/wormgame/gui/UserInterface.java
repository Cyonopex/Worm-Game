package wormgame.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import wormgame.game.WormGame;

public class UserInterface implements Runnable {

	private JFrame frame;
	private WormGame game;
	private int pieceLength;
	private DrawingBoard board;
	private ScoreBoard scoreBoard;
	private Image[] scoreNumbers;
	private final static int NUM_IMAGES = 10;
	public final static int IMAGE_HEIGHT = 50;
	public final static int IMAGE_WIDTH = 30;

	public UserInterface(WormGame game, int pieceLength) {
		this.game = game;
		this.pieceLength = pieceLength;
		loadImages();
	}

	@Override
	public void run() {
		frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		createComponents(frame.getContentPane());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

	}

	private void createComponents(Container container) {
		board = new DrawingBoard(game, pieceLength);
		board.setPreferredSize(new Dimension(pieceLength * (game.getBoardLength()), pieceLength * game.getBoardHeight()));
		frame.addKeyListener(new KeyboardListener(game));
		container.add(board);
		
		scoreBoard = new ScoreBoard(scoreNumbers);
		scoreBoard.setPreferredSize(new Dimension(pieceLength * game.getBoardLength(), IMAGE_HEIGHT));
		container.add(scoreBoard, BorderLayout.SOUTH);
		
	}
	
	public DrawingBoard getDrawingBoard() {
		return board;
	}
	
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
	
	private void loadImages() {
		try {
			scoreNumbers = new Image[NUM_IMAGES];
			for (int i = 0; i < NUM_IMAGES; i++) {
				URL url = UserInterface.class.getResource("/img/" + i + ".gif");
				scoreNumbers[i] = new ImageIcon(url).getImage();
			} 
		} catch (Exception e) {
			System.out.println("Unable to load images");
		}
	}
}

