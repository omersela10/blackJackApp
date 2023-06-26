package blackJackApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import blackJackApp.TableController.BetButtonListener;
import blackJackApp.TableController.DoubleButtonListener;
import blackJackApp.TableController.HitButtonListener;
import blackJackApp.TableController.SeatButtonListener;
import blackJackApp.TableController.SplitButtonListener;
import blackJackApp.TableController.StandButtonListener;
import blackJackApp.TableController.SurrenderButtonListener;

public class TableWindow extends JFrame {
	
	// Data Members
	
	private Player thePlayingPlayer;
	private static final String tableIcon = "resources/table/background.png";
	private static final String dealerIcon =  "resources/table/Dealer.png";
	
	private static int dealerX = 500;
	private static int dealerY = 60;
	
	// Pane
	protected JLayeredPane theLayeredPane;
	
	// PlayerComponent
	private PlayerComponent thePlayerComponent;
	
	private JLabel playerInfoLabel;
	private JLabel dealerInfoLabel;
	private JLabel textJLabel;
	protected JLabel timerLabel;
	
	// Buttons
	private JButton betButton;
	private JButton seatOrUpButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton splitButton;
    private JButton surrenderButton;
    private JButton doubleButton;
    

	// Constructor
	public TableWindow(Player newPlayer)   {
		

		this.thePlayingPlayer = newPlayer;
		this.textJLabel = new JLabel();
		
		try {
		     initializeComponents();
		     initializeTimerLabel();
		}
		catch(Exception e) {
			
		}

    }
	public Player getPlayer() {
		return this.thePlayingPlayer;
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



	public void updatePlayerLabel() {
		
		this.theLayeredPane.remove(this.playerInfoLabel);
		this.playerInfoLabel = new JLabel("Name: " + thePlayingPlayer.getPlayerName() + " | Money: " + thePlayingPlayer.getTotalMoney() + "$");
		this.playerInfoLabel.setBounds(20, 20, 200, 20);
		this.playerInfoLabel.setForeground(Color.WHITE);
	    this.theLayeredPane.add(playerInfoLabel, new Integer(1));
     
		
	}

	public void updateDealerComponent(Dealer theDealer) {

        int i = 0;

        for (Card card : theDealer.getDealerHand().getCards()) {
        	
        	String iconPath = "resources/cards/" + card.getIconPath();
            ImageIcon cardIcon = new ImageIcon(iconPath);
            JLabel cardIconLabel = new JLabel(cardIcon);
            cardIconLabel.setBounds(dealerX - 15*i + 25, dealerY + 60, cardIcon.getIconWidth(), cardIcon.getIconHeight());
            cardIconLabel.setForeground(Color.WHITE);
            this.theLayeredPane.add(cardIconLabel, new Integer(1));
            i += 1;
         
            if(theDealer.isDealerTurn() == false) {
            	break;
            }
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
        this.dealerInfoLabel = new JLabel();
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
        this.surrenderButton = new JButton("Surrender");
        this.surrenderButton.setBounds(300, 650, 100, 20);
        this.theLayeredPane.add(this.surrenderButton, new Integer(1));
        
   
        
        this.standButton = new JButton("Stand");
        this.standButton.setBounds(410, 650, 100, 20);
        this.theLayeredPane.add(standButton, new Integer(1));
        
   
        
        this.hitButton = new JButton("Hit");
        this.hitButton.setBounds(520, 650, 100, 20);
        this.theLayeredPane.add(this.hitButton, new Integer(1));
        
 

        this.splitButton = new JButton("Split");
        this.splitButton.setBounds(630, 650, 100, 20);
        this.theLayeredPane.add(this.splitButton, new Integer(1));
        
        this.doubleButton = new JButton("Double");
        this.doubleButton.setBounds(740, 650, 100, 20);
        this.theLayeredPane.add(this.doubleButton, new Integer(1));
    
   

        this.seatOrUpButton = new JButton("Seat or Up");
        this.seatOrUpButton.setBounds(1000, 60, 100, 20);
        this.theLayeredPane.add(this.seatOrUpButton, new Integer(1));
        
  
        
        this.betButton = new JButton("Bet");
        this.betButton.setBounds(940, 650, 100, 20);
        this.theLayeredPane.add(this.betButton, new Integer(1));
        

	}
	// Update message in the table
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
	public  void removePlayerComponent() {
		this.theLayeredPane.remove(this.thePlayerComponent.getPlayerPanel());
		this.theLayeredPane.repaint();
	}


	public void addSeatButtonListener(SeatButtonListener seatButtonListener) {
		seatOrUpButton.addActionListener(seatButtonListener);
		
	}
	public void addBetButtonListener(BetButtonListener betButtonListener) {
		betButton.addActionListener(betButtonListener);
		
	}
	public void addHitButtonListener(HitButtonListener hitButtonListener) {
		hitButton.addActionListener(hitButtonListener);
		
	}
	public void addStandButtonListener(StandButtonListener standButtonListener) {
		standButton.addActionListener(standButtonListener);
		
	}
	public void addSplitButtonListener(SplitButtonListener splitButtonListener) {
		splitButton.addActionListener(splitButtonListener);
		
	}
	public void addDoubleButtonListener(DoubleButtonListener doubleButtonListener) {
		doubleButton.addActionListener(doubleButtonListener);
		
	}
	public void addSurrenderButtonListener(SurrenderButtonListener surrenderButtonListener) {
		surrenderButton.addActionListener(surrenderButtonListener);
		
	}
	public void updateTableComponent(Table table) {
		this.dealerInfoLabel.setText(table.dealer.getDealerName());
		this.updatePlayerComponenet(this.thePlayingPlayer);
		
		
	}
	
	public void updatePlayerComponenet(Player anyPlayer) {
		
		if(anyPlayer.seatIndex != -1) {
			
		    this.thePlayerComponent = new PlayerComponent(this.thePlayingPlayer, this.theLayeredPane, this.thePlayingPlayer.seatIndex);
			this.theLayeredPane.add(this.thePlayerComponent, new Integer(1));	
		}
		else {
			removePlayerComponent();

		}
		this.theLayeredPane.repaint();
	}
	


}
