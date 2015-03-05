package damas.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import damas.Board;
import damas.CPUWorker;
import damas.GameEngine;
import damas.Square;
import damas.listeners.*;
import damas.moves.Move;
import damas.player.PlayerColour;
import damas.player.PlayerMode;
import damas.util.GameOverDetect;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	private Image board_w_img = new ImageIcon("./res/whiteBoard.png").getImage();
	private Image board_b_img = new ImageIcon("./res/blackBoard.png").getImage();
	private Image white_img = new ImageIcon("./res/white.png").getImage();
	private Image black_img = new ImageIcon("./res/black.png").getImage();
	private Image whiteD_img = new ImageIcon("./res/whiteDama.png").getImage();
	private Image blackD_img = new ImageIcon("./res/blackDama.png").getImage();
	private Image red_img = new ImageIcon("./res/redSelected.png").getImage();
	private Image green_img = new ImageIcon("./res/greenSelected.png").getImage();
	private Image blue_img = new ImageIcon("./res/blueSelected.png").getImage();
	private Image yellow_b_img = new ImageIcon("./res/yellowBorder.png").getImage();
	private Image red_b_img = new ImageIcon("./res/redBorder.png").getImage();
	
	private Board board;
	private List<Move> possibleMoves;
	private GameEngine gameEngine;
	

	private CPUWorker cpuWorker;
	
	
	private boolean listening;
	private boolean isDragging = false;
	private boolean lastPieceMoved = false;
	private Move lastMove;
	private int draggedX = 0, draggedY = 0;
	private int X1 = 0, Y1 = 0, X2 = 1, Y2 = 4;
	
	private boolean animation = false;
	private int animatedX = 0, animatedY = 0;
	private int targetX = 0, targetY = 0;
	
	private StatusBarListener statusBarListener;
	private DataDisplayListener dataDisplayListener;
	private BoardListener boardListener = new BoardListener(){
		@Override
		public void onMoveDone(Move last_Move) {
			lastPieceMoved = false;
			lastMove = last_Move;
			if (!isHumanPlaying()) animatedMove();
			lastPieceMoved = true;
			gameEngine.nextTurn();
			dataDisplayListener.onMoveDone(lastMove);
			refreshPanel();
			updateMoveList(gameEngine.getCurrentTurnSettings().getRules().getPossibleMoves(board, gameEngine.getCurrentTurn(), new GameOverDetect(false)));
			if (noMovesLeft()){
				dataDisplayListener.onFinish();
				listening = false;
				return;
			}
			listening = gameEngine.getCurrentTurnSettings().getPlayerMode().equals(PlayerMode.HUMAN);
			if (!listening){
				statusBarListener.textChanged(gameEngine.getCurrentTurn().toString() + " thinking..   (" + gameEngine.getCurrentTurnSettings().getHeuristic().evaluate(board, gameEngine.getCurrentTurn(), gameEngine.getCurrentTurnSettings().getRules()) + ")");
				playCPU();
			}
			else{
				statusBarListener.textChanged(gameEngine.getCurrentTurn().toString() + "'s turn!   (" + gameEngine.getCurrentTurnSettings().getHeuristic().evaluate(board, gameEngine.getCurrentTurn(), gameEngine.getCurrentTurnSettings().getRules()) + ")");
			}
			refreshPanel();
		}

		@Override
		public void onStart() {
			lastPieceMoved = false;
			dataDisplayListener.onStart();
			this.onNewGame();
			refreshPanel();
			updateMoveList(gameEngine.getCurrentTurnSettings().getRules().getPossibleMoves(board, gameEngine.getCurrentTurn(), new GameOverDetect(false)));
			listening = gameEngine.getCurrentTurnSettings().getPlayerMode().equals(PlayerMode.HUMAN);
			if (!listening){
				statusBarListener.textChanged(gameEngine.getCurrentTurn().toString() + " thinking..");
				playCPU();
			}
			else{
				statusBarListener.textChanged(gameEngine.getCurrentTurn().toString() + "'s turn!");
			}
		}
		
		@Override
		public void onNewGame() {
			if (cpuWorker != null)
				cpuWorker.kill();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
	};
	
	
	public BoardPanel(GameEngine gEngine){
		this.board = new Board();
		this.gameEngine = gEngine;
		this.gameEngine.setBoardListener(boardListener);
		this.gameEngine.setBoard(board);
		
		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (!isHumanPlaying()) return;
				if (board.getSquare(X1, Y1).getOwner() == gameEngine.getCurrentTurn()){
					isDragging = true;
					lastPieceMoved = false;
					X2 = X1;
					Y2 = Y1;
					draggedX = e.getY();
					draggedY = e.getX();
				}
				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!isHumanPlaying()) return;
				isDragging = false;
				if (possibleMovement()){
					boardListener.onMoveDone(doMovement());
				}
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (!isHumanPlaying()) return;
				Y2 = arg0.getX() * board.MAX_DIM / getWidth();
				X2 = arg0.getY() * board.MAX_DIM / getHeight();
				draggedX = arg0.getY();
				draggedY = arg0.getX();
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				if (!isHumanPlaying()) return;
				Y1 = arg0.getX() * board.MAX_DIM / getWidth();
				X1 = arg0.getY() * board.MAX_DIM / getHeight();
				repaint();
			}
			
		});
	}
	protected void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {}
	}
	protected void doAnimatedMove(){
		while (targetX != animatedX || targetY != animatedY){
			if (Math.abs(targetX - animatedX) <= 5) animatedX = targetX;
			if (Math.abs(targetY - animatedY) <= 5) animatedY = targetY;
			else{
				if (targetX > animatedX) animatedX+=5;
				if (targetY > animatedY) animatedY+=5;
				if (targetX < animatedX) animatedX-=5;
				if (targetY < animatedY) animatedY-=5;
			}
			refreshPanel();
			this.sleep(5);
		}
		refreshPanel();
		this.sleep(150);
		refreshPanel();
	}
	protected void animatedMove() {
		animation = true;
		refreshPanel();
		animatedX = lastMove.fromX * getHeight() / board.MAX_DIM;
		animatedY = lastMove.fromY * getWidth() / board.MAX_DIM;
		targetX = lastMove.toX * getHeight() / board.MAX_DIM;
		targetY = lastMove.toY * getWidth() / board.MAX_DIM;
		if (!lastMove.eat){
			doAnimatedMove();
		}
		else{
			for (int i = 0; i < lastMove.eaten.size(); i++){
				targetX = lastMove.eaten.get(i).x * getHeight() / board.MAX_DIM;
				targetY = lastMove.eaten.get(i).y * getWidth() / board.MAX_DIM;
				if (targetX > animatedX) targetX = (lastMove.eaten.get(i).x + 1) * getHeight() / board.MAX_DIM;
				else targetX = (lastMove.eaten.get(i).x - 1) * getHeight() / board.MAX_DIM;
				if (targetY > animatedY) targetY = (lastMove.eaten.get(i).y + 1) * getWidth() / board.MAX_DIM;
				else targetY = (lastMove.eaten.get(i).y - 1) * getWidth() / board.MAX_DIM;
				doAnimatedMove();
			}
		}
		animation = false;
	}
	protected void refreshPanel() {
		this.revalidate();
		this.repaint();
		
	}
	protected boolean noMovesLeft() {
		return this.possibleMoves.size() == 0;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				if (((i+j) & 1) == 0){
					g.drawImage(board_w_img, j * getWidth() / board.MAX_DIM, i * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
				}
				else{
					g.drawImage(board_b_img, j * getWidth() / board.MAX_DIM, i * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
				}
					
			}
		}
		
		for (int i = 0; i < board.MAX_DIM; i++) {
			for (int j = 0; j < board.MAX_DIM; j++) {
				g.drawImage(getSquareImage(i,j), j * getWidth() / board.MAX_DIM, i * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
			}
		}
		
		if (isHumanPlaying()){
			if (isDragging){
				Image img = red_img;
				if (possibleMovement())
					img = green_img;
				g.drawImage(board_b_img, Y1 * getWidth() / board.MAX_DIM, X1 * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
				g.drawImage(getSquareImage(X1,Y1), draggedY - getWidth()/2 / board.MAX_DIM, draggedX - getHeight()/2 / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
				g.drawImage(img, Y2 * getWidth() / board.MAX_DIM, X2 * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
				g.drawImage(blue_img, Y1 * getWidth() / board.MAX_DIM, X1 * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
			}
			else{
				Image img = red_img;
				if (board.getSquare(X1, Y1).getOwner() == gameEngine.getCurrentTurn())
					img = green_img;
				g.drawImage(img, Y1 * getWidth() / board.MAX_DIM, X1 * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
			}
		}
		if (lastPieceMoved){
			g.drawImage(yellow_b_img, lastMove.toY * getWidth() / board.MAX_DIM, lastMove.toX * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
			g.drawImage(yellow_b_img, lastMove.fromY * getWidth() / board.MAX_DIM, lastMove.fromX * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
			if (lastMove.eat){
				for (int i = 0; i < lastMove.eaten.size(); i++){
					g.drawImage(red_b_img, lastMove.eaten.get(i).y * getWidth() / board.MAX_DIM, lastMove.eaten.get(i).x * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
				}
			}
		}
		if (animation){
			g.drawImage(board_b_img, lastMove.toY * getWidth() / board.MAX_DIM, lastMove.toX * getHeight() / board.MAX_DIM, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
			g.drawImage(getSquareImage(this.lastMove.initialSquare), animatedY, animatedX, getWidth() / board.MAX_DIM, getHeight() / board.MAX_DIM, this);
		}
	}
	Image getSquareImage(Square square){
		switch(square){
		case BLACK:
			return black_img;
		case WHITE:
			return white_img;
		case BLACK_D:
			return blackD_img;
		case WHITE_D:
			return whiteD_img;
		default:
			return null;
	}
	}
	
	Image getSquareImage(int i, int j){
		switch(board.getSquare(i,j)){
		case BLACK:
			return black_img;
		case WHITE:
			return white_img;
		case BLACK_D:
			return blackD_img;
		case WHITE_D:
			return whiteD_img;
		default:
			return null;
	}
	}
	
	public boolean possibleMovement(){
		for (int i = 0; i < possibleMoves.size(); i++){
			if (possibleMoves.get(i).fromX == X1 && possibleMoves.get(i).toX == X2 && possibleMoves.get(i).fromY == Y1 && possibleMoves.get(i).toY == Y2){
				return true;
			}
		}
		return false;
	}
	
	public Move doMovement(){
		for (int i = 0; i < possibleMoves.size(); i++){
			if (possibleMoves.get(i).fromX == X1 && possibleMoves.get(i).toX == X2 && possibleMoves.get(i).fromY == Y1 && possibleMoves.get(i).toY == Y2){
				board.doMove(possibleMoves.get(i));
				return possibleMoves.get(i);
			}
		}
		return null;
	}
	
	public void playCPU(){
		cpuWorker = new CPUWorker(board, gameEngine.getCurrentTurnSettings(), gameEngine.getCurrentTurn(), boardListener);
		refreshPanel();
		cpuWorker.start();
		refreshPanel();
	}
	
	public boolean isHumanPlaying(){
		return listening;
	}
	
	public void updateMoveList(List<Move> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}
	public void setStatusBarListener(StatusBarListener statusBarListener) {
		this.statusBarListener = statusBarListener;
	}
	public void setDataDisplayListener(DataDisplayListener dataDisplayListener) {
		this.dataDisplayListener = dataDisplayListener;
	}
}
