package damas;


import damas.listeners.BoardListener;
import damas.moves.Move;
import damas.player.PlayerColour;

public class CPUWorker extends Thread{
	
	private Board board;
	private Settings settings;
	private PlayerColour player;
	private BoardListener boardListener;
	private Move move;

	public CPUWorker(Board board, Settings settings, PlayerColour player, BoardListener boardListener) {
		this.board = board;
		this.settings = settings;
		this.player = player;
		this.boardListener = boardListener;
	}
	
	private volatile Thread blinker;
	
	public void start() {
        blinker = new Thread(this);
        blinker.start();
    }
	
	public void kill() {
	    blinker = null;
	}

	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		move = settings.getAlgorithm().algorithm(settings, board.clone(), player);
		//System.out.println(move.getValue());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		if (blinker == thisThread){
			board.doMove(move);
			boardListener.onMoveDone(move);
		}
	}

}
