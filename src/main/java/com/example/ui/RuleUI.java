package main.java.com.example.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class RuleUI extends JFrame{
    public RuleUI() {
        setTitle("Rule");
        setSize(700,220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panelRule = new JPanel();
        JLabel rule = new JLabel();
        rule.setText("<html><body style='width: 570px; padding: 8;'>"
                + "<h2 align='center'>Exam Rules</h2>"
                + "<ul>"
                + "<li>1. Do not minimize or switch to another window during the exam.</li>"
                + "<li>2. Pressing the Windows key, ESC key, PrtSrc key, or Alt + Tab = CHEAT.</li>"
                + "<li>3. Any violation of these rules will result in immediate account lock and exam termination.</li>"
                + "</ul>"
                + "</body></html>");
                rule.setFont(rule.getFont().deriveFont(16f));
        panelRule.add(rule);
        JPanel panelButton = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.GREEN);
        okButton.addActionListener(e -> dispose());
        panelButton.add(okButton);
        getContentPane().add(panelRule, "Center");
        getContentPane().add(panelButton, "South");
        setVisible(true);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("The Window look and feel do not support on your OS");
        }
        new RuleUI();
    }
}
