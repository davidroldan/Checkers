package damas.heuristic;

import damas.Board;
import damas.player.PlayerColour;
import damas.rules.Rules;

public class WeightCount implements Heuristic{
	
	final static int NORMAL = 8;
	final static int DAMA = 12;

	@Override
	public double evaluate(Board board, PlayerColour player, Rules rules) {
		int count = 0;
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				switch (board.getSquare(i, j)) {
					case BLACK:
						count += NORMAL;
						break;
					case BLACK_D:
						count += DAMA;
						break;
					case WHITE:
						count -= NORMAL;
						break;
					case WHITE_D:
						count -= DAMA;
						break;
					default:
						break;
				}
			}
		}
		count *= (player == PlayerColour.BLACK ? 1 : -1);
		return count;
		//long rng = System.currentTimeMillis() % 100;
		//return count * 100 + rng;
	}
	
	@Override
	public String toString(){
		return "Weight Count (number and type)";
	}
}
