package blackJackApp;

import java.util.*;

import javax.swing.JOptionPane;

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
	public int getTotalMoney() {
		return this.user.getTotalAmount();
	}

	@Override
	protected void setHands(List<Hand> anyHands) {
		this.hands = anyHands;
		
	}

	@Override
	public String seat(List<Player> places) {
		
		// Ask for place:
		String userInput = JOptionPane.showInputDialog("Insert place between 1-4");
		if(userInput == null || userInput.isBlank() == true) {
			JOptionPane.showInputDialog("Invalid input");
		}
		
		int place = Integer.parseInt(userInput) - 1;
		
		if(place < 0 || place > 3) {
			JOptionPane.showInputDialog("Invalid input");
		}
		
		if(places.get(place) != null) {
			return "Occupied";
		}
		// Vacant so seat
		places.set(place, this);
		return "Seated in "+ userInput;
	}
	


}
