package blackJackApp;

import java.util.*;

public class GuestPlayer extends Player {

	// Data Members
	private final String GUEST = "Guest";
	private String guestName;
	private int guestMoney;
	private static Integer numberOfGuests = 0;
	
	// Constructor
	public GuestPlayer() {
		
		this.guestMoney = 1000;
		numberOfGuests += 1;
		this.guestName = GUEST + numberOfGuests.toString();
		
	}

	// Getters
	public String getGuestName() {
		return guestName;
	}
	
	@Override
	public List<Hand> getHands() {
		return super.hands;
	}


	@Override
	public int getTotalMoney() {
		return this.guestMoney;
	}
	
	public static int getNumberOfGuests() {
		return numberOfGuests;
	}
	
	public void setHands(List<Hand> hands) {
		this.hands = hands;
	}

	// Setters

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


	@Override
	protected void setTotalMoney(int newAmount) {
		this.guestMoney = newAmount;
		
	}

	
	public static void setNumberOfGuests(int numberOfGuests) {
		GuestPlayer.numberOfGuests = numberOfGuests;
	}


	

	@Override
	public void up() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean seat(int place) {
		// TODO Auto-generated method stub
		return false;
	}




}
