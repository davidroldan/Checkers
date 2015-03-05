package damas.rules;

import java.util.ArrayList;
import java.util.List;

import damas.Board;
import damas.moves.EatenPiece;
import damas.moves.Move;
import damas.player.PlayerColour;
import damas.util.GameOverDetect;

public class EnglishRules implements Rules{
	
	public String toString(){
		return "Reglas inglesas";
	}
	
	@Override
	public PlayerColour moveFirst() {
		return PlayerColour.WHITE;
	}
	
	@Override
	public List<Move> getPossibleMoves(Board board, PlayerColour player, GameOverDetect gameOverDetect) {
		gameOverDetect.gameOver = false;
		ArrayList<Move> list = new ArrayList<Move>();
		// Draw if nonEatenStreak >= 80
		if (board.getNonEatenStreak() >= 80){
			return list;
		}
		// Calculate possible moves
		for (int i = 0; i < board.MAX_DIM; i++){
			for (int j = 0; j < board.MAX_DIM; j++){
				if (board.getSquare(i, j).getOwner() == player){
					if (board.getSquare(i, j).isDama()){
						checkMovesDama(list, board, player, i, j);
					}
					else{
						checkMovesNormal(list, board, player, i, j);
					}
				}
			}
		}
		// No moves = Player loses
		if (list.size() == 0){
			gameOverDetect.gameOver = true;
		}
		
		//printList(list,"before"); //(testing)
		reduceList(list);
		//printList(list,"after"); //(testing)
		return list;
	}
	private void reduceList(ArrayList<Move> list) {
		int maxEaten = 0;
		for (Move move : list){
			if (move.eat){
				if (move.eaten.size() > maxEaten){
					maxEaten = move.eaten.size();
				}
			}
		}
		if (maxEaten == 0) return;
		
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).eaten.size() < maxEaten){
				list.remove(i);
				i--;
			}
		}
		
	}
	/**
	 * For testing purposes
	 * @param list
	 */
	@SuppressWarnings("unused")
	private void printList(ArrayList<Move> list, String s){
		System.out.println("Move list " + s + " reducing:");
		for (int i = 0; i < list.size(); i++){
			System.out.println(list.get(i).fromX + " " + list.get(i).fromY + " " + list.get(i).toX + " " + list.get(i).toY + ", numEaten = " + list.get(i).eaten.size());
		}
	}
	
	private void checkMovesNormal(ArrayList<Move> list, Board board, PlayerColour player, int x, int y){
		if (player.equals(PlayerColour.WHITE)){
			if (x > 0 && y > 0 && board.getSquare(x-1, y-1).isEmpty()){
				list.add(new Move(x, y, x-1, y-1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
			if (x > 0 && y < board.MAX_DIM - 1 && board.getSquare(x-1, y+1).isEmpty()){
				list.add(new Move(x, y, x-1, y+1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
		}
		else{ // BLACK
			if (x < board.MAX_DIM - 1 && y > 0 && board.getSquare(x+1, y-1).isEmpty()){
				list.add(new Move(x, y, x+1, y-1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
			if (x < board.MAX_DIM - 1 && y < board.MAX_DIM - 1 && board.getSquare(x+1, y+1).isEmpty()){
				list.add(new Move(x, y, x+1, y+1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
		}
		checkMovesNormalEat(list, board, player, x, y);
	}
	
	private void checkMovesNormalEat(ArrayList<Move> list, Board board, PlayerColour player, int x, int y){
		if (player.equals(PlayerColour.WHITE)){
			if (x > 1 && y > 1 && board.getSquare(x-1, y-1).getOwner() == player.opposite() && board.getSquare(x-2, y-2).isEmpty()){
				Move move = new Move(x, y, x-2, y-2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
				move.eat = true;
				move.eaten.add(new EatenPiece(x-1,y-1, board.getSquare(x-1, y-1)));
				if (board.getSquare(x-1, y-1).isDama())
					move.numEatenDamas++;
				list.add(move);
				checkMovesNormalEatMultiple(list, board, player, move);
			}
			if (x > 1 && y < board.MAX_DIM - 2 && board.getSquare(x-1, y+1).getOwner() == player.opposite() && board.getSquare(x-2, y+2).isEmpty()){
				Move move = new Move(x, y, x-2, y+2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
				move.eat = true;
				move.eaten.add(new EatenPiece(x-1,y+1, board.getSquare(x-1, y+1)));
				if (board.getSquare(x-1, y+1).isDama())
					move.numEatenDamas++;
				list.add(move);
				checkMovesNormalEatMultiple(list, board, player, move);
			}
		}
		else{ // BLACK
			if (x < board.MAX_DIM - 2 && y > 1 && board.getSquare(x+1, y-1).getOwner() == player.opposite() && board.getSquare(x+2, y-2).isEmpty()){
				Move move = new Move(x, y, x+2, y-2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
				move.eat = true;
				move.eaten.add(new EatenPiece(x+1,y-1, board.getSquare(x+1, y-1)));
				if (board.getSquare(x+1, y-1).isDama())
					move.numEatenDamas++;
				list.add(move);
				checkMovesNormalEatMultiple(list, board, player, move);
			}
			if (x < board.MAX_DIM - 2 && y < board.MAX_DIM - 2 && board.getSquare(x+1, y+1).getOwner() == player.opposite() && board.getSquare(x+2, y+2).isEmpty()){
				Move move = new Move(x, y, x+2, y+2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
				move.eat = true;
				move.eaten.add(new EatenPiece(x+1,y+1, board.getSquare(x+1, y+1)));
				if (board.getSquare(x+1, y+1).isDama())
					move.numEatenDamas++;
				list.add(move);
				checkMovesNormalEatMultiple(list, board, player, move);
			}
		}
	}
	
	private void checkMovesNormalEatMultiple(ArrayList<Move> list, Board board, PlayerColour player, Move move){
		if (player.equals(PlayerColour.WHITE)){
			if (move.toX > 1 && move.toY > 1 && board.getSquare(move.toX - 1, move.toY-1).getOwner() == player.opposite() && board.getSquare(move.toX-2, move.toY-2).isEmpty()){
				Move newMove = move.clone();
				newMove.toX = newMove.toX - 2;
				newMove.toY = newMove.toY - 2;
				newMove.eaten.add(new EatenPiece(move.toX - 1,move.toY - 1, board.getSquare(move.toX - 1,move.toY - 1)));
				if (board.getSquare(move.toX-1, move.toY - 1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesNormalEatMultiple(list, board, player, newMove);
			}
			if (move.toX > 1 && move.toY < board.MAX_DIM - 2 && board.getSquare(move.toX-1, move.toY+1).getOwner() == player.opposite() && board.getSquare(move.toX-2, move.toY+2).isEmpty()){
				Move newMove = move.clone();
				newMove.toX = newMove.toX - 2;
				newMove.toY = newMove.toY + 2;
				newMove.eaten.add(new EatenPiece(move.toX-1,move.toY+1, board.getSquare(move.toX - 1,move.toY + 1)));
				if (board.getSquare(move.toX-1, move.toY+1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesNormalEatMultiple(list, board, player, newMove);
			}
		}
		else{ // BLACK
			if (move.toX < board.MAX_DIM - 2 && move.toY > 1 && board.getSquare(move.toX+1, move.toY-1).getOwner() == player.opposite() && board.getSquare(move.toX+2, move.toY-2).isEmpty()){
				Move newMove = move.clone();
				newMove.toX = newMove.toX + 2;
				newMove.toY = newMove.toY - 2;
				newMove.eaten.add(new EatenPiece(move.toX+1,move.toY-1, board.getSquare(move.toX + 1,move.toY - 1)));
				if (board.getSquare(move.toX+1, move.toY-1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesNormalEatMultiple(list, board, player, newMove);
			}
			if (move.toX < board.MAX_DIM - 2 && move.toY < board.MAX_DIM - 2 && board.getSquare(move.toX+1, move.toY+1).getOwner() == player.opposite() && board.getSquare(move.toX+2, move.toY+2).isEmpty()){
				Move newMove = move.clone();
				newMove.toX = newMove.toX + 2;
				newMove.toY = newMove.toY + 2;
				newMove.eaten.add(new EatenPiece(move.toX+1,move.toY+1, board.getSquare(move.toX + 1,move.toY + 1)));
				if (board.getSquare(move.toX+1, move.toY+1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesNormalEatMultiple(list, board, player, newMove);
			}
		}
	}
	
	private void checkMovesDama(ArrayList<Move> list, Board board, PlayerColour player, int x, int y){
			if (x > 0 && y > 0 && board.getSquare(x-1, y-1).isEmpty()){
				list.add(new Move(x, y, x-1, y-1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
			if (x > 0 && y < board.MAX_DIM - 1 && board.getSquare(x-1, y+1).isEmpty()){
				list.add(new Move(x, y, x-1, y+1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
			if (x < board.MAX_DIM - 1 && y > 0 && board.getSquare(x+1, y-1).isEmpty()){
				list.add(new Move(x, y, x+1, y-1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
			if (x < board.MAX_DIM - 1 && y < board.MAX_DIM - 1 && board.getSquare(x+1, y+1).isEmpty()){
				list.add(new Move(x, y, x+1, y+1, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM));
			}
		checkMovesDamaEat(list, board, player, x, y);
	}

private void checkMovesDamaEat(ArrayList<Move> list, Board board, PlayerColour player, int x, int y){
		if (x > 1 && y > 1 && board.getSquare(x-1, y-1).getOwner() == player.opposite() && board.getSquare(x-2, y-2).isEmpty()){
			Move move = new Move(x, y, x-2, y-2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
			move.eat = true;
			move.eaten.add(new EatenPiece(x-1,y-1, board.getSquare(x-1, y-1)));
			if (board.getSquare(x-1, y-1).isDama())
				move.numEatenDamas++;
			list.add(move);
			checkMovesDamaEatMultiple(list, board, player, move);
		}
		if (x > 1 && y < board.MAX_DIM - 2 && board.getSquare(x-1, y+1).getOwner() == player.opposite() && board.getSquare(x-2, y+2).isEmpty()){
			Move move = new Move(x, y, x-2, y+2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
			move.eat = true;
			move.eaten.add(new EatenPiece(x-1,y+1, board.getSquare(x-1, y+1)));
			if (board.getSquare(x-1, y+1).isDama())
				move.numEatenDamas++;
			list.add(move);
			checkMovesDamaEatMultiple(list, board, player, move);
		}
		if (x < board.MAX_DIM - 2 && y > 1 && board.getSquare(x+1, y-1).getOwner() == player.opposite() && board.getSquare(x+2, y-2).isEmpty()){
			Move move = new Move(x, y, x+2, y-2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
			move.eat = true;
			move.eaten.add(new EatenPiece(x+1,y-1, board.getSquare(x+1, y-1)));
			if (board.getSquare(x+1, y-1).isDama())
				move.numEatenDamas++;
			list.add(move);
			checkMovesDamaEatMultiple(list, board, player, move);
		}
		if (x < board.MAX_DIM - 2 && y < board.MAX_DIM - 2 && board.getSquare(x+1, y+1).getOwner() == player.opposite() && board.getSquare(x+2, y+2).isEmpty()){
			Move move = new Move(x, y, x+2, y+2, board.getSquare(x, y), board.getNonEatenStreak(), board.MAX_DIM);
			move.eat = true;
			move.eaten.add(new EatenPiece(x+1,y+1, board.getSquare(x+1, y+1)));
			if (board.getSquare(x+1, y+1).isDama())
				move.numEatenDamas++;
			list.add(move);
			checkMovesDamaEatMultiple(list, board, player, move);
		}
}

	private void checkMovesDamaEatMultiple(ArrayList<Move> list, Board board, PlayerColour player, Move move){
		if (move.toX > 1 && move.toY > 1 && board.getSquare(move.toX - 1, move.toY-1).getOwner() == player.opposite() && (board.getSquare(move.toX-2, move.toY-2).isEmpty() || (move.toX-2 == move.fromX && move.toY-2 == move.fromY))){
			if (!alreadyEaten(move.toX - 1,move.toY - 1, move.eaten)){
				Move newMove = move.clone();
				newMove.toX = newMove.toX - 2;
				newMove.toY = newMove.toY - 2;
				newMove.eaten.add(new EatenPiece(move.toX - 1,move.toY - 1, board.getSquare(move.toX - 1,move.toY - 1)));
				if (board.getSquare(move.toX-1, move.toY - 1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesDamaEatMultiple(list, board, player, newMove);
			}
		}
		if (move.toX > 1 && move.toY < board.MAX_DIM - 2 && board.getSquare(move.toX-1, move.toY+1).getOwner() == player.opposite() && (board.getSquare(move.toX-2, move.toY+2).isEmpty() || (move.toX-2 == move.fromX && move.toY+2 == move.fromY))){
			if (!alreadyEaten(move.toX - 1,move.toY + 1, move.eaten)){
				Move newMove = move.clone();
				newMove.toX = newMove.toX - 2;
				newMove.toY = newMove.toY + 2;
				newMove.eaten.add(new EatenPiece(move.toX-1,move.toY+1, board.getSquare(move.toX - 1,move.toY + 1)));
				if (board.getSquare(move.toX-1, move.toY+1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesDamaEatMultiple(list, board, player, newMove);
			}
		}
		if (move.toX < board.MAX_DIM - 2 && move.toY > 1 && board.getSquare(move.toX+1, move.toY-1).getOwner() == player.opposite() && (board.getSquare(move.toX+2, move.toY-2).isEmpty() || (move.toX+2 == move.fromX && move.toY-2 == move.fromY))){
			if (!alreadyEaten(move.toX + 1,move.toY - 1, move.eaten)){	
				Move newMove = move.clone();
				newMove.toX = newMove.toX + 2;
				newMove.toY = newMove.toY - 2;
				newMove.eaten.add(new EatenPiece(move.toX+1,move.toY-1, board.getSquare(move.toX + 1,move.toY - 1)));
				if (board.getSquare(move.toX+1, move.toY-1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesDamaEatMultiple(list, board, player, newMove);
			}
		}
		if (move.toX < board.MAX_DIM - 2 && move.toY < board.MAX_DIM - 2 && board.getSquare(move.toX+1, move.toY+1).getOwner() == player.opposite() && (board.getSquare(move.toX+2, move.toY+2).isEmpty() || (move.toX+2 == move.fromX && move.toY+2 == move.fromY))){
			if (!alreadyEaten(move.toX + 1,move.toY + 1, move.eaten)){	
				Move newMove = move.clone();
				newMove.toX = newMove.toX + 2;
				newMove.toY = newMove.toY + 2;
				newMove.eaten.add(new EatenPiece(move.toX+1,move.toY+1, board.getSquare(move.toX + 1,move.toY + 1)));
				if (board.getSquare(move.toX+1, move.toY+1).isDama())
					newMove.numEatenDamas++;
				list.add(newMove);
				checkMovesDamaEatMultiple(list, board, player, newMove);
			}
		}
	}

	private boolean alreadyEaten(int i, int j, ArrayList<EatenPiece> list) {
		for (int k = 0; k < list.size(); k++){
			if (list.get(k).x == i && list.get(k).y == j){
				return true;
			}
		}
		return false;
	}
}
	
