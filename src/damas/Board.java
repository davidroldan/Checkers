package damas;

import damas.moves.Move;
import damas.player.PlayerColour;


public class Board {
	
	public int MAX_DIM;
	private int nonEatenStreak;
	
	private Square square[][];
	
	/**
	 * Constructor with custom size
	 * @param dim
	 */
	public Board(int dim){
		MAX_DIM = dim;
		// if incorrect dimension, set to 8
		if (MAX_DIM < 0 || MAX_DIM % 2 == 1){
			MAX_DIM = 8;
		}
		nonEatenStreak = 0;
		initBoard();
	}
	
	/**
	 * Default constructor, default size = 8
	 */
	public Board(){
		MAX_DIM = 8;
		nonEatenStreak = 0;
		initBoard();
	}
	
	/**
	 * Private constructor for cloning purposes
	 */
	private Board(int dim, int streak){
		MAX_DIM = dim;
		nonEatenStreak = streak;
		initBoard();
	}
	
	public void initBoard(){
		Square sqr[][] = new Square[MAX_DIM][MAX_DIM];
		for (int i = 0; i < MAX_DIM; i++){
			for (int j = 0; j < MAX_DIM; j++){
				if ((i+j) % 2 == 0){
					sqr[i][j] = Square.EMPTY;
				}
				else if (i < MAX_DIM / 2 - 1){
					sqr[i][j] = Square.BLACK;
				}
				else if (i > MAX_DIM / 2){
					sqr[i][j] = Square.WHITE;
				}
				else sqr[i][j] = Square.EMPTY;
			}
		}
		nonEatenStreak = 0;
		square = sqr;
	}
	
	public Square getSquare(int x, int y){
		if (x >= 0 && y >= 0 && x < MAX_DIM && y < MAX_DIM)
			return square[x][y];
		else
			return null;
	}
	
	public void setSquare(int x, int y, Square square){
		this.square[x][y] = square;
	}
	
	public int getNonEatenStreak(){
		return this.nonEatenStreak;
	}
	
	public void resize(int dim){
		MAX_DIM = dim;
		// if incorrect dimension, set to 8
		if (MAX_DIM < 0 || MAX_DIM % 2 == 1){
			MAX_DIM = 8;
		}
		initBoard();
	}

	public void doMove(Move move) {
		if (move.toX != move.fromX || move.toY != move.fromY){
			square[move.toX][move.toY] = square[move.fromX][move.fromY];
			square[move.fromX][move.fromY] = Square.EMPTY;
		}
		if (move.eat){
			for (int i = 0; i < move.eaten.size(); i++){
				square[move.eaten.get(i).x][move.eaten.get(i).y] = Square.EMPTY;
			}
			nonEatenStreak = 0;
		}
		else{
			nonEatenStreak++;
		}
		if (square[move.toX][move.toY].getOwner() == PlayerColour.WHITE && move.toX == 0){
			square[move.toX][move.toY] = square[move.toX][move.toY].convertDama();
		}
		if (square[move.toX][move.toY].getOwner() == PlayerColour.BLACK && move.toX == this.MAX_DIM - 1){
			square[move.toX][move.toY] = square[move.toX][move.toY].convertDama();
		}
	}
	
	public void undoMove(Move move){
		if (move.toX != move.fromX || move.toY != move.fromY){
			square[move.toX][move.toY] = Square.EMPTY;
		}
		if (move.eat){
			for (int i = 0; i < move.eaten.size(); i++){
				square[move.eaten.get(i).x][move.eaten.get(i).y] = move.eaten.get(i).square;
			}
		}
		nonEatenStreak = move.previousNonEatenStreak;
		square[move.fromX][move.fromY] = move.initialSquare;
	}
	
	public Board clone(){
		Board newboard = new Board(MAX_DIM, this.nonEatenStreak);
		newboard.square = new Square[MAX_DIM][MAX_DIM];
		for (int i = 0; i < MAX_DIM; i++){
			for (int j = 0; j < MAX_DIM; j++){
				newboard.square[i][j] = this.square[i][j];
			}
		}
		return newboard;	
	}	
}
