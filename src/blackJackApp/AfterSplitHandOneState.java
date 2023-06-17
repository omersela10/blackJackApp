package blackJackApp;

public class AfterSplitHandOneState implements HandState {

	private Player thePlayer;
	private Hand currentHand;
	private boolean afterHit = false;
	
	public AfterSplitHandOneState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
	}

	@Override
	public void split() {
		// JOPtiond."Cannot split twice";
		return;

	}

	@Override
	public void surrender() {
		// JOPtiond."Cannot surrender";
		return;
	}

	@Override
	public void stand() {
 
		// Update State
		this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand2());
	}

	@Override
	public void hit() {
	
		if(this.currentHand.getSumOfCards() >= 21 || this.currentHand.getSumOfCardsWithAce() >= 21) {
			
			// JOption : Cant Hit
			return;
		}
		
		// Get More Card
		this.currentHand.getMoreCard();
		this.afterHit = true;
		
		// If done
		if(this.currentHand.getSumOfCards() >= 21 || this.currentHand.getSumOfCardsWithAce() >= 21) {
			
			// Update State
			this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand2());
		}
		
		// Else, stay in current state
		
	}

	@Override
	public void doubleDown() {
		
		if(this.afterHit == true || this.enoughMoneyForSplitOrDouble() == false) {
			// jOption cant double
			return;
		}

		// Update total money
		this.updateTotalMoneyAfterSplitOrDouble();
		
		// Get More Card
		this.currentHand.getMoreCard();
		
		// Update State
		this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand2());
		
		
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
