package damas.listeners;
import damas.moves.Move;

public interface BoardListener {
	public void onMoveDone(Move lastMove);
	public void onStart();
	public void onNewGame();
}
