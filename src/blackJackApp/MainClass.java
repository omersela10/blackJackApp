package blackJackApp;



public class MainClass {
	
	   public static void main(String[] args) {

		   Player newPlayer = new GuestPlayer();
		   //LobbyWindow lw = new LobbyWindow(newPlayer);
		   
		   
		   Table newTable = OneHundredDollarTable.getInstance();
		   newTable.addPlayer(newPlayer);
		   newTable.addPlayer(new UserPlayer(new User("ahigad", 2000, 0, 0)));
		   TableWindow wt = new TableWindow(newTable, newPlayer);
	    
	   }
}
