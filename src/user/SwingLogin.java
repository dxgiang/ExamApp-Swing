package user;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingLogin extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel loginPanel, registerPanel, panelMN, panelM1, panelM3;
	private JLabel labelLogin, labelRegister, labelUser, labelPass, labelRePass, labelM1;
	private JTextField user, pass, repass, reguser, regpass;
	private JButton createUser, login, register, loginInReG, inDS, addUser, delUser, hien;
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
		//UI MANAGEMENT
		panelM1 = new JPanel();
		panelM1.setSize(300, 200);
		panelM1.setBorder(BorderFactory.createLineBorder(Color.black));
		labelM1 = new JLabel();
		panelM1.add(labelM1);
		panelM3 = new JPanel();
		inDS = new JButton("In danh sach");
		inDS.addActionListener(this
				);
		addUser = new JButton("Them User");
		addUser.addActionListener(this);
		delUser = new JButton("Xoa User");
		delUser.addActionListener(this);
		hien = new JButton("Hien app");
		hien.addActionListener(this);
		panelM3.add(inDS);
		panelM3.add(addUser);
		panelM3.add(delUser);
		panelM3.add(hien);
		panelMN = new JPanel();
		panelMN.setLayout(new GridLayout(0, 1, 10, 10));
		panelMN.add(panelM1);
		panelMN.add(panelM3);
		setVisible(true);
		// Data
		loginsystem = new LoginSystem();
		loginsystem.addUser(new User<String, String>("root", ""), false);
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
				if(username.equals("root")) {
					getContentPane().remove(loginPanel);
					getContentPane().remove(registerPanel);
					getContentPane().add(panelMN);
					revalidate();
					repaint();
				} else {
					setVisible(false);
					new MidTerm().setVisible(true);
				}
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
		} else if(e.getSource() == inDS) {
			StringBuilder sb = new StringBuilder("<html><b>User List:</b><br>");
			for (User<String, String> u : loginsystem.getUserList()) {
				sb.append(u.getUser()).append(" - ").append(u.getPass()).append("<br>");
			}
			sb.append("</html>");
			labelM1.setText(sb.toString());
		} else if(e.getSource() == addUser) {
			String username = JOptionPane.showInputDialog(this, "Enter username:");
			//Fix if username is null or empty
			if(username == null || username.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please enter characters");
				return;
			}
			String password = JOptionPane.showInputDialog(this, "Enter password:");
			if (password == null || password.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please enter characters");
				return;
			}
			loginsystem.addUser(new User<String, String>(username, password), true);
			inDS.doClick();
		} else if(e.getSource() == delUser) {
			String username = JOptionPane.showInputDialog(this, "Enter username to delete:");
			loginsystem.deleteUser(username);
			inDS.doClick();
		} else if(e.getSource() == hien) {
			new MidTerm().setVisible(true);
		}
	}

	public static void main(String[] args) {
		new SwingLogin();
	}
}
