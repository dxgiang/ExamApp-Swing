package main.java.com.example;

import main.java.com.example.ui.SwingLogin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.UIManager;

public class MainApp {
	private static final String LOG_DIRECTORY = "src/main/logs/";
	private static final String SINGLE_LOG_FILE_NAME = "app_log.txt";
	public static void main(String[] args) {
		try {
            File logDir = new File(LOG_DIRECTORY);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            String logFilePath = LOG_DIRECTORY + File.separator + SINGLE_LOG_FILE_NAME;

            PrintStream fileOut = new PrintStream(new FileOutputStream(logFilePath, true));
            System.setOut(fileOut);
            System.setErr(fileOut);

        } catch (Exception e) {
            System.err.println("Error redirecting console output to file: " + e.getMessage());
            e.printStackTrace();
        }

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new SwingLogin().setVisible(true);
	}
}
