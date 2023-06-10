package blackJackApp;

public class User {

	// Data Members
	private String name;
	private int totalAmount;
	private int numberOfWins;
	private int totalProfit;
	
	// Constructor
	public User(String newName, int newTotalAmount, int anyNumberOfWins, int anyTotalProfit) {
		
		this.name = newName;
		this.totalAmount = newTotalAmount;
		this.numberOfWins = anyNumberOfWins;
		this.totalProfit = anyTotalProfit;
	}
	
	public User(User otherUser) {
		
		this.name = otherUser.name;
		this.totalAmount = otherUser.totalAmount;
		this.numberOfWins = otherUser.numberOfWins;
		this.totalProfit = otherUser.totalProfit;
	}

	// Getters
	public String getName() {
		return name;
	}
	
	public int getTotalAmount() {
		return totalAmount;
	}
	
	public int getNumberOfWins() {
		return numberOfWins;
	}
	
	public int getTotalProfit() {
		return totalProfit;
	}
	
	// Setters
	public void setName(String anyName) {
		this.name = anyName;
	}


	public void setTotalAmount(int anyTotalAmount) {
		this.totalAmount = anyTotalAmount;
	}



	public void setNumberOfWins(int anyNumberOfWins) {
		this.numberOfWins = anyNumberOfWins;
	}



	public void setTotalProfit(int anyTotalProfit) {
		this.totalProfit = anyTotalProfit;
	}
	
	
	
	
}
