package main.java.com.example.exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph; // Import this

public class ExamTest extends JFrame {
	// Attributes
	private static final long serialVersionUID = 1L;
	private JPanel panelStart, panelExamTest;
	private JLabel labelStart, labelNote, labelQues;
	private JButton start, ans1, ans2, ans3, ans4;
	private String userName;
	private int index = 0;
	private double score = 0;
	private String status = null;
	private List<Question> listQuestion;
	private LocalDateTime dt;

	// Constructor
	public ExamTest() {
		setTitle("Thi - User: " + (userName != null && !userName.isEmpty() ? userName : "Unknown")); // Use getter or
																										// direct field
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);
		// Data
		listQuestion = new ArrayList<Question>();
		loadQuestionsFromWord("data/questions.docx");

		panelStart = new JPanel();
		labelStart = new JLabel(
				"                                                       Press the button to start the test");

		labelStart.setFont(labelStart.getFont().deriveFont(25f));
		labelNote = new JLabel("Note: Do not minimize or switch to another window during the test.");
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
		panelStart.add(labelNote, BorderLayout.SOUTH);
		panelStart.add(start, BorderLayout.LINE_END);
		panelStart.setBackground(Color.GREEN);
		getContentPane().add(panelStart);

		// UI START EXAM
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
			if (listQuestion.isEmpty()) {
				JOptionPane.showMessageDialog(this,
						"There is not enough questions to start the exam. Please add questions in the file 'questions.docx'.");
				return;
			}
			panelStart.setVisible(false);
			getContentPane().add(panelExamTest, BorderLayout.CENTER);
			panelExamTest.setVisible(true);
			showQuestion();
		});
		ans1.addActionListener(e -> processQuestion(1));
		ans2.addActionListener(e -> processQuestion(2));
		ans3.addActionListener(e -> processQuestion(3));
		ans4.addActionListener(e -> processQuestion(4));
		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				if (index < listQuestion.size() && status == null) {
					JOptionPane.showMessageDialog(ExamTest.this, "You're cheat on the exam!");
					System.out.println(
							upTime() + " " + getTitle() + " CHEAT ON THE EXAM!!(ALT + TAB, MINIMIZE or CLICK OUTSIDE)");
					System.out.println(upTime() + " " + getTitle() + " (Log out)");
					// Close the application
					ExamTest.this.dispose();
					// Set score to 0
					score = 0;
					status = "CHEAT";
				}
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	public String getUserName() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
			labelQues.setFont(labelQues.getFont().deriveFont(17f));
			ans1.setText(ch.getAns1());
			ans1.setFont(ans1.getFont().deriveFont(15f));
			ans2.setText(ch.getAns2());
			ans2.setFont(ans2.getFont().deriveFont(15f));
			ans3.setText(ch.getAns3());
			ans3.setFont(ans3.getFont().deriveFont(15f));
			ans4.setText(ch.getAns4());
			ans4.setFont(ans4.getFont().deriveFont(15f));
		} else {
			JOptionPane.showMessageDialog(this, "You're " + String.format("%.2f", score) + "/10.0.");
			if (score >= 5) {
				status = "PASS";
				System.out.println(
						upTime() + " " + getTitle() + " PASS! Score: " + String.format("%.2f", score) + "/10.0");
				System.out.println(upTime() + " " + getTitle() + " (Log out)");
			} else {
				status = "FAIL";
				System.out.println(
						upTime() + " " + getTitle() + " FAIL! Score: " + String.format("%.2f", score) + "/10.0");
				System.out.println(upTime() + " " + getTitle() + " (Log out)");
			}

			this.dispose();
		}
	}

	public String upTime() {
		dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String dtfor = dt.format(format);
		return dtfor;
	}

	private void loadQuestionsFromWord(String filePath) {
		try (FileInputStream fis = new FileInputStream(filePath); XWPFDocument document = new XWPFDocument(fis)) {
			listQuestion.clear();
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			for (int i = 0; i < paragraphs.size(); i += 6) {
				if (i + 5 < paragraphs.size()) {
					String questionText = paragraphs.get(i).getText().trim();
					String ans1Text = paragraphs.get(i + 1).getText().trim();
					String ans2Text = paragraphs.get(i + 2).getText().trim();
					String ans3Text = paragraphs.get(i + 3).getText().trim();
					String ans4Text = paragraphs.get(i + 4).getText().trim();
					int correctAnswerIndex = Integer.parseInt(paragraphs.get(i + 5).getText().trim());

					listQuestion.add(
							new Question(questionText, ans1Text, ans2Text, ans3Text, ans4Text, correctAnswerIndex));
				}
			}
			System.out.println(upTime() + " Loaded " + listQuestion.size() + " questions from " + filePath);

		} catch (IOException e) {
			System.err.println(upTime() + " Error reading Word file: " + e.getMessage());
			JOptionPane.showMessageDialog(this, "Cannot read question file: " + filePath + "\n" + e.getMessage(),
					"Error reading file", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException e) {
			System.err.println(upTime() + " Error formatting the correct answer in the Word file: " + e.getMessage());
			JOptionPane.showMessageDialog(this,
					"Error formatting the correct answer in the question file. Please check the Word file again.",
					"Error formating", JOptionPane.ERROR_MESSAGE);
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