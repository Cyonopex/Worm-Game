package wormgame.domain;

import java.util.ArrayList;
import java.util.List;

import wormgame.Direction;

public class Worm {
	
	private Direction direction;
	private List<Piece> listOfPieces;
	private boolean growOnNextTurn;
	private KeypressTracker tracker;
	private boolean endlessScreen;
	int boardLength, boardHeight;
	
	public Worm(int originalX, int originalY, Direction originalDirection, boolean wrapsAround, int boardLength, int boardHeight) {
		this.direction = originalDirection;
		tracker = new KeypressTracker();
		endlessScreen = wrapsAround;
		this.boardLength = boardLength;
		this.boardHeight = boardHeight;
		
		listOfPieces = new ArrayList<Piece>();
		
		listOfPieces.add(new Piece(originalX, originalY));
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setKeypress(Direction dir) {
		tracker.setPressed(dir);
	}
	
	public void setKeyRel(Direction dir) {
		tracker.setReleased(dir);
	}
	
	public void setDirection() {
		
		//get current state of arrow keys from keypress tracker
		boolean left = tracker.getPressed(Direction.LEFT);
		boolean right = tracker.getPressed(Direction.RIGHT);
		boolean up = tracker.getPressed(Direction.UP);
		boolean down = tracker.getPressed(Direction.DOWN);
		
		Direction finalDirection = direction;
		
		//case where only one button is currently down
		if(left && !right && !up && !down) {
			finalDirection = Direction.LEFT;
		} else if(!left && right && !up && !down) {
			finalDirection = Direction.RIGHT;
		} else if(!left && !right && up && !down) {
			finalDirection = Direction.UP;
		} else if(!left && !right && !up && down) {
			finalDirection = Direction.DOWN;
		}
		//case where two adjacent buttons are down
		else if(left && !right && up && !down) { // L U
			if(direction == Direction.LEFT) finalDirection = Direction.UP;
			if(direction == Direction.UP) finalDirection = Direction.LEFT;
			if(direction == Direction.RIGHT) finalDirection = Direction.UP;
			if(direction == Direction.DOWN) finalDirection = Direction.LEFT;
		} else if(!left && right && up && !down) { // U R
			if(direction == Direction.RIGHT) finalDirection = Direction.UP;
			if(direction == Direction.UP) finalDirection = Direction.RIGHT;
			if(direction == Direction.LEFT) finalDirection = Direction.UP;
			if(direction == Direction.DOWN) finalDirection = Direction.RIGHT;
		} else if(!left && right && !up && down) { // R D
			if(direction == Direction.RIGHT) finalDirection = Direction.DOWN;
			if(direction == Direction.DOWN) finalDirection = Direction.RIGHT;
			if(direction == Direction.LEFT) finalDirection = Direction.DOWN;
			if(direction == Direction.UP) finalDirection = Direction.RIGHT;
		} else if(left && !right && !up && down) { // D L
			if(direction == Direction.LEFT) finalDirection = Direction.DOWN;
			if(direction == Direction.DOWN) finalDirection = Direction.LEFT;
			if(direction == Direction.RIGHT) finalDirection = Direction.DOWN;
			if(direction == Direction.UP) finalDirection = Direction.LEFT;
		}
		
		//don't allow user to move snake back into itself
		if(!finalDirection.isOpposite(direction)) direction = finalDirection;
	}
	
	public void forceSetDirection(Direction dir) {
		this.direction = dir;
	}
	
	public int getLength() {
		return listOfPieces.size();
	}
	
	public List<Piece> getPieces() {
		return listOfPieces;
	}
	
	public void move() {
		
		setDirection();
		tracker.release();

		//get coordinates for head of worm
		Piece head = listOfPieces.get(listOfPieces.size()-1);
		
		if (endlessScreen) {
			//add piece at head of worm
			switch (direction) {
			case LEFT:
				if(head.getX() == 0) { //if the head is at the left boundary
					listOfPieces.add(new Piece(boardLength - 1, head.getY()));
				} else {
					listOfPieces.add(new Piece(head.getX() - 1, head.getY()));
				}
				break;
			case RIGHT:
				if(head.getX() == boardLength-1) { //head at right boundary
					listOfPieces.add(new Piece(0, head.getY()));
				} else {
					listOfPieces.add(new Piece(head.getX() + 1, head.getY()));
				}
				break;
			case UP:
				if(head.getY() == 0) { //head at upper boundary
					listOfPieces.add(new Piece(head.getX(), boardHeight - 1));
				} else {
					listOfPieces.add(new Piece(head.getX(), head.getY() - 1));
				}
				break;
			case DOWN:
				if (head.getY() == boardHeight - 1) {
					listOfPieces.add(new Piece(head.getX(), 0));
				} else {
					listOfPieces.add(new Piece(head.getX(), head.getY() + 1));
				}
				break;
			}
		} else {
			switch (direction) {
			case LEFT:
				listOfPieces.add(new Piece(head.getX() - 1, head.getY()));
				break;
			case RIGHT:
				listOfPieces.add(new Piece(head.getX() + 1, head.getY()));
				break;
			case UP:
				listOfPieces.add(new Piece(head.getX(), head.getY() - 1));
				break;
			case DOWN:
				listOfPieces.add(new Piece(head.getX(), head.getY() + 1));
				break;
			}
		}
		//if worm is smaller than 3, don't remove last piece (or if one of below conditions occur)
		if(this.getLength() <= 3 || growOnNextTurn || runsIntoItself() || (!endlessScreen && leavesBoundary() )) {
			growOnNextTurn = false;
		} else {
			listOfPieces.remove(0);
		}
		
	}
	
	public void grow() {
		growOnNextTurn = true;
	}
	
	public boolean runsInto(Piece piece) {
		
		for(Piece bodyPiece : listOfPieces) {
			if(bodyPiece.getX() == piece.getX() && bodyPiece.getY() == piece.getY()) return true;
		}
		
		return false;
	}
	
	public boolean runsIntoItself() {
		
		Piece head = listOfPieces.get(listOfPieces.size()-1);
		int headXCoordinate = head.getX();
		int headYCoordinate = head.getY();
		
		for(int i = 0; i<listOfPieces.size()-2; i++) {
			Piece bodyPiece = this.listOfPieces.get(i);
			if(bodyPiece.getX() == headXCoordinate && bodyPiece.getY() == headYCoordinate) return true;
		}
		
		return false;
	}
	
	public boolean leavesBoundary() {
		Piece head = listOfPieces.get(listOfPieces.size()-1);
		int headXCoordinate = head.getX();
		int headYCoordinate = head.getY();
		
		return headXCoordinate < 0 || headXCoordinate >= boardLength || headYCoordinate < 0 || headYCoordinate >= boardHeight;
	}
}
