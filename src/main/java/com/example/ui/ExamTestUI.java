package main.java.com.example.exam;

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

public class ExamTestUI extends JFrame {
    private static final long serialVersionUID = 1L;
    public ExamTestLogic logic; // Tham chiếu đến logic xử lý

    // Components
    private JPanel panelStart, panelExamTest;
    private JLabel labelStart, labelNote, labelQues;
    private JButton start, ans1, ans2, ans3, ans4;
    private String userName; // Giữ lại userName ở UI để quản lý tiêu đề
    private boolean completed = false; // Trạng thái hoàn thành bài thi

    // Constructor
    public ExamTestUI() {
        // Khởi tạo logic trước để tải câu hỏi
        this.logic = new ExamTestLogic(this);
        this.userName = ""; // Giữ lại thuộc tính userName
        this.completed = false; // Khởi tạo trạng thái hoàn thành
        
        // Thiết lập JFrame
        setTitle("Exam Text - User: " + (userName != null && !userName.isEmpty() ? userName : "Unknown"));
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        initializeUIComponents();
        addListeners();
        
        // Cấu hình chống gian lận
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
                "                                                       Press the button to start the test");
        labelStart.setFont(labelStart.getFont().deriveFont(25f));
        labelNote = new JLabel("Note: Using the MOUSE to complete the test. Do not minimize or switch to another window during the test.");
        labelNote.setHorizontalAlignment(JLabel.CENTER);
        labelNote.setBorder(new LineBorder(Color.BLACK, 1, true));
        labelNote.setFont(labelNote.getFont().deriveFont(25f));
        labelNote.setForeground(Color.RED);
        labelNote.setBackground(Color.YELLOW);
        panelStart.setLayout(new BorderLayout());
        start = new JButton("===Start===");
        start.setFont(start.getFont().deriveFont(25f));
        start.setBackground(Color.ORANGE);
        panelStart.add(labelStart, BorderLayout.WEST);
        panelStart.add(labelNote, BorderLayout.CENTER);
        panelStart.add(labelNote, BorderLayout.SOUTH); // Giữ nguyên việc add labelNote 2 lần như code gốc
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
            logic.showQuestion(); // Chuyển sang logic
        });
        
        // Chuyển việc xử lý câu trả lời sang logic
        ans1.addActionListener(e -> logic.processQuestion(1));
        ans2.addActionListener(e -> logic.processQuestion(2));
        ans3.addActionListener(e -> logic.processQuestion(3));
        ans4.addActionListener(e -> logic.processQuestion(4));
    }
    
    // ====================================================================
    // CÁC PHƯƠNG THỨC GIAO TIẾP VỚI LOGIC (GIỮ NGUYÊN LOGIC CŨ)
    // ====================================================================
    
    // Getter và Setter (giữ lại 2 hàm này ở UI vì logic setTitle và username được dùng ở đây)
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
        // Cập nhật tiêu đề theo logic cũ
        setTitle("Exam Text - User: " + (userName != null && !userName.isEmpty() ? userName : "Unknown")); 
    }
    
    // Phương thức hiển thị câu hỏi (được gọi từ ExamTestLogic)
    public void displayQuestion(Question ch) {
        labelQues.setText(ch.getQuestion());
        labelQues.setFont(labelQues.getFont().deriveFont(17f));
        ans1.setText(ch.getAns1());
        ans1.setFont(ans1.getFont().deriveFont(15f));
        ans2.setText(ch.getAns2());
        ans2.setFont(ans2.getFont().deriveFont(15f));
        ans3.setText(ch.getAns3());
        ans3.setFont(ans3.getFont().deriveFont(15f));
        ans4.setText(ch.getAns4());
        ans4.setFont(ans4.getFont().deriveFont(15f));
    }

    // Phương thức hiển thị kết quả và log (giữ nguyên logic cũ)
    public void showResult(double score) {
        this.completed = true; // Đánh dấu đã hoàn thành bài thi
        JOptionPane.showMessageDialog(this, "You're " + String.format("%.2f", score) + "/10.0.");
        
        String status = (score >= 5) ? "PASS" : "FAIL";
        logic.setStatus(status); // Cập nhật status vào logic
        
        // Log kết quả ra console (dùng upTime từ UI)
        String logTime = upTime();
        String title = getTitle();
        System.out.println(logTime + " " + title + " " + status + "! Score: " + String.format("%.2f", score) + "/10.0");
        System.out.println(logTime + " " + title + " (Log out)");
        
        this.dispose();
    }
    
    // Phương thức hiển thị lỗi đọc file
    public void showFileError(String filePath, String message, String dialogTitle) {
        JOptionPane.showMessageDialog(this, message, dialogTitle, JOptionPane.ERROR_MESSAGE);
    }
    
    // Phương thức lấy thời gian (giữ nguyên logic cũ)
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
            e.printStackTrace();
        }
        new ExamTestUI().setVisible(true);
    }
    public boolean isCompleted() {
        return completed;
    }
}