package damas.player;

public enum PlayerColour {
	WHITE,
	BLACK;

	public PlayerColour opposite() {
		return this == WHITE ? BLACK : WHITE;
	}
	
	public String toString(){
		if (this == WHITE){
			return "White";
		}
		else return "Black";
	}
}
