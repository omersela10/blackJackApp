package blackJackApp;

import java.util.*;

public class UserPlayer extends User implements Player {

	// Data Members
	private List<Hand> hands;
	
	public UserPlayer(User anyUser) {
		
		super(anyUser);
		this.hands = new ArrayList<Hand>();
		
	}

	@Override
	public Hand getHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTotalMoney() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void up() {
		// TODO Auto-generated method stub
		
	}

}
