package blackJackApp;



public class MainClass {
	
	   public static void main(String[] args) {

		   Player newPlayer = new GuestPlayer();
		   
		   newPlayer.bet(200);
		   newPlayer.hit();
		   System.out.println("");
	    
	   }
}
