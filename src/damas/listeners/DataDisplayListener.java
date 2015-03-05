package damas.listeners;
import damas.moves.Move;

public interface DataDisplayListener {
	public void onMoveDone(Move move);
	public void onStart();
	public void onFinish();
}
