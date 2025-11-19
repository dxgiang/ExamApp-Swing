package main.java.com.example.ui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheatNotificationUI extends JFrame{
    private String user;
    private String reason;
    public JButton buttonExit;
    public CheatNotificationUI(String user, String reason) {
        this.user = user;
        this.reason = reason;
        setTitle("CHEAT DETECTED");
        setSize(550,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/resources/common/icon.jpg"));
        setIconImage(icon.getImage());
        setLayout(new BorderLayout());
        JPanel panelReason = new JPanel(new GridLayout(4,1));
        JLabel labelCatch = new JLabel(" User: " + getUser() +". You're caught cheating on the exam!");
        labelCatch.setFont(new Font("Arial", 1, 15));
        panelReason.add(labelCatch);
        JLabel reasonLabel = new JLabel(" Reason:" + getReason());
        reasonLabel.setFont(new Font("Arial", 1, 15));
        reasonLabel.setForeground(Color.RED);
        panelReason.add(reasonLabel);
        JLabel time = new JLabel(" Time: " + upTime());
        time.setFont(new Font("Arial", 1, 15));
        panelReason.add(time);
        JLabel lock = new JLabel(" NOW YOUR ACCOUNT HAS BEEN LOCKED! PLEASE CONTACT ADMIN!");
        lock.setFont(new Font("Arial", 1, 15));
        panelReason.add(lock);
        JPanel panelButton = new JPanel();
        buttonExit = new JButton("EXIT");
        buttonExit.setBackground(Color.RED);
        buttonExit.setContentAreaFilled(false);
        buttonExit.setOpaque(true);
        buttonExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
            
        });
        panelButton.add(buttonExit);
        getContentPane().add(panelReason, BorderLayout.CENTER);
        getContentPane().add(panelButton, BorderLayout.SOUTH);
        setVisible(true);
    }
    public static void main(String[] args) {
        new CheatNotificationUI("userxmpl","CHEAT REASON");
    }

    public String getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }

    public String upTime() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dtfor = dt.format(format);
        return dtfor;
    }

    public JButton getButtonExit() {
        return buttonExit;
    }
}
