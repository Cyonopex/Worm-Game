package wormgame.game;

import java.awt.Component;

import java.util.Random;

import wormgame.Direction;
import wormgame.domain.Apple;
import wormgame.domain.Worm;

public class WormGame implements Runnable {
	
	private int boardLength, boardHeight;
	private Random random;
	private Worm worm;
	private Apple apple;
	private boolean gameRunning;
	private Thread runThread;
	private boolean endlessScreen;
	private Component drawingboard;
	
	public WormGame(int boardLength, int boardHeight, boolean wrapsAround) {
		this.boardLength = boardLength;
		this.boardHeight = boardHeight;
		this.endlessScreen = wrapsAround;
		
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
	
	private void makeNewApple() {
		int appleX, appleY;
		do {
			appleX = random.nextInt(boardLength);
			appleY = random.nextInt(boardHeight);
			this.apple = new Apple(appleX, appleY);
		} while (worm.runsInto(apple));

	}
	
	public void updateBoard() {

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
			}
			
			if(worm.runsIntoItself() ) {
				gameRunning = false;
			}
			
			if(!endlessScreen && worm.leavesBoundary()) {
				gameRunning = false;
			}


			drawingboard.repaint();
		}
	}
	
	public void setComponent(Component component) {
		this.drawingboard = component;
	}
	
	public Component getComponent() {
		return drawingboard;
	}
	
	public void start() {


		runThread = new Thread(this);
		runThread.start();

	}
	
	public boolean isGameOver() {
		return !gameRunning;
	}
	
	public void resetGame() {
		
		gameRunning = true;
		
		worm = new Worm(boardLength/2, boardHeight/2, Direction.DOWN, endlessScreen, boardLength, boardHeight);
		
		random = new Random();
		
		//initialise apple
		int appleX, appleY;
		do {
			//loop if apple collides with snake
			appleX = random.nextInt(boardLength);
			appleY = random.nextInt(boardHeight);
		} while (appleX == boardLength/2 && appleY == boardHeight/2);
		apple = new Apple(appleX, appleY);
	}
}
