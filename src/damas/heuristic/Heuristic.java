package damas.heuristic;

import damas.Board;
import damas.player.PlayerColour;
import damas.rules.Rules;

public interface Heuristic {
	double evaluate(Board board, PlayerColour player, Rules rules);
	String toString();
}
