package wormgame.game;

import java.util.Random;

import wormgame.Direction;
import wormgame.domain.Apple;
import wormgame.domain.Worm;
import wormgame.gui.DrawingBoard;
import wormgame.gui.ScoreBoard;

public class WormGame implements Runnable {
	
	private Random random;
	private Worm worm;
	private Apple apple;
	
	private Thread runThread;
	
	private int boardLength, boardHeight;
	private boolean gameRunning;
	private boolean wrapsAround;
	private boolean firstGame;
	private boolean canStartNewGame;
	
	private DrawingBoard drawingboard;
	private ScoreBoard scoreBoard;
	
	private static final int NEW_GAME_DELAY_TIME = 500;
	
	public WormGame(int boardLength, int boardHeight, boolean wrapsAround) {
		this.boardLength = boardLength;
		this.boardHeight = boardHeight;
		this.wrapsAround = wrapsAround;
		this.firstGame = true;
		this.gameRunning = false;
		this.canStartNewGame = true;
		resetGame();
	}
	
	public int getBoardLength() {
		return boardLength;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public Worm getWorm() {
		return worm;
	}
	
	public void setWorm(Worm worm) {
		this.worm = worm;
	}
	
	public Apple getApple() {
		return apple;
	}
	
	public void setApple(Apple apple) {
		this.apple = apple;
	}
	
	public boolean firstGame() {
		return firstGame;
	}
	
	public boolean isGameOver() {
		return !gameRunning;
	}
	
	public boolean canStartNewGame() {
		return canStartNewGame;
	}
	
	public void setDrawingBoard(DrawingBoard db) {
		this.drawingboard = db;
	}
	
	public void setScoreBoard(ScoreBoard sb) {
		this.scoreBoard = sb;
	}
	
	private void makeNewApple() {
		int appleX, appleY;
		do {
			appleX = random.nextInt(boardLength);
			appleY = random.nextInt(boardHeight);
			this.apple = new Apple(appleX, appleY);
		} while (worm.runsInto(apple));

	}
	
	@Override
	public void run() {
		while(gameRunning) {
			
			try {

				Thread.currentThread();
				Thread.sleep(100);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			worm.move();

			if(worm.runsInto(apple)) {
				worm.grow();
				this.makeNewApple();
				scoreBoard.incrementScore();
			}
			
			if(worm.runsIntoItself() ) {
				gameRunning = false;
			}
			
			if(!wrapsAround && worm.leavesBoundary()) {
				gameRunning = false;
			}


			drawingboard.repaint();
		}
		
		//when game ends, block input for 0.5 second (to prevent user from accidentally starting a new game first)
		//0.5 seconds seems to be about right such that user won't notice delay in starting new game
		try {
			Thread.sleep(NEW_GAME_DELAY_TIME); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		canStartNewGame = true;
		
	}
	
	public void start() {
		
		canStartNewGame = false; //once game is started, don't start a new game until game over
		firstGame = false;
		gameRunning = true;
		runThread = new Thread(this);
		runThread.start();

	}
	
	public void resetGame() {
		
		worm = new Worm(boardLength/2, boardHeight/2, Direction.DOWN, wrapsAround, boardLength, boardHeight);
		
		random = new Random();
		
		//initialise apple
		int appleX, appleY;
		do {
			//loop if apple collides with snake
			appleX = random.nextInt(boardLength);
			appleY = random.nextInt(boardHeight);
		} while (appleX == boardLength/2 && appleY == boardHeight/2);
		apple = new Apple(appleX, appleY);
		
		if(!firstGame) {
			scoreBoard.resetScore();
		}
	}
}
