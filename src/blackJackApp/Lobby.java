package blackJackApp;

import java.util.*;

public abstract class Lobby {

    //Data Members
    protected List<Table> tables;
    protected Player thePlayer;
    protected int balance;
    public LobbyController theLobbyController;

    //Constructor
    public Lobby(Player newPlayer) {
        this.thePlayer = newPlayer;
        this.balance = newPlayer.getTotalMoney();
        this.tables = new ArrayList<Table>();
        initializeTables();
    }

    //initialize Tables List
    private void initializeTables() {
        this.tables.add(OneHundredDollarTable.getInstance());
        this.tables.add(FiveDollarTable.getInstance());
        this.tables.add(FiftyDollarTable.getInstance());
    }
    
    //Getters
    public List<Table> getTables() {
        return tables;
    }
    
    public Player getPlayer() {
        return thePlayer;
    }
    
    public int getBalance() {
        return balance;
    }
    
    //Setters
    public void SetPlayer(Player newPlayer) {
        this.thePlayer = newPlayer;
    }
    
    public void setBalance(int newBalance) {
        this.balance = newBalance;
    }




}
