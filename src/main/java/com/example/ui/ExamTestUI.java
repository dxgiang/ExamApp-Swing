package main.java.com.example.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import main.java.com.example.anticheat.AntiCheat;
import main.java.com.example.exam.ExamTestLogic;
import main.java.com.example.exam.Question;

public class ExamTestUI extends JFrame {
    private static final long serialVersionUID = 1L;
    public ExamTestLogic logic;
    private JPanel panelStart, panelExamTest;
    private JLabel labelStart, labelNote, labelQues;
    private JButton start, ans1, ans2, ans3, ans4;
    private String userName;
    private boolean completed = false, started = false;

    public ExamTestUI() {
        this.logic = new ExamTestLogic(this);
        this.userName = "";
        this.completed = false;
        
        setTitle("Exam Text - User: " + (userName != null && !userName.isEmpty() ? userName : "Unknown"));
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        initializeUIComponents();
        addListeners();
        AntiCheat antiCheat = new AntiCheat(this, this.logic);
        addKeyListener(antiCheat);
        addWindowFocusListener(antiCheat);
        setFocusable(true);
        requestFocusInWindow();
    }

    private void initializeUIComponents() {
        // Panel Start
        panelStart = new JPanel();
        labelStart = new JLabel(
                "                                           Press the button to start the test >>");
        labelStart.setFont(labelStart.getFont().deriveFont(30f));
        labelNote = new JLabel("Note: Using the MOUSE to complete the test. Do not minimize or switch to another window during the test.");
        labelNote.setHorizontalAlignment(JLabel.CENTER);
        labelNote.setBorder(new LineBorder(Color.BLACK, 1, true));
        labelNote.setFont(labelNote.getFont().deriveFont(30f));
        labelNote.setForeground(Color.RED);
        labelNote.setBackground(Color.YELLOW);
        panelStart.setLayout(new BorderLayout());
        start = new JButton(">===Start===<");
        start.setFont(start.getFont().deriveFont(25f));
        start.setBackground(Color.ORANGE);
        panelStart.add(labelStart, BorderLayout.WEST);
        panelStart.add(labelNote, BorderLayout.SOUTH);
        panelStart.add(start, BorderLayout.LINE_END);
        panelStart.setBackground(Color.GREEN);
        getContentPane().add(panelStart);

        // UI EXAM TEST
        panelExamTest = new JPanel();
        panelExamTest.setBackground(Color.CYAN);
        labelQues = new JLabel();
        panelExamTest.setLayout(new GridLayout(5, 1, 10, 10));
        ans1 = new JButton();
        ans1.setBackground(Color.YELLOW);
        ans2 = new JButton();
        ans2.setBackground(Color.YELLOW);
        ans3 = new JButton();
        ans3.setBackground(Color.YELLOW);
        ans4 = new JButton();
        ans4.setBackground(Color.YELLOW);
        panelExamTest.add(labelQues);
        panelExamTest.add(ans1);
        panelExamTest.add(ans2);
        panelExamTest.add(ans3);
        panelExamTest.add(ans4);
        panelExamTest.setVisible(false);
    }

    private void addListeners() {
        // Add action listener
        start.addActionListener(e -> {
            if (logic.getListQuestion().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "There is not enough questions to start the exam. Please add questions in the file 'questions.docx'.");
                return;
            }
            panelStart.setVisible(false);
            getContentPane().add(panelExamTest, BorderLayout.CENTER);
            panelExamTest.setVisible(true);
            started = true;
            logic.showQuestion();
        });
        
        ans1.addActionListener(e -> logic.processQuestion(1));
        ans2.addActionListener(e -> logic.processQuestion(2));
        ans3.addActionListener(e -> logic.processQuestion(3));
        ans4.addActionListener(e -> logic.processQuestion(4));
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
        setTitle("Exam Text - User: " + (userName != null && !userName.isEmpty() ? userName : "Unknown")); 
    }

    public void displayQuestion(Question ch) {
        labelQues.setText(ch.getQuestion());
        labelQues.setFont(labelQues.getFont().deriveFont(35f));
        ans1.setText(ch.getAns1());
        ans1.setFont(ans1.getFont().deriveFont(35f));
        ans2.setText(ch.getAns2());
        ans2.setFont(ans2.getFont().deriveFont(35f));
        ans3.setText(ch.getAns3());
        ans3.setFont(ans3.getFont().deriveFont(35f));
        ans4.setText(ch.getAns4());
        ans4.setFont(ans4.getFont().deriveFont(35f));
    }

    public void showResult(double score) {
        this.completed = true;
        JOptionPane.showMessageDialog(this, "You're " + String.format("%.2f", score) + "/10.0.");
        
        String status = (score >= 5) ? "PASS" : "FAIL";
        logic.setStatus(status);

        String logTime = upTime();
        String title = getTitle();
        System.out.println(logTime + " " + title + " " + status + "! Score: " + String.format("%.2f", score) + "/10.0");
        System.out.println(logTime + " " + title + " (Log out)");
        
        this.dispose();
    }
    
    public void showFileError(String filePath, String message, String dialogTitle) {
        JOptionPane.showMessageDialog(this, message, dialogTitle, JOptionPane.ERROR_MESSAGE);
    }
    
    public String upTime() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dt.format(format);
    }

    // Main
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("The Window look and feel do not support on your OS");
        }
        new ExamTestUI().setVisible(true);
    }
    public boolean isCompleted() {
        return completed;
    }
    public boolean isStarted() {
        return started;
    }
}