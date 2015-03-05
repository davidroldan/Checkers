package damas.algorithm;

import java.util.List;

import damas.Board;
import damas.Settings;
import damas.moves.Move;
import damas.player.PlayerColour;
import damas.util.GameOverDetect;

public abstract class Algorithm {
	
	public abstract Move algorithm(Settings settings, Board board, PlayerColour currentPlayer);
	public abstract String toString();
	
	protected double evaluateModel(Settings settings, Board board) {
		return settings.getHeuristic().evaluate(board, settings.getPlayerColour(), settings.getRules());
	}
	
	protected List<Move> getPossibleMoves(Settings settings, Board board, PlayerColour currentPlayer, GameOverDetect gameOverDetect) {
		return settings.getRules().getPossibleMoves(board, currentPlayer, gameOverDetect);
	}
}
