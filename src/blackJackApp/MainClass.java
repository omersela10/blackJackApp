package blackJackApp;



public class MainClass {
	
	   public static void main(String[] args) {

		   Player newPlayer = new GuestPlayer();
		   
		   Table newTable = new Table(200);
		   newTable.addPlayer(newPlayer);
		   newTable.addPlayer(new UserPlayer(new User("ahigad", 2000, 0, 0)));
		   newTable.betPlayer(newPlayer);
		   System.out.println("");
	    
	   }
}
