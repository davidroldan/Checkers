package damas.heuristic;

import damas.Board;
import damas.player.PlayerColour;
import damas.rules.Rules;

public class MenCount implements Heuristic{

	@Override
	public double evaluate(Board board, PlayerColour player, Rules rules) {
		int count = 0;
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				switch (board.getSquare(i, j)) {
					case BLACK:
						count ++;
						break;
					case BLACK_D:
						count ++;
						break;
					case WHITE:
						count --;
						break;
					case WHITE_D:
						count --;
						break;
					default:
						break;
				}
			}
		}
		return count * (player == PlayerColour.BLACK ? 1:-1);
	}
	
	@Override
	public String toString(){
		return "Men count";
	}
	
}
