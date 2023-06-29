package blackJackApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import blackJackApp.LobbyController.*;

public class LobbyWindow {

	// Data Members
	private Player thePlayer;
	private JFrame frame;
    private JPanel panel;
    private JLabel playerNameField;

    public LobbyWindow(Player newPlayer, TableController nTcOneHundred,TableController nTcFifty,TableController nTcFive) {
    	
    	 this.thePlayer = newPlayer;
    	 this.createAndShowGUI();
    }
      
       
    public void createAndShowGUI() {
        frame = new JFrame("Lobby Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the panel with a BorderLayout
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon backgroundIcon = new ImageIcon("resources/table/LobbyWindow.png");
                Image image = backgroundIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());

        // Create the player name text field
        playerNameField = new JLabel(this.thePlayer.getPlayerName());
        playerNameField.setPreferredSize(new Dimension(200, 30));

//        // Create the buttons
//        JButton table100Button = createTableButton("100$ Table");
//        JButton table50Button = createTableButton("50$ Table");
//        JButton table5Button = createTableButton("5$ Table");
//        JButton addMoneyButton = createActionButton("Add Money");

        // Add components to the panel
        panel.add(createPlayerNamePanel(), BorderLayout.NORTH);
        panel.add(createTableButtonsPanel(), BorderLayout.CENTER);
        panel.add(createActionButtonPanel(), BorderLayout.SOUTH);

        // Set the panel as the content pane of the frame
        frame.setContentPane(panel);
        // Set the size of the window
        frame.setSize(800, 600); // Set the width to 800 pixels and height to 600 pixels

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private JPanel createPlayerNamePanel() {
        JPanel playerNamePanel = new JPanel();
        playerNamePanel.setOpaque(false);
        playerNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerNamePanel.add(new JLabel("Player Name:"));
        playerNamePanel.add(playerNameField);
        return playerNamePanel;
    }

    private JPanel createTableButtonsPanel() {
        JPanel tableButtonsPanel = new JPanel();
        tableButtonsPanel.setOpaque(false);
        tableButtonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        tableButtonsPanel.add(createTableButton("100$ Table"));
        tableButtonsPanel.add(createTableButton("50$ Table"));
        tableButtonsPanel.add(createTableButton("5$ Table"));
        return tableButtonsPanel;
    }

    private JButton createTableButton(String buttonText ) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click event
                // TODO: Add your logic here
//                moveToTable(toTable);
            }
        });
        return button;
    }

    private JPanel createActionButtonPanel() {
        JPanel actionButtonPanel = new JPanel();
        actionButtonPanel.setOpaque(false);
        actionButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        actionButtonPanel.add(createActionButton("Add Money"));
        return actionButtonPanel;
    }

    private JButton createActionButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(59, 89, 182));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click event
                // TODO: Add your logic here
            }
        });
        return button;
    }
	
}
