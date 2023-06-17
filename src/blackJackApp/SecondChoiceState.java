package blackJackApp;

public class SecondChoiceState implements HandState {
	
	private Player thePlayer;
	private Hand currentHand;
	
	public SecondChoiceState(Player newPlayer, Hand newHand) {
		
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
		// JOPtiond."Cannot Surrender";
		return;
	}

	@Override
	public void stand() {
		
		// Update State
		this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
		this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
		
	}

	@Override
	public void hit() {
		
		if(this.currentHand.getSumOfCards() >= 21 || this.currentHand.getSumOfCardsWithAce() >= 21) {
			
			// JOption : Cant Hit
			return;
		}
		
		// Get More Card
		this.currentHand.getMoreCard();
		
	}

	@Override
	public void doubleDown() {
		
		// JOPtiond."Cannot Double";
		return;
		
	}

}
