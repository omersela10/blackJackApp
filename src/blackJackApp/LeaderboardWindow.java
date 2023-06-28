package blackJackApp;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LeaderboardWindow extends JFrame {
	
    private static LeaderboardWindow instance;
    private JTextArea outputArea;
    private JButton totalWinsButton;
    private JButton totalProfitButton;
    private DBManager usersDB;
    
    // Private constructor to prevent instantiation from outside the class
    private LeaderboardWindow() {
    	
    	usersDB = new DBManager();
    	
    	
    }

    private void printTotalProfitLeaderBoard() {
    	
    	String printStr = "TOTAL PTOFIT LEADER BOARD: \n";
    	
    	ArrayList<Map<String, String>> allUsers = usersDB.sortUsersByProfit();
    	
    	for (int i=0; i<allUsers.size() ; i++) {
    		
    		printStr += ("Name: " + allUsers.get(i).get("Name"));
    		printStr += (", Number of wins: " + allUsers.get(i).get("Number of wins"));
    		printStr += (", Total profit: "+allUsers.get(i).get("Total profit"));
    		printStr += "\n";
    	}
    	
    	outputArea.setText(printStr); 
		
	}

	private void printTotalWinsLeaderBoard() {
		
    	String printStr = "TOTAL WINS LEADER BOARD: \n";
    	
    	ArrayList<Map<String, String>> allUsers = usersDB.sortUsersByWins();
    	
    	for (int i=0; i<allUsers.size() ; i++) {

    		printStr += ("Name: " + allUsers.get(i).get("Name"));
    		printStr += (", Number of wins: " + allUsers.get(i).get("Number of wins"));
    		printStr += (", Total profit: "+ allUsers.get(i).get("Total profit"));
    		printStr += "\n";
    	}
    	
    	outputArea.setText(printStr); 
		
	}
		
	

	// Method to get the instance of the LeaderboardWindow class
    public static LeaderboardWindow getInstance() {
    	
    	if(instance == null) {
    		instance = new LeaderboardWindow();
    	}
    	initialize();
    	
    	return instance;

    }

	private static void initialize() {
	
		instance.setTitle("Leaderboard");
		instance.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		instance.setPreferredSize(new Dimension(800, 600));

        // Create the output area on the right side
		instance.outputArea = new JTextArea();
		instance.outputArea.setEditable(false);
		instance.add(instance.outputArea, BorderLayout.CENTER);

        // Create the buttons on the left side
		instance.totalWinsButton = new JButton("<html>Show total wins<br>leader board</html>");
		instance.totalProfitButton = new JButton("<html>Show total profit<br>leader board</html>");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Adjust the spacing between buttons
        buttonPanel.add(instance.totalWinsButton);
        buttonPanel.add(instance.totalProfitButton);

        instance.add(buttonPanel, BorderLayout.WEST);

        // Add ActionListener to totalWinsButton
        instance.totalWinsButton.addActionListener(e -> {
        	instance.printTotalWinsLeaderBoard(); // Call  desired function for sign up
        });

        // Add ActionListener to totalProfitButton
        instance.totalProfitButton.addActionListener(e -> {
        	instance.printTotalProfitLeaderBoard(); // Call  desired function for log in
        });
        
        instance.pack();
        instance.setVisible(true);
	}
}
