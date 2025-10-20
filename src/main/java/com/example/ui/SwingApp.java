package main.java.com.example.ui;

import javax.swing.UIManager;
import main.java.com.example.auth.DataProcess;
import main.java.com.example.controller.SwingAppController;

public class SwingApp {
    public static java.io.PrintStream originalOut = System.out;
    public static java.io.PrintStream originalErr = System.err;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        DataProcess loginSystem = new DataProcess();
        SwingAppUI ui = new SwingAppUI(loginSystem);
        new SwingAppController(ui, loginSystem);
        ui.setVisible(true);
    }
}