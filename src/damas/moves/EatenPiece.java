package damas.moves;

import damas.Square;

public class EatenPiece {
	public EatenPiece(int x, int y, Square square) {
		this.x = x;
		this.y = y;
		this.square = square;
	}
	public int x;
	public int y;
	public Square square;
}
