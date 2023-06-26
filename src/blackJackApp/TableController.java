package blackJackApp;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
// Controller ->
public class TableController {
	
	private Table table;
	protected List<TableWindow> allWindows;
	
	public TableController(Table anyTable, List<TableWindow> tableGUIS) {
		
        this.table = anyTable;
        this.allWindows = tableGUIS;
        if(this.allWindows == null) {
        	return;
        }
        for(TableWindow anyWindow :  this.allWindows) {
	        // Add action listeners to the buttons in the TableGUI
        	anyWindow.addSeatButtonListener(new SeatButtonListener(anyWindow));
        	anyWindow.addBetButtonListener(new BetButtonListener(anyWindow));
        	anyWindow.addHitButtonListener(new HitButtonListener(anyWindow));
        	anyWindow.addStandButtonListener(new StandButtonListener(anyWindow));
        	anyWindow.addSplitButtonListener(new SplitButtonListener(anyWindow));
        	anyWindow.addDoubleButtonListener(new DoubleButtonListener(anyWindow));
        	anyWindow.addSurrenderButtonListener(new SurrenderButtonListener(anyWindow));
        }
    }
	
	public void addWindow(TableWindow anyWindow) {
		
		this.allWindows.add(anyWindow);
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
    	public SeatButtonListener(TableWindow theWindow) {
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
            Player currentPlayer = theWindow.getPlayer(); // Get the current player from the Table model
            String message = "";
            
            if(this.isSeat == false) {
            	this.isSeat = true;
            	message = table.addPlayer(currentPlayer); 
            }
            else {
            	this.isSeat = false;
            	message = table.removePlayer(currentPlayer);
            }
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange();
        }
    }
 
    // Notify all players of the game state change
    private void notifyPlayersOfGameStateChange() {
    	
    	 for(TableWindow anyWindow : allWindows) {
			 anyWindow.updateTableComponent(table);
		 }
    }
	
	public void startGame()  {
	
		
		this.table.afterBetting();
		System.out.println("AFTer betting");
		this.updateDealerComponent();
		System.out.println("AFTer update desler");
		table.playersTurn();
		System.out.println("AFTer players turn");
	
		table.turnOfDealer();
		System.out.println("AFTer deakers turn");
		
		table.updateMoneyOfPlayers();
	
		table.finishRound();
	}
	
	public void updateDealerComponent() {
		
		    try {
				SwingUtilities.invokeAndWait(() -> {
					for(TableWindow anyWindow : allWindows) {
						 anyWindow.updateDealerComponent(table.dealer);
					 }
				});
			} catch (InvocationTargetException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 
	}
	protected void notifyMessageViaController(String message) {
    	
    	for(TableWindow tableWindow : this.allWindows) {
			 tableWindow.updateMessage(message);
		 }
    }
    
	protected void notifyToSpecificWindow(String message, Player anyPlayer) {
    	
    	for(TableWindow tableWindow : this.allWindows) {
    		if(tableWindow.getPlayer() == anyPlayer) {
			  tableWindow.updateMessage(message);
    		}
		 }
    }
    
    protected void notifyToTimerLabel(String message) {
		
		for(TableWindow tableWindow : this.allWindows) {
    		tableWindow.timerLabel.setText(message);
    	}
		
	}
    protected void updatePlayerLabel(Player anyPlayer) {
		
		for(TableWindow tableWindow : this.allWindows) {
    		if(tableWindow.getPlayer() == anyPlayer) {
			  tableWindow.updatePlayerLabel();
    		}
		 }
	}
}
