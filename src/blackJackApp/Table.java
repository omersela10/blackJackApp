package blackJackApp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Table {
	
	// Data Members:
	private final int MAXIMUMPLAYERS = 4;
	private int minimumBet;
	private List<Player> players;
	private Dealer dealer;
		
	// Constructor:
	public Table(int tableMinBet) {
		
		initializeTable(tableMinBet);
		
	}
	
	// Initialize Table
	private void initializeTable(int tableMinBet) {
		
		this.players = new ArrayList<Player>();
		
		for(int i = 0 ; i < MAXIMUMPLAYERS; i++) {
			this.players.add(null);
		}
		
		this.dealer = new Dealer();
		this.minimumBet = tableMinBet;
	}
	
	// Getters:
	public int getMinimumBet() {
		return this.minimumBet;
	}
	
	public List<Player> getPlayers(){
		return this.players;
	}
		
	// Methods:
	
	// Seat Player to Table
	public void addPlayer(Player toAddPlayer) {
		
		toAddPlayer.seat(this.players);
		
	}
		
	// Leave player from the Table
	public void removePlayer(Player toRemovePlayer) {
	
		toRemovePlayer.up(players);
	}
		

	public void betPlayer(Player anyPlayer) {
		
		String inputBet = JOptionPane.showInputDialog("Insert money for play");
		if(inputBet.isBlank() || inputBet == null || inputBet.matches("\\d+") == false) {
			JOptionPane.showInputDialog("Invalid input");
		}
		
		int betMoney = Integer.parseInt(inputBet);
		
		if(betMoney > anyPlayer.getTotalMoney() || betMoney < this.getMinimumBet()) {
			 JOptionPane.showInputDialog("Enter amount between " + this.minimumBet + anyPlayer.getTotalMoney());
			 return;
		}
		
		// Start bet
		anyPlayer.bet(betMoney);
	}

}
