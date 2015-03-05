package damas.algorithm;

import java.util.List;

import damas.Board;
import damas.Settings;
import damas.moves.Move;
import damas.player.PlayerColour;
import damas.util.GameOverDetect;

public class AlphaBeta extends Algorithm{

	@Override
	public Move algorithm(Settings settings, Board board, PlayerColour currentPlayer) {
		return alphaBeta(settings, board, currentPlayer, 0, - Double.MAX_VALUE, Double.MAX_VALUE);
	}
	
	Move alphaBeta(Settings settings, Board board, PlayerColour currentPlayer, int depth, double alpha, double beta)
		{
		    Move myBest = new  Move();
		    Move reply;

		    if (settings.getDepth() * 2 == depth) { //La profundidad de settings es por cada dos jugadas, 'depth' es por cada jugada
		        final Move move = new Move();
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
		    
		    if (settings.getPlayerColour() == currentPlayer) {
		        myBest.setValue(alpha);
		    } else {
		        myBest.setValue(beta);
		    }

		    for (Move move : possibleMoves){
		    	board.doMove(move);
		        reply = alphaBeta(settings, board, currentPlayer.opposite(), depth+1, alpha, beta);
		        board.undoMove(move);
		        if (settings.getPlayerColour() == currentPlayer && reply.getValue() > myBest.getValue()) {
		            myBest = move;
		            myBest.setValue(reply.getValue());
		            alpha = reply.getValue();

		        } else if (settings.getPlayerColour() != currentPlayer && reply.getValue() < myBest.getValue()) {
		            myBest = move;
		            myBest.setValue(reply.getValue());
		            beta = reply.getValue();

		        }
		        if (alpha >= beta) {
		            return myBest;
		        }
		    }

		    return myBest;
		}

	@Override
	public String toString() {
		return "Alpha Beta";
	}

}
