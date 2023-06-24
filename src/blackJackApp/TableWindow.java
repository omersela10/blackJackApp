package blackJackApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TableWindow extends JFrame implements HandStateChangeListener {
	
	// Data Members
	private Table theTable;
	private Player thePlayingPlayer;
	private boolean isSeat = false;
	private static final String tableIcon = "resources/table/background.png";
	private static final String dealerIcon =  "resources/table/Dealer.png";
	private int dealerX = 500;
	private int dealerY = 60;
	// Pane
	protected JLayeredPane theLayeredPane;
	//PlayerComponent
	private PlayerComponent thePlayerComponent;
	// Buttons
	private JLabel playerInfoLabel;
	private JLabel textJLabel;
	private JLabel timerLabel;
	// Constructor
	public TableWindow(Table newTable, Player newPlayer)   {
		
		this.theTable = newTable;
		this.thePlayingPlayer = newPlayer;
		this.textJLabel = new JLabel();
		
		try {
		     initializeComponents();
		     initializeTimerLabel();
		}
		catch(Exception e) {
			
		}

    }
	
	public void initializeComponents() throws IOException {
		
		setTitle("Blackjack Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        this.setPane();
        
        // Set the preferred size of the window
        setPreferredSize(new Dimension(1200, 800));
        pack(); // Adjust the window size based on the components' preferred sizes
        setVisible(true); // Make the window visible

	}
	
	private void initializeTimerLabel() {
	    timerLabel = new JLabel();
	    timerLabel.setBounds(800, 10, 200, 30); // Set the desired position and size of the label
	    timerLabel.setForeground(Color.WHITE); // Set the text color
	    theLayeredPane.add(timerLabel, new Integer(1)); // Add the label to the layered pane
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
		
		this.updateMessage(message);
		this.updatePlayerComponent();
		if(theTable.anyPlayerBet() == true) {
			this.updateDealerComponent();
		}
		
	    theLayeredPane.repaint();
	}
	
	public void updatePlayerLabel() {
		
		this.theLayeredPane.remove(this.playerInfoLabel);
		this.playerInfoLabel = new JLabel("Name: " + thePlayingPlayer.getPlayerName() + " | Money: " + thePlayingPlayer.getTotalMoney() + "$");
		this.playerInfoLabel.setBounds(20, 20, 200, 20);
		this.playerInfoLabel.setForeground(Color.WHITE);
	    this.theLayeredPane.add(playerInfoLabel, new Integer(1));
     
		
	}

	public void updateDealerComponent() {

        int i = 0;

        for (Card card : theTable.dealer.getDealerHand().getCards()) {
        	
        	String iconPath = "resources/cards/" + card.getIconPath();
            ImageIcon cardIcon = new ImageIcon(iconPath);
            JLabel cardIconLabel = new JLabel(cardIcon);
            cardIconLabel.setBounds(this.dealerX - 15*i + 25, this.dealerY + 60, cardIcon.getIconWidth(), cardIcon.getIconHeight());
            cardIconLabel.setForeground(Color.WHITE);
            this.theLayeredPane.add(cardIconLabel, new Integer(1));
            i += 1;
         
            if(theTable.dealer.isDealerTurn() == false) {
            	break;
            }
        }
        this.theLayeredPane.repaint();
       
	}

	public void updatePlayerComponent() {
		
		if(isSeat == true) {
			
		    this.thePlayerComponent = new PlayerComponent(this.thePlayingPlayer, this.theLayeredPane, this.thePlayingPlayer.seatIndex);
			this.theLayeredPane.add(this.thePlayerComponent, new Integer(1));	
		}
		else {
			removePlayerComponent();

		}
		this.theLayeredPane.repaint();
	}

	private void setPane() throws IOException  {
		
		
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
        dealerInfoLabel.setBounds(dealerX, dealerY, 200, 100);
        
        dealerInfoLabel.setForeground(Color.WHITE);
        this.theLayeredPane.add(dealerInfoLabel, new Integer(1));
        // Create an icon for the dealer
        ImageIcon dealerIcon = new ImageIcon(this.dealerIcon);

        // Create a label to display the dealer icon
        JLabel dealerIconLabel = new JLabel(dealerIcon);
        dealerIconLabel.setBounds(dealerX + 30, dealerY - 20, dealerIcon.getIconWidth(), dealerIcon.getIconHeight());
        this.theLayeredPane.add(dealerIconLabel, new Integer(1));

        // Create buttons for surrender, stand, hit, split, double
        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.setBounds(300, 650, 100, 20);
        this.theLayeredPane.add(surrenderButton, new Integer(1));
        
        surrenderButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = "";
      	    if(thePlayingPlayer.getHandState() == null) {
             	  message = "Please bet first";
            }
            else {
             	 message = thePlayingPlayer.surrender();
             } 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        JButton standButton = new JButton("Stand");
        standButton.setBounds(410, 650, 100, 20);
        this.theLayeredPane.add(standButton, new Integer(1));
        
        standButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = "";
  	        if(thePlayingPlayer.getHandState() == null) {
         	  message = "Please bet first";
            }
            else {
         	  message = thePlayingPlayer.stand();
            }
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        JButton hitButton = new JButton("Hit");
        hitButton.setBounds(520, 650, 100, 20);
        this.theLayeredPane.add(hitButton, new Integer(1));
        hitButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = "";
        	
            if(thePlayingPlayer.getHandState() == null) {
            	message = "Please bet first";
            }
            else {
            	message = thePlayingPlayer.hit();
            } 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });

        JButton splitButton = new JButton("Split");
        splitButton.setBounds(630, 650, 100, 20);
        this.theLayeredPane.add(splitButton, new Integer(1));
        
        splitButton.addActionListener(e -> {
            // Code to handle the seat button click event
    	    String message = "";
    	    if(thePlayingPlayer.getHandState() == null) {
           	  message = "Please bet first";
            }
            else {
           	  message = thePlayingPlayer.split();
            }
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        JButton doubleButton = new JButton("Double");
        doubleButton.setBounds(740, 650, 100, 20);
        this.theLayeredPane.add(doubleButton, new Integer(1));
    
        doubleButton.addActionListener(e -> {
            // Code to handle the seat button click event
        	String message = "";
        	
            if(thePlayingPlayer.getHandState() == null) {
            	message = "Please bet first";
            }
            else {
            	message = thePlayingPlayer.doubleDown();
            }
        	
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });

        JButton seatUpButton = new JButton("Seat or Up");
        seatUpButton.setBounds(1000, 60, 100, 20);
        this.theLayeredPane.add(seatUpButton, new Integer(1));
        
        seatUpButton.addActionListener(e -> {
            // Code to handle the seat button click event
            
        	String message = seatOrUp(); 
        	// Update the table display to reflect the changes
            updateTableDisplay(message); 
        });
        
        JButton betButton = new JButton("Bet");
        betButton.setBounds(940, 650, 100, 20);
        this.theLayeredPane.add(betButton, new Integer(1));
        
        betButton.addActionListener(e -> {
            // Code to handle the seat button click event
        	if(isSeat == false) {
        		updateMessage("Please seat before bet");
        		return;
        	}
            theTable.startBettingPhase(this, this.timerLabel, this.thePlayingPlayer);
           
            
           
       
        });
        
	}
	public void updateMessage(String message) {
		
		this.theLayeredPane.remove(this.textJLabel);
		this.textJLabel.setText("");
		this.textJLabel.setText(message);
        this.textJLabel.setBounds(500, 600, 200, 30);
        this.textJLabel.setForeground(Color.WHITE);
        this.theLayeredPane.add(this.textJLabel, new Integer(1));
        this.theLayeredPane.repaint();
	}

	// Remove the player from the GUI
	public void removePlayerComponent() {
		this.theLayeredPane.remove(this.thePlayerComponent.getPlayerPanel());
		this.theLayeredPane.repaint();
	}

	public void clearTable() {
		try {
			this.theLayeredPane.repaint();
			this.setPane();
			this.theLayeredPane.repaint();
		}
		catch(Exception e) {
			
		}
	}
	public void startGame()   {
		
		this.updateTableDisplay("Start Game");
		this.updateDealerComponent();
		this.updatePlayerLabel();
		this.theTable.afterBetting();
		boolean anyAlivePlayer = this.theTable.playersTurn(this);
		if(anyAlivePlayer == true) {
			this.theTable.turnOfDealer(this);
		}
		
		this.theTable.updateMoneyOfPlayers(this);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.theTable.finishRound(this);
	}

	@Override
	public void onHandStateChanged() {
		this.updatePlayerComponent();
		
	}

	@Override
	public void onDealerPlay() {
		this.updateDealerComponent();
		
	}
}
