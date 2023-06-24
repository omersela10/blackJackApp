package blackJackApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public abstract class Table{
	
	// Data Members:
	private Timer betTimer;
	private static final int TIMEOUT = 3000;
	private volatile boolean anyPlayerBet = false;
	private static volatile boolean inRound = false;
	private static volatile boolean timeToBet = false; 
	
	protected final int MAXIMUMPLAYERS = 4;
	protected List<Player> players;
	protected Dealer dealer;
	
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
		
		this.dealer = new Dealer(tableMinBet);
		
	}
	
	// Getters
	public List<Player> getPlayers(){
		return this.players;
	}
		
	public boolean anyPlayerBet() {
		return this.anyPlayerBet;
	}
	
	// Methods:
	
	// Seat Player to Table
	public String addPlayer(Player toAddPlayer) {
		
		return toAddPlayer.seat(this.players);
	}
		
	// Leave player from the Table
	public String removePlayer(Player toRemovePlayer) {
	
		return toRemovePlayer.up(players);
		
	}
		

	// Help method for get from player initial bet any create new cards.
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
	
	public void playRound(Player anyPlayer , TableWindow tableWindow) {
		anyPlayer.registerHandStateChangeListener(tableWindow);
		boolean isWaitingForDealer = true;
		
		do {
			// If player in endRound State
			isWaitingForDealer = anyPlayer.getHandState() instanceof EndHandRoundState;
		
		}
		while(isWaitingForDealer == false);

	}
	
	// Game Logic

	public boolean playersTurn(TableWindow tableWindow) {
		
		// If there are no players who bets
		boolean anyAlive = false;
		
		if(anyPlayerBet == false) {	
			return anyAlive;
		}
	
		for(Player anyPlayer: this.getPlayers()) {
			if(playingPlayer(anyPlayer) == true) {
				 tableWindow.updateMessage("Turn of:" + anyPlayer.getPlayerName());
				 playRound(anyPlayer, tableWindow);
				 for(Hand hand: anyPlayer.getHands()) {
					 anyAlive = hand.getSumOfPlayingCards() <= 21;
				 }
				 
			}
		}
		return anyAlive;
	}
	
	public synchronized boolean inRound() {
		return this.inRound;
	}
	public synchronized boolean getTimeToBet() {
		return this.timeToBet;
	}
	// Start Betting Phase.
	 public void startBettingPhase(TableWindow tableWindow, JLabel timerLabel, Player anyPlayer) {
		 
		 if(this.inRound() == true) {
			 return;
		 }
		 
		 if(getTimeToBet() == false) {
			 this.setTimeToBet(true);
		 }
		 else {
			 return;
		 }
		 
		 
		 
		 betTimer = new Timer();
		 betTimer.schedule(new TimerTask() {
		        private int remainingTime = TIMEOUT / 1000; // Convert the timeout value to seconds

		        @Override
		        public void run() {
		        	
		            // Update the timer label with the remaining time
		            timerLabel.setText("Time remaining: " + remainingTime + " seconds");
		            
		            if (remainingTime == -1) {
		            	stopBettingPhase();
		            	timerLabel.setText("No more bet");
		            	setInRound(true);
		            	setTimeToBet(false);
		            	tableWindow.startGame();
		
		            }
		            remainingTime--;
		       	

		           
		        }
		    }, TIMEOUT / 1000, 1000);
		 	betPlayer(anyPlayer);// Set the delay and period of the timer task to 1 second
	}
	 
	// After round is over, no one is playing until next timeout.
   public void finishRound(TableWindow tableWindow) {
	   
		for(Player player: this.getPlayers()) {
			if(player != null) {
				player.isPlay = false;
			}
		}
		this.setInRound(false);
		tableWindow.clearTable();
		
	}

   public void stopBettingPhase() {
	  betTimer.cancel();
   }

    public void afterBetting() {
    	
	   for(Player anyPlayer: this.getPlayers()) {
		   
		   if(anyPlayer.isPlay() == true) {
			   anyPlayerBet = true;
			   return;
		   }
	   }
    }
  
    
    public void turnOfDealer(TableWindow tableWindow) {
    	tableWindow.updateMessage("Turn of Dealer");
    	this.dealer.setDealerTurn(true);
    	this.dealer.registerDealerStateChangeListener(tableWindow);
    	this.dealer.notifyDealerStateChangeListener();
    	
    	try {
			 
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
    	while(this.dealer.getSumOfDealerCards() < 17) {
    		dealer.getDealerHand().getMoreCard();
    		this.dealer.notifyDealerStateChangeListener();
    		try {
    			 
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			
    			e.printStackTrace();
    		}
    		
    	}
    	this.dealer.notifyDealerStateChangeListener();
    	this.dealer.setDealerTurn(false);
    	
    	
    	
    }

    // Update money when dealer finish
	public void updateMoneyOfPlayers(TableWindow tableWindow) {
		
		int dealerSum = this.dealer.getSumOfDealerCards();
		boolean dealerFail = dealerSum > 21;
		
		for(Player player : this.players) {
			if(playingPlayer(player) == true) {
				this.checkAndUpdateResultForPlayer(tableWindow, player, dealerSum, dealerFail);
			}
			
		}
		 
		
	}


	
	// Help method for update money
	private void checkAndUpdateResultForPlayer(TableWindow tableWindow, Player player, int dealerSum, boolean dealerFail) {

		int totalWinMoney = 0;
		for(Hand hand: player.getHands()) {
			
			int winPrize = checkAndUpdateResultForHand(hand, dealerSum, dealerFail);
			// Update money
			player.setTotalMoney(player.getTotalMoney() + winPrize);
			totalWinMoney += winPrize;
			
		}
		// Update money for the given player
	    tableWindow.updatePlayerLabel();
	    tableWindow.updateMessage("You earn: " + totalWinMoney +"$");
		
	
	}
	
	// Help method to check all cases when update money for given hand.
	private int checkAndUpdateResultForHand(Hand hand, int dealerSum, boolean dealerFail) {
		
		int sumOfHandCards = hand.getSumOfPlayingCards();
		boolean blackJackHand = hand.hasBlackJack();
		
		// TODO: Check all cases and update
		if(sumOfHandCards > 21) {
			// If busted
			return 0;
		}
		else if(blackJackHand == true && dealer.getDealerHand().hasBlackJack() == false) {
			// If Black Jack and Dealer has'nt
			 return (int) 2.5 * hand.getBetMoney();
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
	public void startRound(TableWindow tableWindow) {
		
		// Players are playing
		this.playersTurn(tableWindow);
		// Dealer Turn
		this.turnOfDealer(tableWindow);
		
		// Update money when dealer show cards
		this.updateMoneyOfPlayers(tableWindow);
		// TODO: Update DB with the new Money
		// TODO: Update win
		// finish round, update no one playing
		this.finishRound(tableWindow);
	}
	
	public synchronized void setInRound(boolean value) {
		this.inRound = value;
	}
	public synchronized void setTimeToBet(boolean value) {
		this.timeToBet = value;
	}

}
