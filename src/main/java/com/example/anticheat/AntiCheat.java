package main.java.com.example.anticheat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import static java.awt.Toolkit.getDefaultToolkit;

import javax.swing.JOptionPane;

import main.java.com.example.exam.ExamTestLogic;
import main.java.com.example.ui.ExamTestUI;

public class AntiCheat implements WindowFocusListener, KeyListener {
    private ExamTestLogic examTestLogic;
    private ExamTestUI examTestUI;
    private boolean windowsPressed = false;
    private boolean escapePressed = false;
    private boolean prtScrPressed = false;
    private volatile boolean cheatingApplied = false;

    public AntiCheat(ExamTestUI examTestUI, ExamTestLogic examTestLogic) {
        this.examTestUI = examTestUI;
        this.examTestLogic = examTestLogic;
    }

    public boolean checkSize() {
        return examTestUI.getWidth() != getDefaultToolkit().getScreenSize().width ||
                examTestUI.getHeight() != getDefaultToolkit().getScreenSize().height;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (examTestLogic.getStatus() == null && !cheatingApplied && !examTestUI.isCompleted()) { 
            int keyCode = e.getKeyCode();
            
            if (keyCode == KeyEvent.VK_WINDOWS) {
                windowsPressed = true;
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
            } else if (keyCode == KeyEvent.VK_PRINTSCREEN) {
                prtScrPressed = true;
            }

            if (windowsPressed) {
                enforceCheatPenalty("Windows key detected");
            } else if (escapePressed) {
                enforceCheatPenalty("ESC key detected");
            } else if (prtScrPressed) {
                enforceCheatPenalty("Print Screen key detected");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (examTestLogic.getStatus() == null && !cheatingApplied && !examTestUI.isCompleted()) { 
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
        if (examTestLogic.getStatus() == null && !cheatingApplied && !examTestUI.isCompleted()) {
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
        if (examTestLogic.getStatus() == null && !cheatingApplied && !examTestUI.isCompleted()) { // Double check status
            cheatingApplied = true;
            JOptionPane.showMessageDialog(examTestUI, "You're caught cheating on the exam! Reason: " + reason);
            System.out.println(
                    examTestLogic.upTime() + " " + examTestUI.getTitle() + " CHEAT ON THE EXAM!! Reason: " + reason);
            System.out.println(examTestLogic.upTime() + " " + examTestUI.getTitle() + " (Log out)");
            examTestLogic.setScore(0);
            examTestLogic.setStatus("CHEAT");
            examTestUI.dispose();
        }
    }
}
