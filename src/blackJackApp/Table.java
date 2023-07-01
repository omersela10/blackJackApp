package blackJackApp;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class Table{
	
	// Data Members:

	// Sounds:
	private SoundPlayer cardDrawSound = new SoundPlayer("resources/sounds/cardDraw.wav");
	private SoundPlayer chipsSettleSound = new SoundPlayer("resources/sounds/chipsSettle.wav");

	private volatile Timer betTimer;
	private static final int TIMEOUT = 8000;
	private volatile boolean anyPlayerBet = false;
	private volatile boolean anyPlayerSeat = false;
	private volatile boolean anyPlayerAlive = false;
	private static volatile boolean inRound = false;
	private static volatile boolean timeToBet = false; 
	
	private Object betLock = new Object();
	protected static final int MAXIMUMPLAYERS = 4;
	protected List<Player> players;
	protected Dealer dealer;
	protected volatile Player currentTurn = null;

	public TableController tableController = null;
	
	

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
	public boolean anyPlayerAlive() {
		
		return this.anyPlayerAlive;
	}
	public TableController getTableController() {
		return tableController;
	}

	public void setTableController(TableController tableController) {
		this.tableController = tableController;
	}
	
	// Methods:
	
	// Seat Player to Table
	public String addPlayer(Player toAddPlayer) {
		
		String returnMessage =  toAddPlayer.seat(this.players);
		if(returnMessage.contains("Seated") == true) {
			this.anyPlayerSeat = true;
		}
		return returnMessage;
	}
		
	// Leave player from the Table
	public String removePlayer(Player toRemovePlayer) {
	
		String returnMessage = toRemovePlayer.up(players);
		int count = 0;
		
		for(Player anyPlayer : this.players) {
			if(anyPlayer == null) {
				count += 1;
			}
		}
		
		if(count == 0 ) {
			this.anyPlayerSeat = false;
		}
		
		return returnMessage;
		
	}
		

	// Help method for get from player initial bet any create new cards.
	public boolean betPlayer(Player anyPlayer) {
		
		if(anyPlayer.getTotalMoney() < this.getMinimumBet()) {
			 JOptionPane.showMessageDialog(null, "You have no enough money");
			 return false;
		}

		String inputBet = JOptionPane.showInputDialog("Insert money for play");
		if(inputBet == null || inputBet.isBlank() || inputBet.matches("\\d+") == false) {
			JOptionPane.showMessageDialog(null, "Invalid input");
			return false;
		}
		
		int betMoney = Integer.parseInt(inputBet);
		
		if(betMoney > anyPlayer.getTotalMoney() || betMoney < this.getMinimumBet()) {
			 JOptionPane.showMessageDialog(null, "Enter amount between " + this.getMinimumBet() + " - " + anyPlayer.getTotalMoney());
			 return false;
		}
		
		// Start bet
		return anyPlayer.bet(betMoney);
    }
	
	
	public void playRound(Player anyPlayer) {
		
		boolean isWaitingForDealer = true;
		
		do {
			// If player in endRound State
			isWaitingForDealer = anyPlayer.getHandState() instanceof EndHandRoundState;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(isWaitingForDealer == false);

	}
	
	// Game Logic

	public void playersTurn() {
		
		// If there are no players who bets
	
		if(this.anyPlayerBet == false) {	
			this.anyPlayerAlive = false;
		}
	
		for(Player anyPlayer: this.getPlayers()) {
			
			if(playingPlayer(anyPlayer) == true) {
				
				 this.setCurrentTurn(anyPlayer);
				 
				 tableController.notifyMessageViaController("Turn of: " + anyPlayer.getPlayerName());
				 
				 playRound(anyPlayer);
				 
				 for(Hand hand: anyPlayer.getHands()) {
					 
					 boolean anyAlive = hand.getSumOfPlayingCards() <= 21 && hand.getBetMoney() > 0;
					 if(anyAlive == true) {
						 this.anyPlayerAlive = true;
						
					 }
					 
				 }
				 
			}
		}
		
		this.setCurrentTurn(null);
	
	}
	
	public synchronized Player getCurrentTurn() {
		return this.currentTurn;
	}
	
	public synchronized void setCurrentTurn(Player currentTurnPlayer) {
		this.currentTurn = currentTurnPlayer;
	}
	
	public synchronized boolean inRound() {
		return inRound;
	}
	
	public synchronized boolean getTimeToBet() {
		return timeToBet;
	}
	
	// Start Betting Phase.
	public void startBettingPhase(Player anyPlayer) {
		
	    if (inRound() == true || anyPlayer.isPlay() == true) {
		   
		   tableController.notifyToSpecificWindow("Can't bet now", anyPlayer);
            return;
        }
	
	    synchronized (betLock) {
	    	
	     

	        if (getTimeToBet() == false) {
	            setTimeToBet(true);
	            betTimer = new Timer();

		        betTimer.schedule(new TimerTask() {
		        	
		            private int remainingTime = TIMEOUT / 1000; // Convert the timeout value to seconds

		            @Override
		            public void run() {
		     
		                synchronized (betLock) {
		                    // Update the timer label with the remaining time
		                    tableController.notifyToTimerLabel("Time remaining: " + remainingTime + " seconds");

		                    if (remainingTime == -1) {
		                        stopBettingPhase();
		                        tableController.notifyToTimerLabel("No more bet");

		                        setInRound(true);
		                        setTimeToBet(false);
		                        tableController.startGame();
		                  
		                    }
		                    remainingTime--;
		                    
		                }
		            }
		        }, TIMEOUT / 1000, 1000);

	        } 

	        
	        betPlayer(anyPlayer);
	        

	       
	    }
	}
	 
	

// After round is over, no one is playing until next timeout.
   public void finishRound() {
	   
		for(Player player: this.getPlayers()) {
			if(player != null) {
				player.isPlay = false;
				player.setHands(null);
				
			}
		}
		this.setInRound(false);
		this.anyPlayerAlive = false;
	}

   public void stopBettingPhase() {
	  betTimer.cancel();
   }

   // After bet method
    public void afterBetting() {
    	
       dealer.setDealerHand(new Hand(0));
       
	   for(Player anyPlayer: this.getPlayers()) {
		   
		   if(playingPlayer(anyPlayer) == true) {
			   anyPlayerBet = true;
			   this.tableController.updatePlayerLabel(anyPlayer);
		   }
	   }
	   
    }
  
    
    public void turnOfDealer() {

    	this.dealer.setDealerTurn(true);
    	notifyControllerOnDealerChanged();
    	
    	while(this.dealer.getSumOfDealerCards() < 17) {
    		
    		dealer.getDealerHand().getMoreCard();
		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		notifyControllerOnDealerChanged();
    		cardDrawSound.play();
    	
    	}

    	this.dealer.setDealerTurn(false);
    	notifyControllerOnDealerChanged();

    }

    // Update money when dealer finish
	public void updateMoneyOfPlayers() {
		
		int dealerSum = this.dealer.getSumOfDealerCards();
		boolean dealerFail = dealerSum > 21;
		
		for(Player player : this.players) {
			if(playingPlayer(player) == true) {
				
				int profit = this.checkAndUpdateResultForPlayer(player, dealerSum, dealerFail);
				int sumOfTotalBetOnHand = 0;
				
				for(Hand hand : player.getHands()) {
					sumOfTotalBetOnHand += hand.getBetMoney();
				}
				
				int difference = profit - sumOfTotalBetOnHand;
				
				if( difference > 0) {
					// Win
					updateWinsInDB(player);
					tableController.notifyToSpecificWindow("You win " + difference + "$", player);
			
				}
				
				else {
					// Lose or draw
					tableController.notifyToSpecificWindow("You lose " + (-1 * difference) + "$", player);
				}
				
				
				updaeMoneyInDB(player, profit);
				tableController.updatePlayerLabel(player);
			}
			
		}
		 
		
	}

	// Update more win in DB
	private void updateWinsInDB(Player player) {
		// Update he win in user object
		if (player instanceof UserPlayer) {
			
			User user = ((UserPlayer)player).getUser();
			
			// Update user number of wins
			user.addOneToNumberOfWins();
			
		}
		
	}

	// Update money in DB
	public void updaeMoneyInDB(Player anyPlayer, int profit) {
		// Update DB if it is user 
		if (anyPlayer instanceof UserPlayer) {
			
			User user = ((UserPlayer)anyPlayer).getUser();
			
			// Update += difference in user object
			user.addToTotalProfit(profit);
			
			DBManager.updateUserValues(user);
		}
	}

	
	

	// Help method for update money
	private int checkAndUpdateResultForPlayer(Player player, int dealerSum, boolean dealerFail) {

		int totalWinMoney = 0;
		for(Hand hand: player.getHands()) {
			
			int winPrize = checkAndUpdateResultForHand(hand, dealerSum, dealerFail);
			// Update money
			player.setTotalMoney(player.getTotalMoney() + winPrize);
			totalWinMoney += winPrize;
			
		}
		
		return totalWinMoney;
	
	}
	
	// Help method to check all cases when update money for given hand.
	private int checkAndUpdateResultForHand(Hand hand, int dealerSum, boolean dealerFail) {
		
		int sumOfHandCards = hand.getSumOfPlayingCards();
		boolean blackJackHand = hand.hasBlackJack();
		
		if(sumOfHandCards > 21) {
			// If busted
			return 0;
		}
		else if(blackJackHand == true && dealer.getDealerHand().hasBlackJack() == false) {
			// If Black Jack and Dealer has'nt
			 return (int) (2.5 * hand.getBetMoney());
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
	
	public synchronized void setInRound(boolean value) {
		inRound = value;
	}
	public synchronized void setTimeToBet(boolean value) {
		timeToBet = value;
	}

	public String hit(Player currentPlayer) {
		
		String message = currentPlayer.hit();
		
		if(message.indexOf("Can't") == -1) {
			cardDrawSound.play();
		}
		
		return message;
		
	}

	public String stand(Player currentPlayer) {
		
		return currentPlayer.stand();
		
	}

	public String split(Player currentPlayer) {
	
		String message = currentPlayer.split();
		
		if(message.indexOf("Can't") == -1) {
			cardDrawSound.play();
		}
		
		return message;

	}

	public String doubleDown(Player currentPlayer) {
		
		String message = currentPlayer.doubleDown();
		
		if(message.indexOf("Can't") == -1) {
			cardDrawSound.play();
		}
		
		return message;

		
	}
	public String surrender(Player currentPlayer) {
		return currentPlayer.surrender();
		
	}

	public Player getCurrentPlayer() {
		return this.getCurrentTurn();
	}

	public void placeBet(Player currentPlayer) {
		
		this.startBettingPhase(currentPlayer);

		chipsSettleSound.play();
	}
	 // Method to notify the controller of a game state change
    private void notifyControllerOnDealerChanged() {
       tableController.updateDealerComponent();
    }


    private TableWindow getWindowFromController(Player anyPlayer) {
    	
    	for(TableWindow tableWindow : this.tableController.obsrevers) {
    		
    		if(tableWindow.getPlayer() == anyPlayer) {
    			return tableWindow;
    		}
    	}
    	return null;
    }
    
    

}
