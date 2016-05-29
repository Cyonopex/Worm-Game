package wormgame.domain;

public class Piece {
	
	private int x, y;
	
	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean runsInto(Piece piece) {
		return (piece.getX() == this.getX() && piece.getY() == this.getY());
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
