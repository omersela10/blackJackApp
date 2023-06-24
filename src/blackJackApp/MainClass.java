package blackJackApp;



public class MainClass {
	
	   public static void main(String[] args) {

		   Player newPlayer = new GuestPlayer();
	
		   Table newTable = OneHundredDollarTable.getInstance();


		   TableWindow wt = new TableWindow(newTable, newPlayer);
	    
	   }
}
