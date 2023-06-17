package blackJackApp;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	
	// Data Members
	private final String DEALER = "Dealer";
	private String dealerName;
	private Hand dealerHand;
	private static Integer numberOfDealers = 0;
			
	// Constructor
	public Dealer() {
		
		numberOfDealers += 1;
		this.dealerName = DEALER + numberOfDealers.toString();

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
	
	
			
}
