package damas;

import damas.algorithm.Algorithm;
import damas.heuristic.Heuristic;
import damas.player.PlayerColour;
import damas.player.PlayerMode;
import damas.rules.Rules;

public class Settings {
	private PlayerColour playerColour;
	private PlayerMode playerMode;
	private Heuristic heuristic;
	private Algorithm algorithm;
	private Rules rules;
	private int depth;
	
	public Settings(PlayerMode playerMode, PlayerColour playerColour, Rules rules){
		this.playerMode = playerMode;
		this.playerColour = playerColour;
		this.rules = rules;
		this.heuristic = null;
		this.algorithm = null;
		this.depth = 0;
	}
	
	public Settings(PlayerMode playerMode, PlayerColour playerColour, Rules rules, Heuristic heuristic, Algorithm algorithm, int depth){
		this.playerMode = playerMode;
		this.playerColour = playerColour;
		this.heuristic = heuristic;
		this.algorithm = algorithm;
		this.depth = depth;
		this.rules = rules;
	}
	
	
	/**
	 * Change settings
	 * @param playerMode
	 * @param heuristic
	 * @param algorithm
	 * @param depth
	 * @param rules
	 */
	public void changeSettings(PlayerMode playerMode, Rules rules, Heuristic heuristic, Algorithm algorithm, int depth){
		this.playerMode = playerMode;
		this.heuristic = heuristic;
		this.algorithm = algorithm;
		this.depth = depth;
		this.rules = rules;
	}
	
	
	public PlayerColour getPlayerColour() {
		return playerColour;
	}
	public void setPlayerColour(PlayerColour player) {
		this.playerColour = player;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}
	public Algorithm getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	public Heuristic getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(Heuristic heuristic) {
		this.heuristic = heuristic;
	}
	public PlayerMode getPlayerMode() {
		return playerMode;
	}
	public void setPlayerMode(PlayerMode playerMode) {
		this.playerMode = playerMode;
	}
	public Rules getRules() {
		return rules;
	}

	public void setRules(Rules rules) {
		this.rules = rules;
	}
}
