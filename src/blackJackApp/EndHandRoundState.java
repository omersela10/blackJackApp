package blackJackApp;

public class EndHandRoundState implements HandState {
	
	private Player thePlayer;
	private Hand currentHand;
	
	public EndHandRoundState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
	}

	@Override
	public void split() {
		// JOption Can't Play
		return;
	}

	@Override
	public void surrender() {
		// JOption Can't Play
		
	}

	@Override
	public void stand() {
		// JOption Can't Play
		
	}

	@Override
	public void hit() {
		// JOption Can't Play
		
	}

	@Override
	public void doubleDown() {
		// JOption Can't Play
		
	}

	
}
