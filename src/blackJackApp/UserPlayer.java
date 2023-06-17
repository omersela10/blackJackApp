package blackJackApp;

import java.util.*;

public class UserPlayer extends Player{

	// Data Members
	private User user;
	
	public UserPlayer(User newUser) {
		this.user = newUser;
	}

	@Override
	public List<Hand> getHands() {
		return super.hands;
	}

	@Override
	public void setTotalMoney(int newAmount) {
		this.user.setTotalAmount(newAmount);
		
	}

	

	@Override
	public void up() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalMoney() {
		return this.user.getTotalAmount();
	}

	@Override
	protected void setHands(List<Hand> anyHands) {
		this.hands = anyHands;
		
	}

	@Override
	public boolean seat(int place) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
