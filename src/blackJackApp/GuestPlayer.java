package blackJackApp;

import java.util.*;

public class GuestPlayer implements Player {

	// Data Members
	private final String GUEST = "Guest";
	private List<Hand> hands;
	private String guestName;
	private int guestMoney;
	private static Integer numberOfGuests;
	
	// Constructor
	public GuestPlayer() {
		
		this.hands = new ArrayList<Hand>();
		this.guestMoney = 1000;
		numberOfGuests += 1;
		this.guestName = GUEST + numberOfGuests.toString();
	}
	public List<Hand> getHands() {
		return hands;
	}

	public void setHands(List<Hand> hands) {
		this.hands = hands;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getGuestMoney() {
		return guestMoney;
	}

	public void setGuestMoney(int guestMoney) {
		this.guestMoney = guestMoney;
	}

	public static int getNumberOfGuests() {
		return numberOfGuests;
	}

	public static void setNumberOfGuests(int numberOfGuests) {
		GuestPlayer.numberOfGuests = numberOfGuests;
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
