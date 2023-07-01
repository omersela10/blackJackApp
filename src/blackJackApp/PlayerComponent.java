package blackJackApp;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;

public class PlayerComponent extends JLabel {


    private JLayeredPane thePane;
    private boolean isSeat = false;
    protected int xPlace;
    protected int yPlace;
    public static final String playerIconPath = "resources/table/Player.png";
    public static final String chipsIconPath = "resources/table/chipsIcon.png";
    private JPanel playerPanel;
    private JPanel handsPanel;


    
    public PlayerComponent(JLayeredPane anyPane, int anySeat) {
    	
        this.thePane = anyPane;
        this.updateXYPlace(anySeat);
 
        this.createPlayerPanel();

       
    }

    public void setSeat(boolean isSeat) {
    	
    	this.isSeat = isSeat;
       
    }

    private void updateXYPlace(int anySeatIndex) {
    	
    	this.yPlace = 400;
    	
    	if(anySeatIndex == 1 || anySeatIndex == 2) {
    		this.yPlace += 100; 
    	}
    	
      
        this.xPlace = anySeatIndex * 300 + 150;
       
    }

    private void createPlayerPanel() {
    	
        playerPanel = new JPanel();
        playerPanel.setLayout(null);
        playerPanel.setOpaque(false);
        playerPanel.setBounds(0, 0, 1200, 800);
        thePane.add(playerPanel, new Integer(1));
        
        handsPanel = new JPanel();
        handsPanel.setLayout(null);
        handsPanel.setOpaque(false);
        handsPanel.setBounds(this.xPlace, this.yPlace, 300, 300);
        playerPanel.add(handsPanel, new Integer(1));

    }

    public void clearComponent() {
    	
    	if(playerPanel != null) {
    		playerPanel.removeAll(); // Clear the player panel before updating

    	}
    }
    
    // Update components
    public void updateComponents(Player player) {
    	
    	if(playerPanel != null) {
    		playerPanel.removeAll(); // Clear the player panel before updating

    	}
    	else {
    		
    		return;
    	}
    	
        
        updateNameComponent(player);
        updatePlayerIconComponent();

        if (player.getHands() != null) {
            updateHandsComponent(player);
        }
        else {
        	
        	clearHandsComponent();
        }

        thePane.repaint(); // Repaint the panel
    }

    public void clearHandsComponent() {
    	
    	if(this.playerPanel != null) {
    		this.playerPanel.remove(handsPanel);
    	}
	}

	// This method paints the icon of the player on the screen
    private void updatePlayerIconComponent() {
    	
        ImageIcon playerIcon = new ImageIcon(playerIconPath);
        JLabel playerIconLabel = new JLabel(playerIcon);
        playerIconLabel.setBounds(this.xPlace, this.yPlace, playerIcon.getIconWidth(), playerIcon.getIconHeight());
        playerPanel.add(playerIconLabel);
        playerPanel.repaint();
    }

    // This method paints the name of the player on the screen
    private void updateNameComponent(Player player) {
    	
        JLabel playerName = new JLabel(player.getPlayerName());
        playerName.setBounds(xPlace, yPlace + 50, 100, 20);
        playerName.setForeground(Color.WHITE);
        playerPanel.add(playerName);
        System.out.println(player.getPlayerName() + "added at " + "xPlace:" + xPlace + "yPlace:" + yPlace);
        playerPanel.repaint();
    }

    // This method paints the hands of the player on the screen
    private void updateHandsComponent(Player player) {
    	
    	playerPanel.remove(handsPanel);
        handsPanel = new JPanel();
        handsPanel.setLayout(null);
        handsPanel.setOpaque(false);
        handsPanel.setBounds(0, 0, 1200, 800);
        
        playerPanel.add(handsPanel, new Integer(1));
 
        
        int i = 0;
        
        for (Hand hand : player.getHands()) {
        	
        	// Display hand bet money
            JLabel handMoneyLabel = new JLabel(Integer.toString(hand.getBetMoney()) + "$");
            handMoneyLabel.setBounds(this.xPlace + i * 150, this.yPlace - 150, 80, 10);
            handMoneyLabel.setForeground(Color.WHITE);
            
            // Display chips icon
            ImageIcon chipIcon = new ImageIcon(chipsIconPath);
            JLabel chipIconLabel = new JLabel(chipIcon);
            chipIconLabel.setBounds(this.xPlace + (i * 150) + 25, this.yPlace - 150, chipIcon.getIconWidth(), chipIcon.getIconHeight());

            
            // Display sum of cards
            JLabel sumOfCards = new JLabel(Integer.toString(hand.getSumOfCards()) + "/" + Integer.toString(hand.getSumOfCardsWithAce()));
            sumOfCards.setBounds(this.xPlace + i * 150, this.yPlace - 120, 80, 10);
            sumOfCards.setForeground(Color.WHITE);
            
            handsPanel.add(chipIconLabel);
            handsPanel.add(handMoneyLabel);
            handsPanel.add(sumOfCards);
            
            updateHandComponent(i, hand);
            i += 1;
        }
        thePane.repaint();
    }

    // This method gets a Hand and paints it on the screen
    private void updateHandComponent(int numOfHand, Hand hand) {
    	
        int placeOfHand = 150 * numOfHand;
        System.out.println("SumOfPlayingCards: " + hand.getSumOfPlayingCards());
        
        int i = 0;
        for (Card card : hand.getCards()) {
        	
        	System.out.println("card: " + card.getValue() );
        	
            String iconPath = "resources/cards/" + card.getIconPath();
            ImageIcon cardIcon = new ImageIcon(iconPath);
            JLabel cardIconLabel = new JLabel(cardIcon);
            cardIconLabel.setBounds(placeOfHand + this.xPlace - 15 * i, this.yPlace - 100, cardIcon.getIconWidth(), cardIcon.getIconHeight());
            cardIconLabel.setForeground(Color.WHITE);
            this.handsPanel.add(cardIconLabel, new Integer(1));
            i += 1;
        }
    }

    // Getter
    public JPanel getPlayerPanel() {
        return playerPanel;
    }
}
