package wormgame.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import wormgame.Direction;
import wormgame.domain.Worm;
import wormgame.game.WormGame;

public class KeyboardListener implements KeyListener {
	
	WormGame game;
	Worm worm;
	
	public KeyboardListener(WormGame game) {
		this.game = game;
		this.worm = game.getWorm();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_I) {
			game.toggleWrapAroundMode();
			return;
		}
		
		if(game.isGameOver() && game.canStartNewGame()) {
			
			if (!game.firstGame()) { //game has already been initialised if it's first game
				game.resetGame();
			}
			this.worm = game.getWorm();
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				worm.forceSetDirection(Direction.UP);
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				worm.forceSetDirection(Direction.DOWN);
			} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				worm.forceSetDirection(Direction.LEFT);
			} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				worm.forceSetDirection(Direction.RIGHT);
			}
			game.start();

		} else {
			
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				worm.setKeypress(Direction.UP);
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				worm.setKeypress(Direction.DOWN);
			} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				worm.setKeypress(Direction.LEFT);
			} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				worm.setKeypress(Direction.RIGHT);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			worm.setKeyRel(Direction.UP);
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			worm.setKeyRel(Direction.DOWN);
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			worm.setKeyRel(Direction.LEFT);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			worm.setKeyRel(Direction.RIGHT);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
