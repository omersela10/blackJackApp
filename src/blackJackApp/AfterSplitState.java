package blackJackApp;

public class AfterSplitState implements HandState {

	private Hand myHand;
	
	public AfterSplitState(Hand newHand) {
		this.myHand = newHand;
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
 
		this.myHand.setHandState(myHand.getEndOfRoundHand());
	}

	@Override
	public void hit() {
	
		
	}

	@Override
	public void doubleDown() {
		// TODO Auto-generated method stub
		
	}

}
