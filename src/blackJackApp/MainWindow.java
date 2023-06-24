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
        mainPanel.setLayout(null); // Set layout manager to null for manual positioning
        add(mainPanel);

        // Create buttons for sign up, log in, play as guest, and leaderboard
        JButton signUpButton = new JButton("Sign up");
        JButton logInButton = new JButton("Log in");
        JButton playAsGuestButton = new JButton("Play as guest");
        JButton leaderboardButton = new JButton("Leader board");

        // Set the location of the buttons using setBounds
        int baffer = 380;
        signUpButton.setBounds(baffer, 500, 120, 30);
        logInButton.setBounds(baffer+150, 500, 120, 30);
        playAsGuestButton.setBounds(baffer+300, 500, 120, 30);
        leaderboardButton.setBounds(50, 700, 120, 30);

        // Add buttons to the mainPanel
        mainPanel.add(signUpButton);
        mainPanel.add(logInButton);
        mainPanel.add(playAsGuestButton);
        mainPanel.add(leaderboardButton);

        
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
		SignUpWindow newSignUpWindow = new SignUpWindow();
	}

	private void logIn() {
		LogInWindow newLogInWindow = new LogInWindow();
	}

	private void playAsGuest() {
		PlayAsGuestWindow newPlayAsGuestWindow = new PlayAsGuestWindow();
	}

	private void leaderboard() {
		LeaderboardWindow newLeaderboardWindow = LeaderboardWindow.getInstance();
	}
}
