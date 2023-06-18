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
	
	// Help Method for check valid player
	private boolean playingPlayer(Player anyPlayer) {
		return anyPlayer != null && anyPlayer.isPlay() == true;
	}
	
	// Initialize Table
	private void initializeTable(int tableMinBet) {
		
		this.players = new ArrayList<Player>();
		
		for(int i = 0 ; i < MAXIMUMPLAYERS; i++) {
			this.players.add(null);
		}
		
		this.dealer = new Dealer();
	}
	
	// Getter
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
		

	// Help method for get from player initial bet any create new cards.
	private boolean betPlayer(Player anyPlayer) {
		
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
			// If player in endRound State
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

	public void playersTurn() {
		
		// If there are no players who bets
		if(anyPlayerBet == false) {
			return;
		}
		
		for(Player anyPlayer: this.getPlayers()) {
			if(playingPlayer(anyPlayer) == true) {
				 playRound(anyPlayer);
			}
		}
	}

	// Start Betting Phase.
	 private void startBettingPhase() {
		 
		 	betTimer = new Timer();
	        timeoutExpired = false;

	        betTimer.schedule(new TimerTask() {
	        	
	            @Override
	            public void run() {
	                // Timeout has expired
	                timeoutExpired = true;
	                // "Timeout! Betting phase ended.");
	                // Perform any necessary actions when the timeout occurs
	                
	            }
	        }, TIMEOUT);
	}
	 
	// After round is over, no one is playing until next timeout.
   private void finishRound() {
	   
		for(Player player: this.getPlayers()) {
			if(player != null) {
				player.isPlay = false;
			}
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
    	
    	
    }

    // Update money when dealer finish
	private void updateMoneyOfPlayers() {
		
		int dealerSum = this.dealer.getSumOfDealerCards();
		boolean dealerFail = dealerSum > 21;
		
		for(Player player : this.players) {
			if(playingPlayer(player) == true) {
				this.checkAndUpdateResultForPlayer(player, dealerSum, dealerFail);
			}
		}
		
	}


	// Deal Cards after bets are done
	private void dealCards() {
		
		for(Player anyPlayer: this.players) {
		
			if(anyPlayer != null) {
				// Time to bet
				boolean anyBet = this.betPlayer(anyPlayer);
				// If any bet, update there is someone who bets
				if(anyBet == true) {
					this.anyPlayerBet = true;
				}
			}
		}
	}
	
	// Help method for update money
	private void checkAndUpdateResultForPlayer(Player player, int dealerSum, boolean dealerFail) {

		for(Hand hand: player.getHands()) {
			
			int winPrize = checkAndUpdateResultForHand(hand, dealerSum, dealerFail);
			// Update money
			player.setTotalMoney(player.getTotalMoney() + winPrize);
	
		}
		
	
	}
	
	// Help method to check all cases when update money for given hand.
	private int checkAndUpdateResultForHand(Hand hand, int dealerSum, boolean dealerFail) {
		
		int sumOfHandCards = hand.getSumOfPlayingCards();
		boolean blackJackHand = hand.hasBlackJack();
		
		// TODO: Check all cases and update
		if(hand.getSumOfPlayingCards() > 21) {
			// If busted
			return 0;
		}
		else if(dealerFail == true) {
			// Dealer Fail or hand has more than dealer
			return 2*hand.getBetMoney();
		}
		else if(dealer.getDealerHand().hasBlackJack() == true && blackJackHand == false) {
			// If dealer has blackjack and current hand didn't
			return 0;
		}
		
		else if(dealer.getDealerHand().hasBlackJack() == true && blackJackHand == true) {
			// Draw - when both had blackJack
			return hand.getBetMoney();
		}
		
		else if(blackJackHand == true && dealer.getDealerHand().hasBlackJack() == false) {
			// Hand has blackjack and dealer didn't
			return (int) 1.5 * hand.getBetMoney();
		}
		else if(hand.getSumOfPlayingCards() > dealerSum) {
			// Dealer has no black jack and hand no. check who higher
			return 2*hand.getBetMoney();
		}
		else if (hand.getSumOfPlayingCards() == dealerSum){
			// Draw
			return hand.getBetMoney();
		}
		else {
			// Loosing
			return 0;
		}
	}
	
	// Round Routine
	public void startRound() {
		
		// Start the bet Phase and wait for bettings
		this.startBettingPhase();
		// Deal Cards
		this.dealCards();
		// Players are playing
		this.playersTurn();
		// Dealer Turn
		this.turnOfDealer();
		// Update money when dealer show cards
		this.updateMoneyOfPlayers();
		// finish round, update no one playing
		this.finishRound();
	}

}
