package blackJackApp;

import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame {
    private static String backgroundIcon = "resources/mainWindow/blackJackMain.png";

    public MainWindow() {
        setTitle("Blackjack main menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                ImageIcon icon = new ImageIcon(backgroundIcon);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Create buttons for sign up, log in, and play as guest
        JButton signUpButton = new JButton("Sign up");
        JButton logInButton = new JButton("Log in");
        JButton playAsGuestButton = new JButton("Play as guest");
        JButton leaderboardButton = new JButton("Leader board");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(signUpButton);
        buttonPanel.add(logInButton);
        buttonPanel.add(playAsGuestButton);
       
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
     // Add ActionListener to signUpButton
        signUpButton.addActionListener(e -> {
            signUp(); // Call your desired function for sign up
        });

        // Add ActionListener to logInButton
        logInButton.addActionListener(e -> {
            logIn(); // Call your desired function for log in
        });

        // Add ActionListener to playAsGuestButton
        playAsGuestButton.addActionListener(e -> {
            playAsGuest(); // Call your desired function for playing as guest
        });

        // Add ActionListener to leaderboardButton
        leaderboardButton.addActionListener(e -> {
        	leaderboard(); // Call your desired function for displaying leaderboard
        });

        pack();
        setVisible(true);
    }

	private void signUp() {
		
		System.out.println("signUp");
		SignUpWindow newSignUpWindow = new SignUpWindow();
	}

	private void logIn() {
		// TODO Auto-generated method stub
		System.out.println("logIn");
		LogInWindow newLogInWindow = new LogInWindow();
	}

	private void playAsGuest() {
		// TODO Auto-generated method stub
		System.out.println("playAsGuest");
		PlayAsGuestWindow newPlayAsGuestWindow = new PlayAsGuestWindow();
	}

	private void leaderboard() {
		// TODO Auto-generated method stub
		System.out.println("showLeaderboard");
		LeaderboardWindow newLeaderboardWindow = LeaderboardWindow.getInstance();
	}
}
