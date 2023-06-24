package blackJackApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TableWindow extends JFrame {
	
	// Data Members
	private Table theTable;
	private Player thePlayingPlayer;
	private boolean isSeat = false;
	private static final String tableIcon = "resources/table/background.png";
	private static final String dealerIcon =  "resources/table/Dealer.png";
	// Pane
	private JLayeredPane theLayeredPane;
	//PlayerComponent
	private PlayerComponent thePlayerComponent;
	// Buttons
	private JButton standButton;
	private JButton hitButton;
	private JButton surrenderButton;
	private JButton doubleButton;
	private JButton splitButton;
	private JButton seatUpButton;
	private JButton betButton;
	private JLabel  playerInfoLabel;
	private JLabel textJLabel;
	
	// Constructor
	public TableWindow(Table newTable, Player newPlayer)   {
		
		this.theTable = newTable;
		this.thePlayingPlayer = newPlayer;
		
		try {
		     initializeComponents();
		}
		catch(Exception e) {
			
		}

    }
	
	public void initializeComponents() throws IOException {
		
		setTitle("Blackjack Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create a layered pane to hold the components
        this.theLayeredPane = new JLayeredPane();
        this.theLayeredPane.setPreferredSize(new Dimension(1200, 800));
        add(this.theLayeredPane, BorderLayout.CENTER);
        
        // Load the background image
        Image img = ImageIO.read(new File(tableIcon));
        
        // Create a custom panel to draw the background image
        JPanel backgroundPanel = new JPanel() {
        	@Override
        	protected void paintComponent(Graphics g) {
        		super.paintComponent(g);
        		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        	}
        };
        backgroundPanel.setBounds(0, 0, 1200, 800);
        this.theLayeredPane.add(backgroundPanel, new Integer(0));
        
        // Create a label for the player's name and total money
        this.playerInfoLabel = new JLabel("Name: " + thePlayingPlayer.getPlayerName() + " | Money: " + thePlayingPlayer.getTotalMoney() + "$");
        playerInfoLabel.setBounds(20, 20, 200, 20);
        playerInfoLabel.setForeground(Color.WHITE);
        this.theLayeredPane.add(playerInfoLabel, new Integer(1));

        // Create a label for the dealer's name and cards
        JLabel dealerInfoLabel = new JLabel(theTable.dealer.getDealerName());
        dealerInfoLabel.setBounds(500, 60, 200, 100);
        
        dealerInfoLabel.setForeground(Color.WHITE);
        this.theLayeredPane.add(dealerInfoLabel, new Integer(1));
        // Create an icon for the dealer
        ImageIcon dealerIcon = new ImageIcon(this.dealerIcon);

        // Create a label to display the dealer icon
        JLabel dealerIconLabel = new JLabel(dealerIcon);
        dealerIconLabel.setBounds(530, 40, dealerIcon.getIconWidth(), dealerIcon.getIconHeight());
        this.theLayeredPane.add(dealerIconLabel, new Integer(1));

        // Create buttons for surrender, stand, hit, split, double
        this.surrenderButton = new JButton("Surrender");
        this.surrenderButton.setBounds(300, 650, 100, 20);
        this.theLayeredPane.add(this.surrenderButton, new Integer(1));
        
        surrenderButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = thePlayingPlayer.surrender(); 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        this.standButton = new JButton("Stand");
        this.standButton.setBounds(410, 650, 100, 20);
        this.theLayeredPane.add(this.standButton, new Integer(1));
        
        standButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = thePlayingPlayer.stand(); 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        this.hitButton = new JButton("Hit");
        this.hitButton.setBounds(520, 650, 100, 20);
        this.theLayeredPane.add(this.hitButton, new Integer(1));
        splitButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = thePlayingPlayer.hit(); 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });

        this.splitButton = new JButton("Split");
        this.splitButton.setBounds(630, 650, 100, 20);
        this.theLayeredPane.add(this.splitButton, new Integer(1));
        
        splitButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = thePlayingPlayer.split(); 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        this.doubleButton = new JButton("Double");
        this.doubleButton.setBounds(740, 650, 100, 20);
        this.theLayeredPane.add(this.doubleButton, new Integer(1));
    
        doubleButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = thePlayingPlayer.doubleDown();
      
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });

        this.seatUpButton = new JButton("Seat or Up");
        this.seatUpButton.setBounds(1000, 60, 100, 20);
        this.theLayeredPane.add(this.seatUpButton, new Integer(1));
        
        seatUpButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = seatOrUp(); 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        this.betButton = new JButton("Bet");
        this.betButton.setBounds(940, 650, 100, 20);
        this.theLayeredPane.add(this.betButton, new Integer(1));
      
        
        // Set the preferred size of the window
        setPreferredSize(new Dimension(1200, 800));
        pack(); // Adjust the window size based on the components' preferred sizes
        setVisible(true); // Make the window visible

	}
	

	private String seatOrUp() {
		
		if(isSeat == true) {
			isSeat = false;
			return theTable.removePlayer(this.thePlayingPlayer);
		}
		else {
			isSeat = true;
			return theTable.addPlayer(this.thePlayingPlayer);
		}
		
	}

	public void updateTableDisplay(String message) {
		
		// Create and position components for each seated player
		this.textJLabel = new JLabel(message);
        this.textJLabel.setBounds(500, 600, 200, 20);
        this.theLayeredPane.add(this.textJLabel, new Integer(1));
		if(isSeat == true) {
			
		    this.thePlayerComponent = new PlayerComponent(this.thePlayingPlayer, this.theLayeredPane, this.thePlayingPlayer.seatIndex);
			
			this.theLayeredPane.add(this.thePlayerComponent, new Integer(1));
				
			// Repaint the layered pane to reflect the changes
			this.theLayeredPane.repaint();
		}
		else {
			removePlayerComponent();

		}
		
		
	    theLayeredPane.repaint();
	}
	
	// Remove the player from the GUI
	public void removePlayerComponent() {
		this.theLayeredPane.remove(this.thePlayerComponent.getPlayerPanel());
	}

}
