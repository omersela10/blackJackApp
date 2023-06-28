package blackJackApp;


import java.util.*;
public class MainClass {
	
	   public static void main(String[] args) {

		    Player newPlayer = new GuestPlayer();
		    Player newPlayer2 = new GuestPlayer();
		    //LobbyWindow lw = new LobbyWindow(newPlayer);
			   
			   
			Table newTable = OneHundredDollarTable.getInstance();
			TableWindow wt = new TableWindow(newPlayer);
			TableWindow wt1 = new TableWindow(newPlayer2);

			List<TableWindow> l = new ArrayList<TableWindow>();
			
			TableController nTc = new TableController(newTable, l);
			newTable.setTableController(nTc);
			nTc.addWindow(wt);
		
			nTc.addWindow(wt1);

	   }
}
