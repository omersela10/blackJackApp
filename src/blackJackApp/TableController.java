package blackJackApp;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
// Controller ->
public class TableController{
	
	private Table table;
	protected List<TableWindow> obsrevers;
	
	public TableController(Table anyTable, List<TableWindow> tableGUIS) {
		
        this.table = anyTable;
        this.obsrevers = new ArrayList<TableWindow>();
        
        if(tableGUIS == null) {
        	return;
        
        }
        
        for(TableWindow anyWindow :  tableGUIS) {
	       
        	addWindow(anyWindow);
        }
    }
	
	// Add action listeners to the buttons in the TableGUI
	public void addWindow(TableWindow anyWindow) {
		
		this.obsrevers.add(anyWindow);
	   	anyWindow.addSeatButtonListener(new SeatButtonListener(anyWindow));
    	anyWindow.addBetButtonListener(new BetButtonListener(anyWindow));
    	anyWindow.addHitButtonListener(new HitButtonListener(anyWindow));
    	anyWindow.addStandButtonListener(new StandButtonListener(anyWindow));
    	anyWindow.addSplitButtonListener(new SplitButtonListener(anyWindow));
    	anyWindow.addDoubleButtonListener(new DoubleButtonListener(anyWindow));
    	anyWindow.addSurrenderButtonListener(new SurrenderButtonListener(anyWindow));
	}
	

	// Listener for the Bet button
    public class BetButtonListener implements ActionListener {
    	
    	TableWindow theWindow;
    	
    	public BetButtonListener(TableWindow theWidnow) {
    		
    		this.theWindow = theWidnow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
           // Get the bet amount from the TableGUI
            Player currentPlayer = theWindow.getPlayer(); // Get the current player from the Table model
            if(currentPlayer.seatIndex == -1) {
            	notifyToSpecificWindow("Please seat first", currentPlayer);
            	return;
            }
            table.placeBet(currentPlayer); // Place the bet in the Table model
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }

    // Listener for the Hit button
    public class HitButtonListener implements ActionListener {
    	
    	TableWindow theWindow;
    	
    	public HitButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            String message = table.hit(currentPlayer); // Player hits in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }

    // Listener for the Stand button
    public class StandButtonListener implements ActionListener {
    	
    	TableWindow theWindow;
    	
    	public StandButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            String message = table.stand(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }
    // Listener for the Split button
    public class SplitButtonListener implements ActionListener {
    	
    	TableWindow theWindow;
    	
    	public SplitButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            
            String message = table.split(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }
    // Listener for the Double button
    public class DoubleButtonListener implements ActionListener {
    	
    	TableWindow theWindow;
    	
    	public DoubleButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
      
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            String message = table.doubleDown(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }
    // Listener for the Surrender button
    public class SurrenderButtonListener implements ActionListener {
    	
    	TableWindow theWindow;
    	
    	public SurrenderButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            
            String message = table.surrender(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }
    // Listener for the Seat button
    public class SeatButtonListener implements ActionListener {
    	
    	TableWindow theWindow;
    	private boolean isSeat = false;
    	PlayerComponent playerComponent;
    	public SeatButtonListener(TableWindow theWindow) {
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
            Player currentPlayer = theWindow.getPlayer(); // Get the current player from the Table model
            String message = "";
            
            
            if(this.isSeat == false) {
            	this.isSeat = true;
            	playerComponent   = new PlayerComponent(theWindow.theLayeredPane, theWindow.getPlayer().seatIndex);

            	subscribePlayerComponent(playerComponent);
            	message = table.addPlayer(currentPlayer);
            	
            }
            else {
            	this.isSeat = false;
            	unSubscribePlayerComponent(playerComponent);
            	message = table.removePlayer(currentPlayer);
            }
            
            playerComponent.setSeat(theWindow.getPlayer().seatIndex);
            theWindow.updateMessage(message);
            
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }
 
    // Notify all players of the game state change
    private void notifyPlayersOfGameStateChange() {
    	
    	 for(TableWindow anyWindow : obsrevers) {
    		
			 anyWindow.onPropertyChanged(this.table);
			 
		 }
    }
	
