package blackJackApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public abstract class Table{
	
	// Data Members:
	protected final int MAXIMUMPLAYERS = 4;
	protected List<Player> players;
	protected Dealer dealer;
	
	private Timer betTimer;
	private static final int TIMEOUT = 10000;
	private boolean timeoutExpired;
	private volatile boolean anyPlayerBet = false;
	
	
	public abstract int getMinimumBet();
		
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
		

	public boolean betPlayer(Player anyPlayer) {
		
		String inputBet = JOptionPane.showInputDialog("Insert money for play");
		if(inputBet == null || inputBet.isBlank() || inputBet.matches("\\d+") == false) {
			JOptionPane.showMessageDialog(null, "Invalid input");
			return false;
		}
		
		int betMoney = Integer.parseInt(inputBet);
		
		if(betMoney > anyPlayer.getTotalMoney() || betMoney < this.getMinimumBet()) {
			 JOptionPane.showMessageDialog(null, "Enter amount between " + this.getMinimumBet() + anyPlayer.getTotalMoney());
			 return false;
		}
		
		// Start bet
		return anyPlayer.bet(betMoney);
		
	}
	
	public void playRound(Player anyPlayer) {
		
		boolean isWaitingForDealer = true;
		
		do {
			isWaitingForDealer = anyPlayer.getHandState().getClass().getName().equals("EndHandRoundState");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		while(isWaitingForDealer == false);

	}
	
	// Game Logic

	public void Play() {
		
		// While there are players who bets
		if(anyPlayerBet == false) {
			return;
		}
		for(Player anyPlayer: this.getPlayers()) {
			if(anyPlayer.isPlay() == true) {
				 playRound(anyPlayer);
			}
		}
	}

	 public void startBettingPhase() {
		 
		 	setBetsToAllPlayers();
		 	betTimer = new Timer();
	        timeoutExpired = false;

	        betTimer.schedule(new TimerTask() {
	        	
	            @Override
	            public void run() {
	                // Timeout has expired
	                timeoutExpired = true;
	                // "Timeout! Betting phase ended.");
	                // Perform any necessary actions when the timeout occurs
	                Play();
	            }
	        }, TIMEOUT);
	}
	 
   private void setBetsToAllPlayers() {
		for(Player player: this.getPlayers()) {
			player.isPlay = false;
		}
		
	}

   public void stopBettingPhase() {
	  betTimer.cancel();
   }

    public boolean isTimeoutExpired() {
        return timeoutExpired;
    }
    
    public void turnOfDealer() {
    	
    	Card Card2 = this.dealer.getSecondCard();
    	
    	try {
    		
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
    	
    	while(this.dealer.getSumOfDealerCards() < 17) {
    		dealer.getDealerHand().getMoreCard();
    		
    	}
    	updateMoneyOfPlayers();
    	
    }

	private void updateMoneyOfPlayers() {
		
		int dealerSum = this.dealer.getSumOfDealerCards();
		boolean dealerFail = dealerSum > 21;
		
		for(Player player : this.players) {
			if(player.isPlay() == true) {
				this.checkAndUpdateResult(player, dealerSum, dealerFail);
			}
		}
		
	}

	private void checkAndUpdateResult(Player player, int dealerSum, boolean dealerFail) {

		for(Hand hand: player.getHands()) {
			
			int sumOfHandCards = hand.getSumOfPlayingCards();
			boolean blackJackHand = hand.hasBlackJack();
			// TODO: Check all cases and update
			if(hand.getSumOfPlayingCards() > 21) {
				continue;
			}
			else if(dealerFail || hand.getSumOfPlayingCards() > dealerSum) {
				
			}
			
		
			
		}
		
	}

}
