package damas.gui.configuration;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import damas.Settings;
import damas.algorithm.Algorithm;
import damas.algorithm.AlphaBeta;
import damas.algorithm.Minimax;
import damas.heuristic.*;
import damas.player.PlayerMode;

@SuppressWarnings("serial")
public class MainConfiguration extends JPanel{
	
	public JComboBox<PlayerMode> jComboMode;
	public JComboBox<Algorithm> jComboAlgorithm;
	public JComboBox<Integer> jComboDepth;
	public JComboBox<Heuristic> jComboHeuristic;
	
	public MainConfiguration(int player, Settings oldSettings){
		String s = "White";
		if (player == 2){
			s = "Black";
		}
		this.setBorder(new TitledBorder("Configuration Player " + s));
		this.setLayout(new GridLayout(4,2));
		
		JLabel label = new JLabel("Mode");
		this.add(label);
		
		jComboMode = new JComboBox<PlayerMode>();
		jComboMode.addItem(PlayerMode.HUMAN);
		jComboMode.addItem(PlayerMode.CPU);
		this.add(jComboMode);
		jComboMode.setEnabled(true);
		jComboMode.setSelectedItem(oldSettings.getPlayerMode());
		
		label = new JLabel("Algorithm");
		this.add(label);
		
		jComboAlgorithm = new JComboBox<Algorithm>();
		jComboAlgorithm.addItem(new AlphaBeta());
		jComboAlgorithm.addItem(new Minimax());
		this.add(jComboAlgorithm);
		jComboAlgorithm.setEnabled(jComboMode.getSelectedItem().equals(PlayerMode.CPU));
		if (jComboMode.getSelectedItem().equals(PlayerMode.CPU)){
			for (int i = 0; i < jComboAlgorithm.getItemCount(); i++){
				if (jComboAlgorithm.getItemAt(i).toString().equals(oldSettings.getAlgorithm().toString())){
					jComboAlgorithm.setSelectedItem(jComboAlgorithm.getItemAt(i));
				}
			}
		}
		
		label = new JLabel("Depth");
		this.add(label);
		
		jComboDepth = new JComboBox<Integer>();
		for (int i = 1; i <= 8; i++){
			jComboDepth.addItem(i);
		}
		this.add(jComboDepth);
		jComboDepth.setEnabled(jComboMode.getSelectedItem().equals(PlayerMode.CPU));
		if (jComboMode.getSelectedItem().equals(PlayerMode.CPU)){
			jComboDepth.setSelectedItem(oldSettings.getDepth());
		}
		
		label = new JLabel("Heuristic");
		this.add(label);
		
		jComboHeuristic = new JComboBox<Heuristic>();
		jComboHeuristic.addItem(new WeightCountTieBreaker());
		jComboHeuristic.addItem(new WeightCountAdvance());
		jComboHeuristic.addItem(new WeightCount());
		jComboHeuristic.addItem(new MenCount());
		this.add(jComboHeuristic);
		jComboHeuristic.setEnabled(jComboMode.getSelectedItem().equals(PlayerMode.CPU));
		if (jComboMode.getSelectedItem().equals(PlayerMode.CPU)){
			for (int i = 0; i < jComboHeuristic.getItemCount(); i++){
				if (jComboHeuristic.getItemAt(i).toString().equals(oldSettings.getHeuristic().toString())){
					jComboHeuristic.setSelectedItem(jComboHeuristic.getItemAt(i));
				}
			}
		}
		
		jComboMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboMode.getSelectedItem().equals(PlayerMode.CPU)){
					jComboAlgorithm.setEnabled(true);
					jComboDepth.setEnabled(true);
					jComboHeuristic.setEnabled(true);
				}
				else{
					jComboAlgorithm.setEnabled(false);
					jComboDepth.setEnabled(false);
					jComboHeuristic.setEnabled(false);
				}
			}
		});
	}
}
