package blackJackApp;


public class FirstChoiceState implements HandState{

	// Data Members
	private Player thePlayer;
	private Hand currentHand;
	
	// Constructor
	public FirstChoiceState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
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
	
	@Override
	public void split() {
		
		if(this.currentHand.canSplit() == false || enoughMoneyForSplitOrDouble() == false) {
			//JOption "CANT SPLIT"
			return;
		}
		
		// Update total money
		updateTotalMoneyAfterSplitOrDouble();
		
		// Update Hands
		this.thePlayer.setHands(this.currentHand.createNewHandsAfterSplit());
		
		// Update States
		this.thePlayer.setAfterSplitHand1(new AfterSplitHandOneState(this.thePlayer, this.thePlayer.getHands().get(0)));
		this.thePlayer.setAfterSplitHand2(new AfterSplitHandTwoState(this.thePlayer, this.thePlayer.getHands().get(1)));
		
		// Check if BlackJack in any new hand
		if(this.thePlayer.getHands().get(0).hasBlackJack() == true) {
			this.thePlayer.setAfterSplitHand1(new EndHandRoundState(this.thePlayer, this.thePlayer.getHands().get(0)));
		}
	
		if(this.thePlayer.getHands().get(1).hasBlackJack() == true) {
			this.thePlayer.setAfterSplitHand2(new EndHandRoundState(this.thePlayer, this.thePlayer.getHands().get(1)));
		}
		
		// Update Player State to first hand playing
		this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand1());
	}

	@Override
	public void surrender() {
		
		// Update money
		this.thePlayer.setTotalMoney(this.thePlayer.getTotalMoney() - this.currentHand.getBetMoney()/2);
		this.currentHand.setBetMoney(0);
		
		// Update State
		this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
		this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
	}

	@Override
	public void stand() {
		
		// Update State
		this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
		this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
		
	}

	@Override
	public void hit() {
		
		if(this.currentHand.hasBlackJack() == true) {
			
			// JOption : Cant Hit
			return;
		}
		
		// Get More Card
		this.currentHand.getMoreCard();
		
		// If done
		if(this.currentHand.getSumOfCards() >= 21 || this.currentHand.getSumOfCardsWithAce() >= 21) {
			
				// Update State
				this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
				this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
				
				return;
		}
		
		// Update State
		this.thePlayer.setSecondChoice(this.thePlayer.getHandState());
		this.thePlayer.setTheState(this.thePlayer.getSecondChoice());
	
		
	}

	@Override
	public void doubleDown() {

		if(this.enoughMoneyForSplitOrDouble() == false) {
			//JOption "No enough money for double"
			return;
		}
		
		// Update total money
		this.updateTotalMoneyAfterSplitOrDouble();
		
		// Get More Card
		this.currentHand.getMoreCard();
		
		// Update State
		this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
		this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
		
	}

}
