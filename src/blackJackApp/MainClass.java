package blackJackApp;



public class MainClass {
	
	   public static void main(String[] args) {

		   Player newPlayer = new GuestPlayer();
		   //LobbyWindow lw = new LobbyWindow(newPlayer);
		   
		   
		Table newTable = OneHundredDollarTable.getInstance();
	
		TableWindow wt = new TableWindow(newTable, newPlayer);
		


	   }
}
