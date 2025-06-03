package main.java.com.example;

import main.java.com.example.ui.SwingLogin; 
import javax.swing.UIManager;

public class MainApp {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new SwingLogin().setVisible(true);
	}
}