	public void startGame()  {
	
		updateDealerLabel();
		this.table.afterBetting();
		
		if(this.table.anyPlayerBet() == true){
			this.createDealerComponent();
			System.out.println("AFTer betting");
			this.updateDealerComponent();
			System.out.println("AFTer update desler");
		}
		
		
	 
        Thread playerThread = new Thread(() -> {
        	table.playersTurn();
            System.out.println("After player's turn");
        });

        playerThread.start();

        // Wait for the player's turn to complete before proceeding with the dealer's turn
        try {
        	// Join
			playerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(table.anyPlayerAlive() == true) {
        	table.turnOfDealer();
        }
		
		System.out.println("AFTer deakers turn");
		
		table.updateMoneyOfPlayers();
	
		table.finishRound();
		
		try {
	        	// Sleep for 4 seconds
				Thread.sleep(4000);
			} 
		  catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		this.cleanAllTables();
	}
	
	private void updateDealerLabel() {
		for(TableWindow tableWindow : this.obsrevers) {
			tableWindow.updateDealerLabel(this.table);
		}
		
	}

	private void createDealerComponent() {
		
		for(TableWindow tableWindow : this.obsrevers) {
			tableWindow.createNewDealerComponent();
		}
		
	}

	private void cleanAllTables() {
		
	
		// Clean player components
		for(TableWindow windowTable : this.obsrevers) {
			for(PlayerComponent anyComponent : windowTable.playersComponent) {
				windowTable.clearPlayerComponent(anyComponent);
			}
		}
		  try {
	        	// Sleep for 1 second
				Thread.sleep(1000);
			} 
		  catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		// Clean Dealer component
		for(TableWindow windowTable : this.obsrevers) {
			windowTable.removeDealerComponent();
		}
		try {
	        	// Sleep for 1 second
				Thread.sleep(1000);
			} 
		  catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		this.notifyToTimerLabel("Waiting for bet");
	}

	public void updateDealerComponent() {
	
		for(TableWindow anyWindow : obsrevers) {
			 anyWindow.updateDealerComponent(this.table.dealer);
		 }

		
		 
	}
	// Notify message to all windows
	protected void notifyMessageViaController(String message) {
    	
    	for(TableWindow tableWindow : this.obsrevers) {
			 tableWindow.updateMessage(message);
		 }
    }
	// Notify message to specific window
	protected void notifyToSpecificWindow(String message, Player anyPlayer) {
    	
    	for(TableWindow tableWindow : this.obsrevers) {
    		if(tableWindow.getPlayer() == anyPlayer) {
			  tableWindow.updateMessage(message);
    		}
		 }
    }
	// Notify message to Timer label
    protected void notifyToTimerLabel(String message) {
		
		for(TableWindow tableWindow : this.obsrevers) {
    		tableWindow.timerLabel.setText(message);
    	}
		
	}
    
    // Update player label
    protected void updatePlayerLabel(Player anyPlayer) {
		
		for(TableWindow tableWindow : this.obsrevers) {
    		if(tableWindow.getPlayer() == anyPlayer) {
			  tableWindow.updatePlayerLabel();
    		}
		 }
	}

    public void subscribePlayerComponent(PlayerComponent newPlayerComponent) {
    	
    	for(TableWindow tableWindow : this.obsrevers) {
    		
    	
	    	tableWindow.playersComponent.add(newPlayerComponent);
	
    		tableWindow.theLayeredPane.add(newPlayerComponent, new Integer(1));
    		tableWindow.theLayeredPane.repaint();
    		System.out.println("added:" + newPlayerComponent.seatIndex);
    	}
    	
    }
    public void unSubscribePlayerComponent(PlayerComponent anyPlayerComponent) {
    	
    	for(TableWindow tableWindow : this.obsrevers) {
 
    			tableWindow.playersComponent.remove(anyPlayerComponent);
    			tableWindow.theLayeredPane.remove(anyPlayerComponent);
  
    		System.out.println("removed:" + anyPlayerComponent.seatIndex);
    	}
    	  
    }


}
