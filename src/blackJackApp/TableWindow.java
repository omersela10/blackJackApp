package blackJackApp;

import java.awt.BorderLayout;

import javax.swing.*;
public class TableWindow extends JFrame {
	
	// Data Members
	private Table theTable;
	private Player thePlayingPlayer;
	private String tableIcon = "resources/table/blackJackTable.png";
	
	// Constructor
	public TableWindow(Table newTable) {
		this.theTable = newTable;
		
		setTitle("Blackjack Table");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// Create a label to display the table icon
		ImageIcon icon = new ImageIcon(tableIcon);
		JLabel tableLabel = new JLabel(icon);
		add(tableLabel, BorderLayout.CENTER);
		
		pack(); // Adjust the window size based on the components' preferred sizes
		setVisible(true); // Make the window visible
	}
}
