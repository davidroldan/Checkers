package damas.moves;

import java.util.ArrayList;

import damas.Square;
import damas.util.NumToLetter;

public class Move {
	
	public int fromX;
	public int fromY;
	public int toX;
	public int toY;
	public int previousNonEatenStreak;
	public boolean eat = false;
	public int numEatenDamas = 0;
	public Square initialSquare;
	public ArrayList<EatenPiece> eaten = new ArrayList<EatenPiece>();
	
	private int boardSize;
	
	/**
	 * Default constructor
	 */
	public Move(){
	}
	
	
	/**
	 * Constructor with the 4 coordinates
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 */
	public Move(int fromX, int fromY, int toX, int toY, Square initialSquare, int prevStreak, int boardSize){
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.initialSquare = initialSquare;
		this.previousNonEatenStreak = prevStreak;
		this.boardSize = boardSize;
	}
	
	@SuppressWarnings("unchecked")
	public Move clone(){
		Move move2 = new Move(fromX, fromY, toX, toY, initialSquare, previousNonEatenStreak, boardSize);
		move2.eat = this.eat;
		move2.numEatenDamas = this.numEatenDamas;
		move2.eaten = (ArrayList<EatenPiece>) this.eaten.clone();
		return move2;
	}
	
	//Atributos y Funciones para minimax y alfabeta
	
	private double value = Double.NEGATIVE_INFINITY;
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	//String
	
	public String toString(){
		String a = NumToLetter.getLetter(fromY + 1) + String.valueOf(boardSize - fromX);
		if (eat){
			a += " x ";
		}
		else{
			a += " - ";
		}
		a += NumToLetter.getLetter(toY + 1) + String.valueOf(boardSize - toX);
		return a;
	}
}
