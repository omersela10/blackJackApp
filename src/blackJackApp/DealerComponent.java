package blackJackApp;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class DealerComponent extends JLabel {
	
	private JLayeredPane thePane;    
	private JPanel dealerPanel;
	
	private static int dealerX = 500;
	private static int dealerY = 60;
	
    public DealerComponent(JLayeredPane anyPane) {
    	
        this.thePane = anyPane;
        this.createDealerPanel();

    }
    
   private void createDealerPanel() {
    	
	   dealerPanel = new JPanel();
	   dealerPanel.setLayout(null);
	   dealerPanel.setOpaque(false);
	   dealerPanel.setBounds(0, 0, 1200, 800);
       thePane.add(dealerPanel, new Integer(1));
    }
    
    public void updateComponent(Dealer dealer) {

        int i = 0;

        for (Card card : dealer.getDealerHand().getCards()) {
        	
        	String iconPath = "resources/cards/" + card.getIconPath();
        	System.out.println("deler card :" + card.getValue());
            ImageIcon cardIcon = new ImageIcon(iconPath);
            JLabel cardIconLabel = new JLabel(cardIcon);
            cardIconLabel.setBounds(dealerX - 15*i + 25, dealerY + 60, cardIcon.getIconWidth(), cardIcon.getIconHeight());
            cardIconLabel.setForeground(Color.WHITE);
            this.dealerPanel.add(cardIconLabel, new Integer(1));
            
            i += 1;
         
            if(dealer.isDealerTurn() == false) {
            	break;
            }
        }
    
    }
    public void clearDealerComponent() {
    	this.thePane.remove(dealerPanel);
    	this.thePane.repaint();
    
    }
}
