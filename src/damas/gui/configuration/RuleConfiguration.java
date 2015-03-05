package damas.gui.configuration;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import damas.Settings;
import damas.rules.*;

@SuppressWarnings("serial")
public class RuleConfiguration extends JPanel{
	
	public JComboBox<Rules> jComboRules;
	public JComboBox<Integer> jComboBoardSize;
	
	public RuleConfiguration(Settings oldSettings, int boardSize){
		this.setBorder(new TitledBorder("Reglas"));
		this.setLayout(new GridLayout(2,2));
		
		JLabel label = new JLabel("Reglas");
		this.add(label);
		
		jComboRules = new JComboBox<Rules>();
		jComboRules.addItem(new EnglishRules());
		jComboRules.addItem(new SpanishRules());
		this.add(jComboRules);
		jComboRules.setEnabled(true);
		for (int i = 0; i < jComboRules.getItemCount(); i++){
			if (jComboRules.getItemAt(i).toString().equals(oldSettings.getRules().toString())){
				jComboRules.setSelectedItem(jComboRules.getItemAt(i));
			}
		}
		
		label = new JLabel("Tamaño del tablero");
		this.add(label);
		
		jComboBoardSize = new JComboBox<Integer>();
		for (int i = 4; i <= 60; i+=2){
			jComboBoardSize.addItem(i);
		}
		this.add(jComboBoardSize);
		jComboBoardSize.setEnabled(true);
		jComboBoardSize.setSelectedItem(boardSize);
	}
}
