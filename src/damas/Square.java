package damas;

import damas.player.PlayerColour;

public enum Square {
	
	EMPTY (null),
	WHITE (PlayerColour.WHITE),
	BLACK (PlayerColour.BLACK),
	WHITE_D (PlayerColour.WHITE),
	BLACK_D (PlayerColour.BLACK);
	
	private final PlayerColour owner;
	
	private Square(PlayerColour owner){
		this.owner = owner;
	}

	public Square convertDama() {
		if (this == WHITE)
			return WHITE_D;
		else if (this == BLACK)
			return BLACK_D;
		return this;
	}

	public Square convertNormal() {
		if (this == WHITE_D)
			return WHITE;
		else if (this == BLACK_D)
			return BLACK;
		return this;
	}
	
	public boolean isEmpty(){
		return this == EMPTY;
	}
	
	public PlayerColour getOwner(){
		return this.owner;
	}
	
	public boolean isDama(){
		return (this == BLACK_D  || this == WHITE_D);
	}
}

