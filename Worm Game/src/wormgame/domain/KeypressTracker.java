package wormgame.domain;

import wormgame.Direction;

public class KeypressTracker {
	private boolean leftPressed, rightPressed, upPressed, downPressed;
	private boolean leftRel, rightRel, upRel, downRel;
	
	public KeypressTracker() {
		resetPressed();
	}
	
	public void resetPressed() {
		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;
		leftRel = false;
		rightRel = false;
		upRel = false;
		downRel = false;
	}

	public void setPressed(Direction dir) {
		switch (dir) {
		case LEFT:
			leftPressed = true;
			break;
		case RIGHT:
			rightPressed = true;
			break;
		case UP:
			upPressed = true;
			break;
		case DOWN:
			downPressed = true;
			break;
		default:
			break;
		}
	}
	
	public void setReleased(Direction dir) {

		switch (dir) {
		case LEFT:
			leftRel = true;
			break;
		case RIGHT:
			rightRel = true;
			break;
		case UP:
			upRel = true;
			break;
		case DOWN:
			downRel = true;
			break;
		default:
			break;
		}
		
	}
	
	public void release() {
		if(leftRel) leftPressed = false;
		if(rightRel) rightPressed = false;
		if(upRel) upPressed = false;
		if(downRel) downPressed = false;
		
		leftRel = false;
		rightRel = false;
		upRel = false;
		downRel = false;
	}
	
	public boolean getPressed(Direction dir) {
		switch(dir) {
		case LEFT:
			return leftPressed;
		case RIGHT:
			return rightPressed;
		case UP:
			return upPressed;
		case DOWN:
			return downPressed;
		default:
			return false;
		}
	}
	
}
