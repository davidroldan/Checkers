package damas.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import damas.listeners.*;
import damas.moves.Move;


public class DataDisplay extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private JTextArea jText;
	private int moves = 0;
	private DataDisplayListener dataDisplayListener = new DataDisplayListener(){
		
		@Override
		public void onMoveDone(Move move) {
			moves++;
			if (moves == 1){
				jText.setText(jText.getText() + "        " + String.valueOf(moves/2+1) + ".    " + move);
			}else if (moves % 2 == 1){
				jText.setText(jText.getText() + "\n        " + String.valueOf(moves/2+1) + ".    " + move);
			}
			else{
				jText.setText(jText.getText() + "        " + move);
			}
		}
		
		@Override
		public void onStart() {
			jText.setText("");
			moves = 0;
		}
		
		@Override
		public void onFinish() {
			jText.setText(jText.getText() + "\n" + "Game Over");
		}
	};
	public DataDisplay() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(new JTextArea() {
			private static final long serialVersionUID = 1L;
			{
				this.setEditable(false);
				jText = this;
			}
			
			
		},JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		this.setBorder(new TitledBorder("Partida"));
		this.setPreferredSize(new Dimension(200, Integer.MAX_VALUE));
	}
	
	/**
	 * @return the text area this component has
	 */
	public JTextArea getJTextArea() {
		return jText;
	}
	
	public DataDisplayListener getDataDisplayListener(){
		return this.dataDisplayListener;
	}
}
