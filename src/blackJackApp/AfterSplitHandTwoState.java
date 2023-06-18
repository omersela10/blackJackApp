package blackJackApp;

public class AfterSplitHandTwoState implements HandState {

	// Data Members
	private Player thePlayer;
	private Hand currentHand;
	private boolean afterHit = false;
	
	// Constructor
	public AfterSplitHandTwoState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
	}

	@Override
	public String split() {
		
		return "Cannot split twice";

	}

	@Override
	public String surrender() {
		
		return "Cannot surrender";
	}

	@Override
	public String stand() {
 
		// Update State
		this.thePlayer.setTheState(new EndHandRoundState(this.thePlayer, this.currentHand));
		return "Stand";
	}

	@Override
	public String hit() {
	
		if(this.currentHand.getSumOfCards() >= 21 || this.currentHand.getSumOfCardsWithAce() >= 21) {
			
			// JOption : Cant Hit
			return "Can't hit";
		}
		
		// Get More Card
		this.currentHand.getMoreCard();
		this.afterHit = true;
		
		// If done
		if(this.currentHand.getSumOfCards() >= 21 || this.currentHand.getSumOfCardsWithAce() >= 21) {
			
			// Update State
			this.thePlayer.setTheState(new EndHandRoundState(this.thePlayer, this.currentHand));
		}
		
		// Else, stay in current state
		return "Hitted";
	}

	@Override
	public String doubleDown() {
		
		if(this.afterHit == true || this.enoughMoneyForSplitOrDouble() == false) {
		
			return "Can't Double";
		}

		// Update total money
		this.updateTotalMoneyAfterSplitOrDouble();
		
		// Get More Card
		this.currentHand.getMoreCard();
		
		// Update State
		this.thePlayer.setTheState(new EndHandRoundState(this.thePlayer, this.currentHand));
		
		return "Double down";
	}
	
	// Helper method for check enough money for split or double
	private boolean enoughMoneyForSplitOrDouble() {
		
		return this.thePlayer.getTotalMoney() >= 2*this.currentHand.getBetMoney();
	}
	
	// Help method for update money after split or double
	private void updateTotalMoneyAfterSplitOrDouble() {
		// Update total money
		this.thePlayer.setTotalMoney(this.thePlayer.getTotalMoney() - this.currentHand.getBetMoney());
	}

}