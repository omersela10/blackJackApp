package blackJackApp;

import java.util.*;

public abstract class Player implements HandState {
	
	// Data Members
	protected List<Hand> hands;
	protected boolean isPlay;
	
	// States
	protected HandState firstChoice;
	protected HandState secondChoice;
	protected HandState afterSplitHand1;
	protected HandState afterSplitHand2;
	protected HandState endOfRoundHand;

	protected HandState theState;
	
	// Abstract methods
	protected abstract List<Hand> getHands();
	protected abstract void setHands(List<Hand> anyHands);
	protected abstract int getTotalMoney();
	protected abstract void setTotalMoney(int newAmount);
	public abstract String seat(List<Player> places);

	
	// Constructor
	public Player() {
		
		this.isPlay = false;

	}
	
	// Set the States
	protected void InitializeStates() {
		
		this.afterSplitHand1 = null;
		this.afterSplitHand2 = null;
		this.firstChoice = new FirstChoiceState(this,this.getHands().get(0));
		this.secondChoice = null;
		this.endOfRoundHand = null;
	
		this.theState = this.getFirstChoice();
	}

	public void bet(int betMoney) {
		
		if(betMoney > this.getTotalMoney()) {
			// JOPtion "no enough money"
			return;
		}
		// Set money
		this.setTotalMoney(this.getTotalMoney() - betMoney);
		
		// Set to playing
		this.isPlay = true;
		
		this.hands = new ArrayList<Hand>();
		// Create hand
		this.hands.add(new Hand(betMoney));
		this.InitializeStates();
		
		// If has blackJack, set state to endState
		if(this.getHands().get(0).hasBlackJack() == true) {
			// Update money
			int blackJackPrice = (int) 1.5 * this.getHands().get(0).getBetMoney();
			this.setTotalMoney(this.getTotalMoney() + blackJackPrice);
			// Update State
			this.setTheState(new EndHandRoundState(this, this.getHands().get(0)));
		}
		
	}
	
	// Up Option for player
	public void up(List<Player> places) {
		
		isPlay = false;
		// Update leaving
		for(int i = 0; i < places.size(); i++) {
			if(this.equals(places.get(i)) == true) {
				places.set(i, null);
			}
		}
		
	}
	
	// Getters
	protected HandState getHandState() {
		return this.theState;
	}
	
	protected HandState getFirstChoice() {
		return this.firstChoice;
	}

	protected HandState getSecondChoice() {
		return this.secondChoice;
	}

	protected HandState getAfterSplitHand1() {
		return this.afterSplitHand1;
	}
	
	protected HandState getAfterSplitHand2() {
		return this.afterSplitHand2;
	}
	
	protected HandState getEndOfRoundHand() {
		return this.endOfRoundHand;
	}
	
	protected HandState getTheState() {
		return this.theState;
	}
	
	// Setters
	protected void setTheState(HandState anyState) {
		this.theState = anyState;
	}
	
	protected void setFirstChoice(HandState firstChoice) {
		this.firstChoice = firstChoice;
	}

	protected void setSecondChoice(HandState secondChoice) {
		this.secondChoice = secondChoice;
	}

	protected void setAfterSplitHand1(HandState afterSplitHand1) {
		this.afterSplitHand1 = afterSplitHand1;
	}
	
	protected void setAfterSplitHand2(HandState afterSplitHand2) {
		this.afterSplitHand2 = afterSplitHand2;
	}
	
	protected void setEndOfRoundHand(HandState endOfRoundHand) {
		this.endOfRoundHand = endOfRoundHand;
	}
	
	// Playing functions
	@Override
	public String split() {
		return this.theState.split();
	}

	@Override
	public String surrender() {
		return this.theState.surrender();
		
	}

	@Override
	public String stand() {
		return this.theState.stand();
		
	}

	@Override
	public String hit() {
		return this.theState.hit();
		
	}

	@Override
	public String doubleDown() {
		return this.theState.doubleDown();
		
	}

	
}
