package damas.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import damas.listeners.StatusBarListener;

@SuppressWarnings("serial")
public class StatusBar extends JPanel{
	private JLabel label;
	private StatusBarListener statusBarListener = new StatusBarListener(){
		@Override
		public void textChanged(String s) {
			label.setText(s);
		}
	};
	
	public StatusBar(){
		this.setPreferredSize(new Dimension(Integer.MAX_VALUE,16));
		this.setLayout(new BorderLayout());
		final JLabel outLabel = new JLabel(); outLabel.setHorizontalAlignment(JLabel.CENTER); outLabel.setText("OUT"); 
		this.add(outLabel, BorderLayout.WEST);
		this.label = outLabel;
	}
	public StatusBarListener getStatusBarListener() {
		return this.statusBarListener;
	}
}
