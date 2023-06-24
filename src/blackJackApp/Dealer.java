package blackJackApp;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	
	// Data Members
	private final String DEALER = "Dealer";
	private String dealerName;
	private Hand dealerHand;
	private boolean dealerTurn = false;
	public HandStateChangeListener dealerStateChangeListener;
			
	// Constructor
	public Dealer(int minimumBet) {
		
		this.dealerName = DEALER + " Of Table " + minimumBet + "$";
		this.dealerHand = new Hand(0);
	
	}
	
	// Getters
	public String getDealerName() {
		return this.dealerName;
	}
	public Card getFirstCard() {
		return this.dealerHand.getCards().get(0);
	}
	
	public Card getSecondCard() {
		return this.dealerHand.getCards().get(1);
	}
	public List<Card> getDealerCards(){
		return this.dealerHand.getCards();
	}

	public boolean isDealerTurn() {
		return this.dealerTurn;
	}
	
	public int getSumOfDealerCards() {
		
		return this.getDealerHand().getSumOfPlayingCards();
	}
	public Hand getDealerHand() {
		return dealerHand;
	}
	
	// Setters:
	public void SetDealerHand(Hand newHand) {
		this.dealerHand = newHand;
	}
	public void setDealerTurn(boolean turn) {
		this.dealerTurn = turn;
	}
	
	
   public void registerDealerStateChangeListener(HandStateChangeListener listener) {
        this.dealerStateChangeListener = listener;
    }

   public void notifyDealerStateChangeListener() {
        if (this.dealerStateChangeListener != null) {
        	this.dealerStateChangeListener.onDealerPlay();
        }
    }
	
	
			
}
