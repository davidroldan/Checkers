package damas.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import damas.GameEngine;


public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private int width = 800;
	private int height = 600;
	private GameEngine gameEngine;
	private BoardPanel boardPanel;
	private StatusBar statusBar;
	private DataDisplay dataDisplay;
	
	public MainWindow (){
		super("Checkers");
		this.gameEngine = new GameEngine();
		this.boardPanel = new BoardPanel(gameEngine);
		this.dataDisplay = new DataDisplay();
		this.statusBar = new StatusBar();
		this.boardPanel.setStatusBarListener(this.statusBar.getStatusBarListener());
		this.boardPanel.setDataDisplayListener(this.dataDisplay.getDataDisplayListener());
		this.add(new MainPanel(), BorderLayout.CENTER);
		this.add(statusBar, BorderLayout.SOUTH);
		this.setJMenuBar(new MenuBar(gameEngine));
		this.setParams();	
		this.gameEngine.newGame();
	}
	
	/**
	 * Sets various parameters to default values
	 */
	private void setParams() {
		this.setSize(this.width, this.height);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(500,300));
	}
	
	@SuppressWarnings("serial")
	private class MainPanel extends JPanel{
		MainPanel(){
			this.setLayout(new BorderLayout());
			this.add(boardPanel, BorderLayout.CENTER);
			this.add(dataDisplay, BorderLayout.EAST);
		}
	}
}