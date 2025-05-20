package exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ExamTest extends JFrame {
	// Attributes
	private static final long serialVersionUID = 1L;
	private JPanel panelStart, panelExamTest;
	private JLabel labelStart, labelQues;
	private JButton start, ans1, ans2, ans3, ans4;
	private String userName;
	private int index = 0;
	private double score = 0;
	private List<Question> listQuestion;

	// Constructor
	public ExamTest() {
		String currentUser = userName;
		if (currentUser == null || currentUser.isEmpty()) {
			currentUser = "Unknown";
		}
		setTitle("Thi - User: " + getUserName());
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		// Data
		listQuestion = new ArrayList<Question>();
		listQuestion.add(new Question("1 + 1 = ?", "A. 2", "B. 4", "C. 8", "D. 100", 1));
		listQuestion.add(new Question("256 - 256 = ?", "A. 1", "B. 0", "C. 5", "D. 9", 2));
		listQuestion.add(new Question("4 x 0 = ?", "A. 1", "B. 2", "C. X", "D. 99", 3));
		listQuestion.add(new Question("1 x 0 = ?", "A. 4", "B. 5", "C. 6", "D. 0", 4));

		//
		panelStart = new JPanel();
		labelStart = new JLabel("              Press the button to start the test");
		labelStart.setFont(labelStart.getFont().deriveFont(15f));
		panelStart.setLayout(new BorderLayout());
		start = new JButton("===Start===");
		start.setFont(start.getFont().deriveFont(14f));
		start.setBackground(Color.ORANGE);
		panelStart.add(labelStart, BorderLayout.WEST);
		panelStart.add(start, BorderLayout.LINE_END);
		panelStart.setBackground(Color.GREEN);
		getContentPane().add(panelStart);

		//UI START EXAM
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
		// Add action listener
		start.addActionListener(e -> {
			panelStart.setVisible(false);
			getContentPane().add(panelExamTest, BorderLayout.CENTER);
			panelExamTest.setVisible(true);
			showQuestion();
		});
		ans1.addActionListener(e -> processQuestion(1));
		ans2.addActionListener(e -> processQuestion(2));
		ans3.addActionListener(e -> processQuestion(3));
		ans4.addActionListener(e -> processQuestion(4));
	}

	private String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

	// Getter and Setter
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// Methods
	private void processQuestion(int luaChon) {
		if (index < listQuestion.size()) {
			Question ch = listQuestion.get(index);
			if (luaChon == ch.getIndexRightAns()) {
				score += (double) 10 / listQuestion.size();
			}
			index++;
			showQuestion();
		}
	}

	private void showQuestion() {
		if (index < listQuestion.size()) {
			Question ch = listQuestion.get(index);
			labelQues.setText("Question " + (index + 1) + ": " + ch.getQuestion());
			labelQues.setFont(labelQues.getFont().deriveFont(15f));
			ans1.setText(ch.getAns1());
			ans1.setFont(ans1.getFont().deriveFont(12f));
			ans2.setText(ch.getAns2());
			ans2.setFont(ans2.getFont().deriveFont(12f));
			ans3.setText(ch.getAns3());
			ans3.setFont(ans3.getFont().deriveFont(12f));
			ans4.setText(ch.getAns4());
			ans4.setFont(ans4.getFont().deriveFont(12f));
		} else {
			JOptionPane.showMessageDialog(this, "You have completed the test. Score: " + score + "/10.0.");
			this.dispose();
		}
	}

	// Main
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new ExamTest().setVisible(true);
	}
}
