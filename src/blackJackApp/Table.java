package blackJackApp;

import java.util.ArrayList;
import java.util.List;

public class Table {
	// Data Members:
		private int minBet;
		private List<Player> players;
		private Dealer dealer;
		
	// Constructor:
		public Table(int tableMinBet ) {
			this.minBet = tableMinBet;
			this.players = new ArrayList<Player>(4);
			this.dealer = new Dealer();
		}
		
	//Getters:
		public int GetMinBet() {
			return this.GetMinBet();
		}
		public List<Player> GetPlayers(){
			return this.players;
		}
		
	//Functionabiliy:
		//Load user as a player to the Table
		public void addPlayer(Player toAddPlayer, int place) {
			if(this.players.get(place)== null) {
				this.players.set(place, toAddPlayer);
				//TODO: update player's sitting index to 'place'
			}
			else {
				//TODO: notify this place is taken
			}
		}
		
		//Remove player from the Table
		public void removePlayer(Player toRemovePlayer) {
			if(this.players.get(toRemovePlayer.'GetPlace')== toRemovePlayer) {
				this.players.remove(toRemovePlayer.'GetPlace');
				//TODO: update player's sitting index to null
			}
			else {
				//TODO: error handling 
			}
		}
		
	//Game logic:
		//Hand cards to players and dealer
		

}
