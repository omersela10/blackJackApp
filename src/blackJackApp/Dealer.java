package blackJackApp;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	
		// Data Members
		private final String DEALER = "dealer";
		private String dealerName;
		private Hand dealerHand;
		private static Integer numberOfDealers;
			
		// Constructor
		public Dealer() {
			numberOfDealers +=1;
			this.dealerName = DEALER + numberOfDealers.toString();

			//TODO: get hand
			
		}
		
		//Getters:
		public String GetDealerName() {
			return this.dealerName;
		}
		
		public Hand getDealerHand() {
			return dealerHand;
		}
		
		//Setters:
		public void SetDealerHand(Hand newHand) {
			this.dealerHand = newHand;
		}
			
}
