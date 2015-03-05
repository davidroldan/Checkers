package damas.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import damas.GameEngine;
import damas.listeners.GameConfigurationListener;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private GameEngine gameEngine;

	public MenuBar(GameEngine gEngine) {
		this.gameEngine = gEngine;
		JMenu menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_J);
		JMenuItem menuItem = new JMenuItem("New Game");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				gameEngine.newGame();
			}			
		});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Configuration");
		menuItem.addActionListener(new GameConfigurationListener(gEngine.whitePlayerSettings(), gEngine.blackPlayerSettings(), gEngine));
		menu.add(menuItem);
		
		this.add(menu);

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_A);
		menuItem = new JMenuItem("About..");
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = "CHECKERS AI \n David Roldán Santos";
				JOptionPane.showMessageDialog(MenuBar.this, str , "Checkers", JOptionPane.INFORMATION_MESSAGE);
			}			
		});
		menu.add(menuItem);
		this.add(menu);	
	}
}
