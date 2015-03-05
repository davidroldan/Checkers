package damas.algorithm;

import java.util.List;

import damas.Board;
import damas.Settings;
import damas.moves.Move;
import damas.player.PlayerColour;
import damas.util.GameOverDetect;

public class Minimax extends Algorithm{

	@Override
	public Move algorithm(Settings settings, Board board, PlayerColour currentPlayer) {
		return minimax(settings, board, currentPlayer, 0);
	}
	
	Move minimax(Settings settings, Board board, PlayerColour currentPlayer, int depth) {
		if(settings.getDepth() * 2 == depth){ //La profundidad de settings es por cada dos jugadas, 'depth' es por cada jugada
			Move move = new Move();
			move.setValue(evaluateModel(settings, board));
			return move;
		}
		GameOverDetect GODetect = new GameOverDetect(false);
		List<Move> possibleMoves = getPossibleMoves(settings, board, currentPlayer, GODetect);
		if(possibleMoves.isEmpty()){
		    final Move move = new Move();
		    	if (!GODetect.gameOver) move.setValue(0);
		    	else move.setValue(settings.getPlayerColour() == currentPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE);
				return move;
			}
		boolean isAssigned = false;
		double value = 0;
		Move selectedMove = null;
		for (Move move : possibleMoves) {
			board.doMove(move);
			Move minimax = minimax(settings, board, currentPlayer.opposite(), depth+1);
			if(!isAssigned){
				isAssigned = true;
				value = minimax.getValue();
				selectedMove = move;
			}else{
				if(settings.getPlayerColour() == currentPlayer){
					if(minimax.getValue() > value){
						selectedMove = move;
						value = minimax.getValue();
					}
				}else{
					if(minimax.getValue() < value){
						selectedMove = move;
						value = minimax.getValue();
					}
				}
			}
			board.undoMove(move);
		}
		selectedMove.setValue(value);
		return selectedMove;
	}

	@Override
	public String toString() {
		return "Minimax";
	}

}
