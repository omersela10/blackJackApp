package blackJackApp;


import java.util.*;

import javax.swing.SwingUtilities;


public class MainClass {
	
	   public static void main(String[] args) {

		   // Player newPlayer = new GuestPlayer();
		   // Player newPlayer2 = new GuestPlayer();
		   // //LobbyWindow lw = new LobbyWindow(newPlayer)
		   
			//Table newTable = OneHundredDollarTable.getInstance();
			//TableWindow wt = new TableWindow(newPlayer);

			//List<TableWindow> l = new ArrayList<TableWindow>();
			//TableController nTc = new TableController(newTable, l);
			//newTable.setTableController(nTc);
			//nTc.addWindow(wt);
			//LobbyWindow lobby = new LobbyWindow(newPlayer);
			
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new MainWindow();
	            }
	        });
		   


//		   //sound test
//		   SoundPlayer sp = new SoundPlayer("resources/sounds/cardDraw.wav");
//		   sp.play();


	   }
}
