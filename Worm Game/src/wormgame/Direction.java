package wormgame;

public enum Direction {
	LEFT(-1), 
	RIGHT(1), 
	UP(-2), 
	DOWN(2);
	
	private int value;
	
	private Direction(int value) {
		this.value = value;
	}
	
	public boolean isOpposite(Direction direction) {
		return this.value + direction.value == 0;
	}
}
