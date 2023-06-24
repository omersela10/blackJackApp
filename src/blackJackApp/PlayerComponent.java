package blackJackApp;

import java.awt.*;

import javax.swing.*;

public class PlayerComponent extends JLabel {

    private Player thePlayer;
    private JLayeredPane thePane;
    private int seatIndex;
    private int xPlace;
    private int yPlace;
    public static final String playerIconPath = "resources/table/Player.png";
    private JPanel playerPanel;


    public PlayerComponent(Player player, JLayeredPane anyPane, int anySeat) {
    	
        this.thePlayer = player;
        this.thePane = anyPane;
        this.seatIndex = anySeat;
  
        this.updateXYPlace();
        this.createPlayerPanel();
        this.updateComponents();
    }

    private void updateXYPlace() {
    	
    	this.xPlace = this.seatIndex * 300 + 100;
        this.yPlace = 400;
        
    }

    private void createPlayerPanel() {
    	
        playerPanel = new JPanel();
        playerPanel.setLayout(null);
        playerPanel.setOpaque(false);
        playerPanel.setBounds(0, 0, 1200, 800);
        thePane.add(playerPanel, new Integer(1));
    }
    // Update components
    private void updateComponents() {
    	
        updateNameComponent();
        updatePlayerIconComponent();
        
        if (this.thePlayer.getHands() != null) {
            updateHandsComponent();
        }
    }
    
    // This method paint the icon of the player on the screen
    private void updatePlayerIconComponent() {
    	
        ImageIcon playerIcon = new ImageIcon(playerIconPath);
        JLabel playerIconLabel = new JLabel(playerIcon);
        playerIconLabel.setBounds(this.xPlace, this.yPlace, playerIcon.getIconWidth(), playerIcon.getIconHeight());
        playerPanel.add(playerIconLabel);
    }
    
    // This method paint the name of the player on the screen
    private void updateNameComponent() {
    	
        JLabel playerName = new JLabel(this.thePlayer.getPlayerName());
        playerName.setBounds(xPlace, yPlace + 50, 100, 20);
        playerName.setForeground(Color.WHITE);
        playerPanel.add(playerName);
    }

    // This method paint the hands of the player on the screen
    private void updateHandsComponent() {
    	
        int i = 0;
        
        for (Hand hand : this.thePlayer.getHands()) {
        	
            JLabel handMoneyLabel = new JLabel(Integer.toString(hand.getBetMoney()) + "$");
            handMoneyLabel.setBounds(this.xPlace + i * 50, this.yPlace - 140, 80, 10);
            handMoneyLabel.setForeground(Color.WHITE);
            playerPanel.add(handMoneyLabel);
            
            updateHandComponent(i, hand);
            i += 1;
        }
    }

    // This method get Hand and painting it on the screen
    private void updateHandComponent(int numOfHand, Hand hand) {
    	
        int placeOfHand = 150 * numOfHand;
        
        String sumOfCards = Integer.toString(hand.getSumOfCards()) + "\\" + Integer.toString(hand.getSumOfCardsWithAce());
        int i = 0;

        for (Card card : hand.getCards()) {
        	
        	String iconPath = "resources/cards/" + card.getIconPath();
            ImageIcon cardIcon = new ImageIcon(iconPath);
            JLabel cardIconLabel = new JLabel(cardIcon);
            cardIconLabel.setBounds(placeOfHand + this.xPlace - 15 * i, this.yPlace - 100, cardIcon.getIconWidth(), cardIcon.getIconHeight());
            cardIconLabel.setForeground(Color.WHITE);
            this.playerPanel.add(cardIconLabel, new Integer(1));
            i += 1;
        }
        
    }

    // Getter
    public JPanel getPlayerPanel() {
        return playerPanel;
    }
    
}
