package blackJackApp;


import java.util.*;

import javax.swing.SwingUtilities;


public class MainClass {
	
	   public static void main(String[] args) {

		   SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	new MainWindow();
	            }
	        });
		}
}
