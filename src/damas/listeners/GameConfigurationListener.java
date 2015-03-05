package damas.listeners;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import damas.GameEngine;
import damas.Settings;
import damas.algorithm.Algorithm;
import damas.gui.configuration.MainConfiguration;
import damas.gui.configuration.RuleConfiguration;
import damas.heuristic.Heuristic;
import damas.player.PlayerMode;
import damas.rules.Rules;

public class GameConfigurationListener implements ActionListener{
	private Settings oldSettings1;
	private Settings oldSettings2;
	private GameEngine gameEngine;
	private MainConfiguration mainConfiguration1, mainConfiguration2;
	private RuleConfiguration ruleConfiguration;
	
	public GameConfigurationListener(Settings settings1, Settings settings2, GameEngine gEngine){
		this.oldSettings1 = settings1;
		this.oldSettings2 = settings2;
		this.gameEngine = gEngine;
	}
	
	@SuppressWarnings("serial")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame = new JFrame("Configuración");
		frame.setLayout(new BorderLayout());
		frame.add(new ConfigurationPanel(), BorderLayout.NORTH);
		ruleConfiguration = new RuleConfiguration(oldSettings1, gameEngine.getBoard().MAX_DIM);
		frame.add(ruleConfiguration, BorderLayout.CENTER);
		frame.add(new JPanel(){
			{
				this.setLayout(new GridLayout(1,2));
				this.add(new AcceptButton());
				this.add(new CancelButton());
			}
		},
		BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(false);
	}
	private JFrame frame;
	
	@SuppressWarnings("serial")
	private class ConfigurationPanel extends JSplitPane{
		ConfigurationPanel(){
			mainConfiguration1 = new MainConfiguration(1, oldSettings1);
			mainConfiguration2 = new MainConfiguration(2, oldSettings2);
			this.setLeftComponent(mainConfiguration1);
			this.setRightComponent(mainConfiguration2);
		}
	}
	
	
	
	@SuppressWarnings("serial")
	private class AcceptButton extends JButton{
		public AcceptButton(){
			super("Aceptar");
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateConfiguration();
					frame.dispose();
					gameEngine.newGame();
				}
			});
		}
	}
	
	@SuppressWarnings("serial")
	private class CancelButton extends JButton{
		public CancelButton(){
			super("Cancelar");
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
		}
	}
	
	public void updateConfiguration(){
		this.oldSettings1.changeSettings((PlayerMode) mainConfiguration1.jComboMode.getSelectedItem(),
				(Rules) ruleConfiguration.jComboRules.getSelectedItem(),
				(Heuristic) mainConfiguration1.jComboHeuristic.getSelectedItem(),
				(Algorithm) mainConfiguration1.jComboAlgorithm.getSelectedItem(),
				(int) mainConfiguration1.jComboDepth.getSelectedItem());
		this.oldSettings2.changeSettings((PlayerMode) mainConfiguration2.jComboMode.getSelectedItem(),
				(Rules) ruleConfiguration.jComboRules.getSelectedItem(),
				(Heuristic) mainConfiguration2.jComboHeuristic.getSelectedItem(),
				(Algorithm) mainConfiguration2.jComboAlgorithm.getSelectedItem(),
				(int) mainConfiguration2.jComboDepth.getSelectedItem());
		this.gameEngine.getBoard().resize((int) ruleConfiguration.jComboBoardSize.getSelectedItem());
	}
}
