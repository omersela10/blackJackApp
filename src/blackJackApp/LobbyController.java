package blackJackApp;

import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class LobbyController {

    private Lobby theLobby;
    private LobbyWindow theLobbyWindow;

    public LobbyController(Lobby newLobby) {
        this.theLobby = newLobby;
    }

    public void addWindow(LobbyWindow newWindow) {
        this.theLobbyWindow = newWindow;
        //List<Table> lobbyTables = theLobby.getTables();
    }

    public void moveToTable(Table newTable) {
        //theLobbyWindow.setVisible(false);
        //theLobbyWindow.dispose();
        //theLobbyWindow = null;
        //theLobbyWindow = new TableWindow(theLobby.getPlayer(), newTable);
        //theLobbyWindow.setVisible(true);

        //open new table window
        TableWindow wt = new TableWindow(theLobby.getPlayer());




    }



}
