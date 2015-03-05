package damas.heuristic;

import damas.Board;
import damas.Square;
import damas.player.PlayerColour;
import damas.rules.Rules;

public class WeightCountTieBreaker implements Heuristic{

	@Override
	public double evaluate(Board board, PlayerColour player, Rules rules) {
		double count = 0;
		int pieceCount = 0;
		int DAMA_VALUE = 12;
		if (rules.toString().equals("Reglas españolas"))
			DAMA_VALUE = 15;
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				switch (board.getSquare(i, j)) {
					case BLACK:
						count += ((i+1) * 1.0 / board.MAX_DIM) * 2 + 7;
						pieceCount++;
						break;
					case BLACK_D:
						count += DAMA_VALUE;
						pieceCount++;
						break;
					case WHITE:
						count -= ((board.MAX_DIM - i) * 1.0 / board.MAX_DIM) * 2 + 7;
						pieceCount++;
						break;
					case WHITE_D:
						count -= DAMA_VALUE;
						pieceCount++;
						break;
					default:
						break;
				}
				if (j == 0 || j == board.MAX_DIM - 1){
					if (board.getSquare(i, j).equals(Square.WHITE) || board.getSquare(i, j).equals(Square.WHITE_D)){
						count -= 0.05;
					}else if (!board.getSquare(i, j).equals(Square.EMPTY)){
						count += 0.05;
					}
				}
			}
		}
		count *= (player == PlayerColour.BLACK ? 1 : -1);
		if (count > 9){
			count -= pieceCount * 1.0 / 5;
		}
		if (pieceCount < 5 && count > 0){
			count += 10;
		}
		count *= 100;
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				switch (board.getSquare(i, j)) {
					case BLACK_D:
						if (player == PlayerColour.BLACK && count > 0) {
							count -= getDistance(i, j, PlayerColour.BLACK, board);
						}
						if (player == PlayerColour.WHITE && count < 0) {
							count += getDistance(i, j, PlayerColour.BLACK, board);
						}
						break;
					case WHITE_D:
						if (player == PlayerColour.WHITE && count > 0) {
							count -= getDistance(i, j, PlayerColour.WHITE, board);
						}
						if (player == PlayerColour.BLACK && count < 0) {
							count += getDistance(i, j, PlayerColour.WHITE, board);
						}
						break;
					default:
						break;
				}
			}
		}
		long rng = System.currentTimeMillis() % 100;
		if (count > 0)
			return count * 100 + rng;
		else return count * 100 - rng;
	}
	
	private int getDistance(int x, int y, PlayerColour player, Board board){
		int count = 0;
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				switch (board.getSquare(i, j)) {
					case BLACK:
						if (player == PlayerColour.WHITE){
							count += Math.abs(i-x) + Math.abs(j-y);
						}
						break;
					case BLACK_D:
						if (player == PlayerColour.WHITE){
							count += Math.abs(i-x) + Math.abs(j-y);
						}
						break;
					case WHITE:
						if (player == PlayerColour.BLACK){
							count += Math.abs(i-x) + Math.abs(j-y);
						}
						break;
					case WHITE_D:
						if (player == PlayerColour.BLACK){
							count += Math.abs(i-x) + Math.abs(j-y);
						}
						break;
					default:
						break;
				}
			}
		}
		return count;
	}
	
	@Override
	public String toString(){
		return "Weight count advanced with tiebreaker";
	}
}
