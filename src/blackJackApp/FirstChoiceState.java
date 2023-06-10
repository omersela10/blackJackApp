package blackJackApp;

interface play{
	
	void bet();
	void seat();
	void out();
	
}
class Guest implements Play{
	
}
class Player implements Play, extend User{
	
	public Player(User a) {
		super(a);
		this.Hand = new 
	}
}
public class FirstChoiceState implements HandState {

	private Play  
	
	public FirstChoiceState(Hand newHand) {
		this.myHand = newHand;
	}

	@Override
	public void split() {

		handbefore = this.plyar.getHand();
		Hand1 = new Hand()
		hand1.cards[0] = 
		this.myHand.setHandState(this.myHands[0].getAfterSplit());
	}

	@Override
	public void surrender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doubleDown() {
		// TODO Auto-generated method stub
		
	}

}
