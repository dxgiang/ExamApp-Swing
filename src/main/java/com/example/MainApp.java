package main.java.com.example;

import main.java.com.example.ui.SwingApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.UIManager;

public class MainApp {
    private static final String LOG_DIRECTORY = "data/";
    private static final String SINGLE_LOG_FILE_NAME = "app_log.txt";
    
    public static void main(String[] args) {
        SwingApp.originalOut = System.out;
        SwingApp.originalErr = System.err;
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        try {
            File logDir = new File(LOG_DIRECTORY);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            String logFilePath = LOG_DIRECTORY + File.separator + SINGLE_LOG_FILE_NAME;

            PrintStream fileOut = new PrintStream(new FileOutputStream(logFilePath, true));
            fileOut.printf("\n\n--- Start New Session: %s ---\n", timestamp);
            System.setOut(fileOut);
            System.setErr(fileOut);
            
            System.out.println(timestamp + " Redirected console output to " + logFilePath);
            
        } catch (Exception e) {
            System.err.println("Error redirecting console output to file: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("The Window look and feel do not support on your OS");
        }
        
        SwingApp.main(args);
    }
    public static String getLogFilePath() {
        return LOG_DIRECTORY + File.separator + SINGLE_LOG_FILE_NAME;
    }
}