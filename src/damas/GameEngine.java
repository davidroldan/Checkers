package damas;

import damas.algorithm.*;
import damas.heuristic.*;
import damas.listeners.BoardListener;
import damas.player.PlayerColour;
import damas.player.PlayerMode;
import damas.rules.EnglishRules;

public class GameEngine {

	private Board board;
	private Settings settings1;
	private Settings settings2;
	private PlayerColour currentTurn;
	private BoardListener boardListener;
	
	public GameEngine(){
		setDefaultSettings();
	}
	
	public void newGame(){
		boardListener.onNewGame();
		board.initBoard();
		currentTurn = settings1.getRules().moveFirst();
		boardListener.onStart();
	}
	
	public PlayerColour getCurrentTurn(){
		return this.currentTurn;
	}
	
	public void nextTurn(){
		this.currentTurn = this.currentTurn.opposite();
	}
	
	public void setDefaultSettings(){
		//settings1 = new Settings(PlayerMode.HUMAN, PlayerColour.WHITE, new InternationalRules());
		settings1 = new Settings(PlayerMode.HUMAN, PlayerColour.WHITE, new EnglishRules(), new WeightCount(), new Minimax() , 4);
		settings2 = new Settings(PlayerMode.CPU, PlayerColour.BLACK, new EnglishRules(), new WeightCountTieBreaker(), new AlphaBeta() , 4);
	}
	public Settings getCurrentTurnSettings(){
		if (currentTurn.equals(PlayerColour.WHITE)){
			return settings1;
		}
		else return settings2;
	}
	
	public Board getBoard(){
		return this.board;
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	public Settings whitePlayerSettings(){
		return settings1;
	}
	
	public Settings blackPlayerSettings(){
		return settings2;
	}
	
	public void setBoardListener(BoardListener boardListener) {
		this.boardListener = boardListener;		
	}

}
