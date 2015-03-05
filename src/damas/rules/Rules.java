package damas.rules;

import java.util.List;

import damas.Board;
import damas.moves.Move;
import damas.player.PlayerColour;
import damas.util.GameOverDetect;

public interface Rules {
	public PlayerColour moveFirst();
	public List<Move> getPossibleMoves(Board board, PlayerColour player, GameOverDetect gameOverDetect);
}
