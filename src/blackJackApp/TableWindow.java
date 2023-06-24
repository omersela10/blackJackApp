package blackJackApp;

import java.awt.*;
import javax.swing.*;

public class TableWindow extends JFrame {
	
	// Data Members
	private Table theTable;
	private Player thePlayingPlayer;
	private static String tableIcon = "resources/table/blackJackTable.png";
	
	// Constructor
	public TableWindow(Table newTable, Player newPlayer) {
		
		this.theTable = newTable;
		this.thePlayingPlayer = newPlayer;
		
		 setTitle("Blackjack Table");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());

	        // Create a label to display the table icon
	        ImageIcon icon = new ImageIcon(tableIcon);
	        JLabel tableLabel = new JLabel(icon);
	        add(tableLabel, BorderLayout.CENTER);
	        // Create a label for the player's name and total money
	        JLabel playerInfoLabel = new JLabel("Name: " + thePlayingPlayer.getPlayerName() + " , Money: " + thePlayingPlayer.getTotalMoney());
	        add(playerInfoLabel, BorderLayout.NORTH);
	        
	        // Create buttons for surrender, stand, hit, split, double
	        JButton surrenderButton = new JButton("Surrender");
	        JButton standButton = new JButton("Stand");
	        JButton hitButton = new JButton("Hit");
	        JButton splitButton = new JButton("Split");
	        JButton doubleButton = new JButton("Double");

	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(surrenderButton);
	        buttonPanel.add(standButton);
	        buttonPanel.add(hitButton);
	        buttonPanel.add(splitButton);
	        buttonPanel.add(doubleButton);

	        add(buttonPanel, BorderLayout.SOUTH);

	        // Set the preferred size of the window
	        Dimension preferredSize = new Dimension(1200, 800);
	        setPreferredSize(preferredSize);
	        pack(); // Adjust the window size based on the components' preferred sizes
	        setVisible(true); // Make the window visible
	}
}
