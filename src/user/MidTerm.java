package user;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MidTerm extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel panel1, panel2, panel3, panel4, mainPanel;
	private JLabel label1, label2, label3;
	private JTextField field1, field2;
	private JButton button1, button2, button3;

	public MidTerm() {
		setTitle("MidTerm");
		setSize(300, 210);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new FlowLayout());
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 1, 10, 0));
		panel1 = new JPanel();
		label1 = new JLabel("Mid-Term Exam");
		panel1.add(label1);
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(10, 10, 10));
		label2 = new JLabel("Input Value");
		field1 = new JTextField(10);
		panel2.add(label2);
		panel2.add(field1);
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout(10, 5, 0));
		label3 = new JLabel("Output Value");
		field2 = new JTextField(10);
		panel3.add(label3);
		panel3.add(field2);
		panel4 = new JPanel();
		button1 = new JButton("Binary");
		button1.addActionListener(this);
		button2 = new JButton("Prime");
		button2.addActionListener(this);
		button3 = new JButton("Exit");
		button3.addActionListener(this);
		panel4.add(button1);
		panel4.add(button2);
		panel4.add(button3);
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(panel4);
		getContentPane().add(mainPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// TODO Auto-generated method stub
			if (e.getSource() == button1) {
				int number = Integer.parseInt(field1.getText());
				StringBuilder sb = new StringBuilder();
				while(number > 0) {
					int kq = number % 2;
					number = number / 2;
					String temp = Integer.toString(kq);
					sb.insert(0, temp);
				}
				field2.setText(sb.toString());
			} else if (e.getSource() == button2) {
				int checkPrime = Integer.parseInt(field1.getText());
				int count = 0;
				for (int i = 1; i <= checkPrime; i++) {
					if (checkPrime % i == 0) {
						count++;
					}
				}
				if (count == 2) {
					field2.setText("is prime");
				} else {
					field2.setText("is not prime");
				}
			} else if(e.getSource() == button3) {
				setVisible(false);
			}
			
		} catch (NumberFormatException error) {
			field2.setText("Input value error");
		}
	}

	public static void main(String[] args) {
		new MidTerm().setVisible(true);
	}
}
