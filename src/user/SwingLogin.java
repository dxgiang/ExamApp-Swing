package user;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingLogin extends JFrame implements ActionListener {
	private JPanel loginPanel, registerPanel;
	private JLabel labelLogin, labelRegister, labelUser, labelPass, labelRePass;
	private JTextField user, pass, repass, reguser, regpass;
	private JButton createUser, login, register, loginInReG;
	private LoginSystem loginsystem;

	public SwingLogin() throws HeadlessException {
		super();
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setTitle("Login System");
		setResizable(false);
		// UI LOGIN
		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(4, 1, 10, 10));
		labelLogin = new JLabel("LOGIN");
		JPanel panel1 = new JPanel();
		panel1.add(labelLogin);
		labelUser = new JLabel("Username");
		user = new JTextField(15);
		JPanel panel2 = new JPanel();
		panel2.setSize(500, 200);
		panel2.add(labelUser);
		panel2.add(user);
		labelPass = new JLabel("Password");
		pass = new JTextField(15);
		JPanel panel3 = new JPanel();
		panel3.add(labelPass);
		panel3.add(pass);
		login = new JButton("Login");
		login.addActionListener(this);
		register = new JButton("Register");
		register.addActionListener(this);
		JPanel panel4 = new JPanel();
		panel4.add(register);
		panel4.add(login);
		loginPanel.add(panel1);
		loginPanel.add(panel2);
		loginPanel.add(panel3);
		loginPanel.add(panel4);
		getContentPane().add(loginPanel);
		// UI REGISTER
		registerPanel = new JPanel();
		registerPanel.setLayout(new GridLayout(5, 1, 10, 10));
		labelRegister = new JLabel("REGISTER");
		JPanel panel5 = new JPanel();
		panel5.add(labelRegister);
		labelUser = new JLabel("Username");
		reguser = new JTextField(15);
		JPanel panel6 = new JPanel();
		panel6.add(labelUser);
		panel6.add(reguser);
		labelPass = new JLabel("Password");
		regpass = new JTextField(15);
		JPanel panel7 = new JPanel();
		panel7.add(labelPass);
		panel7.add(regpass);
		labelRePass = new JLabel("Repeat Password");
		repass = new JTextField(15);
		JPanel panel8 = new JPanel();
		panel8.add(labelRePass);
		panel8.add(repass);
		loginInReG = new JButton("Login");
		loginInReG.addActionListener(this);
		JPanel panel9 = new JPanel();
		createUser = new JButton("Create");
		createUser.addActionListener(this);
		panel9.add(loginInReG);
		panel9.add(createUser);
		registerPanel.add(panel5);
		registerPanel.add(panel6);
		registerPanel.add(panel7);
		registerPanel.add(panel8);
		registerPanel.add(panel9);
		setVisible(true);
		// Data
		loginsystem = new LoginSystem();
		loginsystem.addUser(new User<String, String>("root", "0000"), false);
		loginsystem.addUser(new User<String, String>("giang", "0077"), false);
		loginsystem.addUser(new User<String, String>("hitler", "1945"), false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == register) {
			getContentPane().remove(loginPanel);
			getContentPane().add(registerPanel);
			revalidate();
			repaint();
		} else if (e.getSource() == loginInReG) {
			getContentPane().remove(registerPanel);
			getContentPane().add(loginPanel);
			revalidate();
			repaint();
		} else if (e.getSource() == login) {
			String username = user.getText();
			String password = pass.getText();
			if (loginsystem.authenticate(username, password)) {
				JOptionPane.showMessageDialog(this, "LOGIN SUCCESSFULLY");
				new ConvertCtoF();
			} else {
				JOptionPane.showMessageDialog(this, "WRONG INFOMATION");
			}
		} else if (e.getSource() == createUser) {
			String username = reguser.getText();
			String password = regpass.getText();
			if (!password.equals(repass.getText())) {
				JOptionPane.showMessageDialog(this, "PASSWORDS DO NOT MATCH!");
				return;
			}
			loginsystem.addUser(new User<String, String>(username, password), true);
		}
	}

	public static void main(String[] args) {
		new SwingLogin();
	}
}
