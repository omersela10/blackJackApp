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

public class LobbyWindow extends JFrame{

    // Data Members
    private final Player thePlayer;
    private JLabel playerNameField;
    private final TableController tcOneHundred;
    private final TableController tcFifty;
    private final TableController tcFive;
	private JPanel panel;

    private static final String TABLESTRING100 = "100$ Table";
    private static final String TABLESTRING50 = "50$ Table";
    private static final String TABLESTRING5 = "5$ Table";
    
    public LobbyWindow(Player newPlayer, TableController nTcOneHundred,TableController nTcFifty,TableController nTcFive) {
    	
        this.thePlayer = newPlayer;
        this.tcOneHundred = nTcOneHundred;
        this.tcFifty = nTcFifty;
        this.tcFive = nTcFive;
        this.createAndShowGUI();
        
        // Update User is connected in DB
        updateUserConnectInDB();
    }


    // Update user connect in DB
    private void updateUserConnectInDB() {
    	
         if (thePlayer instanceof UserPlayer) {
 			
 			User user = ((UserPlayer)thePlayer).getUser();
 			DBManager.setconnectedToUser(user, true);
 		}
	}


	public void createAndShowGUI() {


        setTitle("Lobby Window");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Create the panel with a BorderLayout
        // Load and draw the background image
        this.panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon backgroundIcon = new ImageIcon("resources/table/LobbyWindow.png");
                Image image = backgroundIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
    
        this.panel.setLayout(new BorderLayout());

        // Create a label for the player's name and total money
        this.playerNameField = new JLabel("Name: " + thePlayer.getPlayerName() + " | Money: " + thePlayer.getTotalMoney() + "$");
        playerNameField.setBounds(20, 20, 500, 20);
        playerNameField.setForeground(Color.GRAY);
        this.panel.add(playerNameField, BorderLayout.NORTH);

        // Add components to the panel
  
        this.panel.add(createTableButtonsPanel(), BorderLayout.CENTER);
        this.panel.add(createActionButtonPanel(), BorderLayout.SOUTH);

        // Set the panel as the content pane of the frame
        this.setContentPane(panel);
        // Set the size of the window
        this.setSize(800, 600); // Set the width to 800 pixels and height to 600 pixels

        // Center the window on the screen
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    private void createPlayerNamePanel() {
    	
    	this.panel.remove(this.playerNameField);
    
    	// Create a label for the player's name and total money
    	 this.playerNameField = new JLabel("Name: " + thePlayer.getPlayerName() + " | Money: " + thePlayer.getTotalMoney() + "$");
    	 this.playerNameField.setBounds(20, 20, 500, 20);
    	 this.playerNameField.setForeground(Color.GRAY);
    	 this.panel.add(this.playerNameField, BorderLayout.NORTH);
 
    }

    private JPanel createTableButtonsPanel() {
    	
        JPanel tableButtonsPanel = new JPanel();
        tableButtonsPanel.setOpaque(false);
        tableButtonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        tableButtonsPanel.add(createTableButton(TABLESTRING100));
        tableButtonsPanel.add(createTableButton(TABLESTRING50));
        tableButtonsPanel.add(createTableButton(TABLESTRING5));
        
        return tableButtonsPanel;
    }

    private JButton createTableButton(String buttonText) {

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
                TableWindow nTw = new TableWindow(thePlayer);
                switch (buttonText) {
                    case TABLESTRING100:
                    	 tcOneHundred.addWindow(nTw);
                    	 tcOneHundred.getTable().setTableController(tcOneHundred);
                    	 break;
                    case TABLESTRING50:
                    	tcFifty.addWindow(nTw);
                    	tcFifty.getTable().setTableController(tcFifty);
                    	break;
                    case TABLESTRING5:
                    	tcFive.addWindow(nTw);
                    	tcFive.getTable().setTableController(tcFive);
                    	break;
                }
                setVisible(false);
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
                if(thePlayer instanceof UserPlayer) {
                	addMoneyDialog();
                
                }
                else {
                	JOptionPane.showMessageDialog(null, "Guest players can not add money :(");
                
                }
            }
        });
        return button;
    }
    public void addMoneyDialog() {
    	
        // Create a panel to hold the sign-up form components
        JPanel addMoneyPanel = new JPanel();
        addMoneyPanel.setLayout(new BoxLayout(addMoneyPanel, BoxLayout.Y_AXIS));

        // Create labels and text fields for name and password
        JLabel amountLabel = new JLabel("Amount to add:");
        JTextField amountTextField = new JTextField(20);


        // Add the components to the sign-up panel
        addMoneyPanel.add(amountLabel);
        addMoneyPanel.add(amountTextField);

        // Show the sign-up panel and handle the button press
        int result = JOptionPane.showConfirmDialog(this, addMoneyPanel, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If the OK button is pressed
        if (result == JOptionPane.OK_OPTION) {
            // Update player money in DB
        	String amount = amountTextField.getText();
        	boolean isValid = amount.matches("^[1-9]\\d*$");
        	if (isValid == true) {
        		
                thePlayer.setTotalMoney(thePlayer.getTotalMoney() + Integer.parseInt(amount));//Integer.parseInt(amountTextField.getText()));
                DBManager.updateUserValues(((UserPlayer)thePlayer).getUser());
                createPlayerNamePanel();
                panel.revalidate();
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Insert valid number, please");
        	}

        }

    }
}
