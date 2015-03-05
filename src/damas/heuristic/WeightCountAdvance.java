package damas.heuristic;

import damas.Board;
import damas.player.PlayerColour;
import damas.rules.Rules;

public class WeightCountAdvance implements Heuristic{

	@Override
	public double evaluate(Board board, PlayerColour player, Rules rules) {
		int count = 0;
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				switch (board.getSquare(i, j)) {
					case BLACK:
						count += ((i+1) / board.MAX_DIM) * 3 + 5;
						break;
					case BLACK_D:
						count += 10;
						break;
					case WHITE:
						count -= ((board.MAX_DIM - i) / board.MAX_DIM) * 3 + 5;
						break;
					case WHITE_D:
						count -= 10;
						break;
					default:
						break;
				}
			}
		}
		count *= (player == PlayerColour.BLACK ? 1 : -1);
		
		long rng = System.currentTimeMillis() % 100;
		return count * 100 + rng;
	}
	
	@Override
	public String toString(){
		return "Weight count advanced (Number, type, and position)";
	}
}
