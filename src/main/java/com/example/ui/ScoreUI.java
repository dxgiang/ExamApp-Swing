package main.java.com.example.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ScoreUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private Double score;

    public ScoreUI(Double score) {
        this.score = score;
        setTitle("Your Score");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        JPanel panelLabel1 = new JPanel(new GridLayout(3,1,0,0));
        JLabel scoreLabel = new JLabel("Your Score ");
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(24f));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        panelLabel1.add(scoreLabel);
        JLabel scoreValueLabel = new JLabel(String.valueOf(getScore()));
        if(score < 5) {
            scoreValueLabel.setForeground(java.awt.Color.RED);
        } else {
            scoreValueLabel.setForeground(java.awt.Color.GREEN);
        }
        scoreValueLabel.setFont(scoreValueLabel.getFont().deriveFont(48f));
        scoreValueLabel.setHorizontalAlignment(JLabel.CENTER);
        panelLabel1.add(scoreValueLabel);
        JLabel resultLabel = new JLabel();
        if(score < 5) {
            resultLabel.setText("Unfortunately, you did not pass the exam.");
        } else {
            resultLabel.setText("Congratulations, you passed the exam!");
        }
        resultLabel.setFont(resultLabel.getFont().deriveFont(14f));
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        panelLabel1.add(resultLabel);
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(70, 30));
        okButton.addActionListener(e -> dispose());
        if(getScore() < 5) {
            okButton.setBackground(Color.RED);
        } else {
            okButton.setBackground(Color.GREEN);
        }
        okButton.setContentAreaFilled(false);
        okButton.setOpaque(true);
        buttonPanel.add(okButton);
        getContentPane().add(panelLabel1, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("The Window look and feel do not support on your OS");
        }
        ScoreUI scoreUI = new ScoreUI(5.0);
        scoreUI.setVisible(true);
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
