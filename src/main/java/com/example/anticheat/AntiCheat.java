package main.java.com.example.anticheat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import static java.awt.Toolkit.getDefaultToolkit;

import javax.swing.JOptionPane;

import main.java.com.example.exam.ExamTest;

public class AntiCheat implements WindowFocusListener, KeyListener {
    private ExamTest examTest;
    private boolean windowsPressed = false;
    private boolean escapePressed = false;
    private boolean prtScrPressed = false;
    private volatile boolean cheatingApplied = false;

    public AntiCheat(ExamTest examTest) {
        this.examTest = examTest;
    }

    public boolean checkSize() {
        return examTest.getWidth() != getDefaultToolkit().getScreenSize().width ||
                examTest.getHeight() != getDefaultToolkit().getScreenSize().height;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (examTest.getStatus() == null && !cheatingApplied) {
            if (e.getKeyCode() == KeyEvent.VK_WINDOWS) {
                windowsPressed = true;
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
            } else if (e.getKeyCode() == KeyEvent.VK_PRINTSCREEN) {
                prtScrPressed = true;
                enforceCheatPenalty("Print Screen key detected");
            }
        }
        if (windowsPressed == true) {
            enforceCheatPenalty("Windows key detected");
        } else if (escapePressed == true) {
            enforceCheatPenalty("ESC key detected");
        } else if (prtScrPressed == true) {
            enforceCheatPenalty("Print Screen key detected");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (examTest.getStatus() == null) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_WINDOWS) {
                windowsPressed = false;
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                escapePressed = false;
            } else if (keyCode == KeyEvent.VK_PRINTSCREEN) {
                prtScrPressed = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        if (examTest.getStatus() == null && !cheatingApplied) {
            if (windowsPressed) {
                enforceCheatPenalty("Windows key detected");
            } else if (escapePressed) {
                enforceCheatPenalty("ESC key detected");
            } else if (prtScrPressed) {
                enforceCheatPenalty("Print Screen key detected");
            } else {
                enforceCheatPenalty("Window lost focus(Alt + Tab, Minimize or Click outside)");
            }
        }
    }

    private void enforceCheatPenalty(String reason) {
        if (examTest.getStatus() == null) { // Double check status
            JOptionPane.showMessageDialog(examTest, "You're caught cheating on the exam! Reason: " + reason);
            System.out.println(
                    examTest.upTime() + " " + examTest.getTitle() + " CHEAT ON THE EXAM!! Reason: " + reason);
            System.out.println(examTest.upTime() + " " + examTest.getTitle() + " (Log out)");

            // Set score to 0 and status to CHEAT
            examTest.setScore(0);
            examTest.setStatus("CHEAT");

            // Close the application
            examTest.dispose();
        }
    }
}
