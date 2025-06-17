package main.java.com.example.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import main.java.com.example.auth.LoginSystem;
import main.java.com.example.auth.User;
import main.java.com.example.exam.ExamTest;

public class SwingLogin extends JFrame implements ActionListener {
	// Attributes
	private static final long serialVersionUID = 1L;
	private JPanel loginPanel, registerPanel, panelMN, panelM3, panelLoading;
	private JLabel labelLogin, labelRegister, labelNote, labelUser, labelPass, labelRePass, labelCopyright;
	private JTextField user, pass, repass, reguser, regpass;
	private JButton createUser, login, register, loginInReG, printList, addUser, delUser, showApp, unlock, lock, logout,
			hidePass, hideRePass, hideRegPass;
	private JMenuBar barmenu;
	private JMenu menuOption, menuUser, menuHelp;
	private JMenuItem itemExit, itemAbout, itemLogin, itemRegister, itemPrintList, itemAddUser, itemDelUser,
			itemShowApp, itemUnlock, itemLock, itemLogout, itemNonRoot;
	private LoginSystem loginsystem;
	private ExamTest testExam;
	private int countWrong = 0, countWrong2 = 0;
	private boolean isRoot = false;
	private JTable userTable;
	private DefaultTableModel tableModel;
	private LocalDateTime dt;

	// Constructor
	public SwingLogin() throws HeadlessException {
		super();
		setSize(550, 380);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setTitle("Login");
		setResizable(false);
		ImageIcon icon = new ImageIcon(getClass().getResource("/main/resources/common/icon.jpg"));
		setIconImage(icon.getImage());
		// UI LOGIN
		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(6, 1, 10, 10));
		labelLogin = new JLabel("LOGIN");
		labelLogin.setFont(new Font("Serif", 1, 25));
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.white);
		panel1.add(labelLogin);
		labelUser = new JLabel("Username");
		labelUser.setFont(labelLogin.getFont().deriveFont(15f));
		user = new JTextField(22);
		user.setBorder(BorderFactory.createTitledBorder("Username"));
		user.setBackground(Color.white);
		user.addActionListener(this);
		JPanel panel2 = new JPanel();
//		panel2.add(labelUser);
		panel2.add(user);
		panel2.setBackground(Color.white);
		labelPass = new JLabel("Password ");
		labelPass.setFont(labelLogin.getFont().deriveFont(15f));
		pass = new JPasswordField(17);
		pass.setBorder(BorderFactory.createTitledBorder("Password"));
		pass.setBackground(Color.white);
		pass.addActionListener(this);
		// Add a button to toggle password visibility
		hidePass = new JButton("⦿");
		hidePass.setBackground(Color.white);
		hidePass.setFont(labelLogin.getFont().deriveFont(9f));
		hidePass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hidePass.getText().equals("⦿")) {
					hidePass.setText("⦾");
					((JPasswordField) pass).setEchoChar((char) 0); // Show password
				} else {
					hidePass.setText("⦿");
					((JPasswordField) pass).setEchoChar('*'); // Hide password
				}
			}
		});
		JPanel panel3 = new JPanel();
//		panel3.add(labelPass);
		panel3.add(pass);
		panel3.add(hidePass);
		panel3.setBackground(Color.white);
		login = new JButton("Login");
		login.setBackground(Color.green);
		login.setFont(new Font("Serif", 0, 13));
		login.addActionListener(this);
		register = new JButton("Register");
		register.addActionListener(this);
		register.setFont(new Font("Serif", 0, 10));
		JPanel panel4 = new JPanel();
		panel4.add(register);
		panel4.add(login);
		panel4.setBackground(Color.white);
		labelNote = new JLabel("Note: If you enter wrong password 3 times, application will freeze few seconds!");
		labelNote.setForeground(Color.red);
		labelNote.setFont(new Font("Serif", 0, 13));
		JPanel panel0 = new JPanel();
		panel0.add(labelNote);
		panel0.setBackground(Color.white);
		labelCopyright = new JLabel("© • Exam App 2025 - Copyright by river0077 • ©");
		labelCopyright.setFont(new Font("Serif", 0, 13));
		JPanel panel00 = new JPanel();
		panel00.add(labelCopyright);
		panel00.setBackground(Color.white);
		loginPanel.add(panel1);
		loginPanel.add(panel2);
		loginPanel.add(panel3);
		loginPanel.add(panel4);
		loginPanel.add(panel0);
		loginPanel.add(panel00);
		loginPanel.setBackground(Color.white);
		getContentPane().add(loginPanel);
		getContentPane().setBackground(Color.white);
		// UI REGISTER
		registerPanel = new JPanel();
		registerPanel.setLayout(new GridLayout(6, 1, 10, 10));
		labelRegister = new JLabel("REGISTER");
		labelRegister.setFont(new Font("Serif", 1, 25));
		JPanel panel5 = new JPanel();
		panel5.add(labelRegister);
		panel5.setBackground(Color.white);
		labelUser = new JLabel("Username  ");
		labelUser.setFont(labelLogin.getFont().deriveFont(15f));
		reguser = new JTextField(22);
		reguser.setBorder(BorderFactory.createTitledBorder("Username"));
		reguser.setBackground(Color.white);
		reguser.addActionListener(this);
		JPanel panel6 = new JPanel();
//		panel6.add(labelUser);
		panel6.add(reguser);
		panel6.setBackground(Color.white);
		labelPass = new JLabel("Password  ");
		labelPass.setFont(labelLogin.getFont().deriveFont(15f));
		regpass = new JPasswordField(17);
		regpass.setBorder(BorderFactory.createTitledBorder("Password"));
		regpass.setBackground(Color.white);
		regpass.addActionListener(this);
		// Add a button to toggle password visibility
		hideRegPass = new JButton("⦿");
		hideRegPass.setBackground(Color.white);
		hideRegPass.setFont(labelLogin.getFont().deriveFont(9f));
		hideRegPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hideRegPass.getText().equals("⦿")) {
					hideRegPass.setText("⦾");
					((JPasswordField) regpass).setEchoChar((char) 0); // Show password
				} else {
					hideRegPass.setText("⦿");
					((JPasswordField) regpass).setEchoChar('*'); // Hide password
				}
			}
		});
		JPanel panel7 = new JPanel();
//		panel7.add(labelPass);
		panel7.add(regpass);
		panel7.add(hideRegPass);
		panel7.setBackground(Color.white);
		labelRePass = new JLabel("RePassword");
		labelRePass.setFont(labelLogin.getFont().deriveFont(15f));
		repass = new JPasswordField(17);
		repass.setBorder(BorderFactory.createTitledBorder("RePassword"));
		repass.setBackground(Color.white);
		repass.addActionListener(this);
		// Add a button to toggle password visibility
		hideRePass = new JButton("⦿");
		hideRePass.setBackground(Color.white);
		hideRePass.setFont(labelLogin.getFont().deriveFont(9f));
		hideRePass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hideRePass.getText().equals("⦿")) {
					hideRePass.setText("⦾");
					((JPasswordField) repass).setEchoChar((char) 0); // Show password
				} else {
					hideRePass.setText("⦿");
					((JPasswordField) repass).setEchoChar('*'); // Hide password
				}
			}
		});
		JPanel panel8 = new JPanel();
//		panel8.add(labelRePass);
		panel8.add(repass);
		panel8.add(hideRePass);
		panel8.setBackground(Color.white);
		loginInReG = new JButton("Login");
		loginInReG.addActionListener(this);
		loginInReG.setFont(new Font("Serif", 0, 10));
		JPanel panel9 = new JPanel();
		createUser = new JButton("Create");
		createUser.setBackground(Color.blue);
		createUser.setFont(new Font("Serif", 0, 13));
		createUser.addActionListener(this);
		panel9.add(loginInReG);
		panel9.add(createUser);
		panel9.setBackground(Color.white);
		JPanel panel10 = new JPanel();
		labelCopyright = new JLabel("© • Exam App 2025 - Copyright by river0077 • ©");
		labelCopyright.setFont(new Font("Serif", 0, 13));
		panel10.add(labelCopyright);
		panel10.setBackground(Color.white);
		registerPanel.add(panel5);
		registerPanel.add(panel6);
		registerPanel.add(panel7);
		registerPanel.add(panel8);
		registerPanel.add(panel9);
		registerPanel.add(panel10);
		registerPanel.setBackground(Color.white);

		// UI MANAGEMENT
		String[] columnNames = { "Username", "Password", "Score", "Status" };
		tableModel = new DefaultTableModel(columnNames, 0);
		userTable = new JTable(tableModel);
		userTable.setEnabled(true);
		JScrollPane scrollPane = new JScrollPane(userTable);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollPane.setPreferredSize(new Dimension(450, 260));

		panelM3 = new JPanel();
		printList = new JButton("Print List");
		printList.setBackground(Color.gray);
		printList.setFont(labelLogin.getFont().deriveFont(10.5f));
		printList.addActionListener(this);
		addUser = new JButton("Add User");
		addUser.setBackground(Color.green);
		addUser.setFont(labelLogin.getFont().deriveFont(10.5f));
		addUser.addActionListener(this);
		delUser = new JButton("Delete User");
		delUser.setBackground(Color.red);
		delUser.setFont(labelLogin.getFont().deriveFont(10.5f));
		delUser.addActionListener(this);
		showApp = new JButton("Show App");
		showApp.setBackground(Color.blue);
		showApp.setFont(labelLogin.getFont().deriveFont(10.5f));
		showApp.addActionListener(this);
		unlock = new JButton("Unlock");
		unlock.setBackground(Color.pink);
		unlock.setFont(labelLogin.getFont().deriveFont(10.5f));
		unlock.addActionListener(this);
		lock = new JButton("Lock");
		lock.setBackground(Color.red);
		lock.setFont(labelLogin.getFont().deriveFont(10.5f));
		lock.addActionListener(this);
		logout = new JButton("Logout");
		logout.setBackground(Color.orange);
		logout.setFont(labelLogin.getFont().deriveFont(10.5f));
		logout.addActionListener(this);
		panelM3.add(printList);
		panelM3.add(addUser);
		panelM3.add(delUser);
		panelM3.add(showApp);
		panelM3.add(unlock);
		panelM3.add(lock);
		panelM3.add(logout);
		panelM3.setBackground(Color.yellow);
		JPanel panelLoading = new JPanel();
		panelLoading.setBackground(Color.yellow);
		JLabel labelLoading = new JLabel("");
		panelLoading.add(labelLoading);
		JPanel panel001 = new JPanel();
		labelCopyright = new JLabel("© • Copyright by river0077");
		panel001.add(labelCopyright);
		panel001.setBackground(Color.yellow);
		// Menu
		barmenu = new JMenuBar();
		menuOption = new JMenu("Option");
		menuUser = new JMenu("User");
		itemNonRoot = new JMenuItem("You can not access this!");
		itemNonRoot.setEnabled(false);
		menuUser.add(itemNonRoot);
		itemLogin = new JMenuItem("Login");
		itemLogin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		ImageIcon iconLogin = new ImageIcon(getClass().getResource("/main/resources/ui/login.png"));
		// set size for iconLogin
		iconLogin = new ImageIcon(iconLogin.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemLogin.setIcon(iconLogin);
		itemLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRoot == true && e.getSource() == itemLogin) {
					logout.doClick();
				} else {
					getContentPane().remove(registerPanel);
					getContentPane().remove(panelMN);
					getContentPane().add(loginPanel);
					menuUser.remove(itemPrintList);
					menuUser.remove(itemAddUser);
					menuUser.remove(itemDelUser);
					menuUser.remove(itemShowApp);
					menuUser.remove(itemUnlock);
					menuUser.remove(itemLock);
					menuUser.remove(itemLogout);
					menuUser.add(itemNonRoot);
					user.setText("");
					pass.setText("");
					setTitle("Login");
					revalidate();
					repaint();
				}
			}
		});
		itemRegister = new JMenuItem("Register");
		itemRegister.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		// set size for iconRegister
		ImageIcon iconRegister = new ImageIcon(getClass().getResource("/main/resources/ui/register.png"));
		// set size for iconRegister
		iconRegister = new ImageIcon(iconRegister.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemRegister.setIcon(iconRegister);
		itemRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRoot == true && e.getSource() == itemRegister) {
					logout.doClick();
				} else {
					getContentPane().remove(loginPanel);
					getContentPane().remove(panelMN);
					getContentPane().add(registerPanel);
					menuUser.remove(itemPrintList);
					menuUser.remove(itemAddUser);
					menuUser.remove(itemDelUser);
					menuUser.remove(itemShowApp);
					menuUser.remove(itemUnlock);
					menuUser.remove(itemLock);
					menuUser.remove(itemLogout);
					menuUser.add(itemNonRoot);
					reguser.setText("");
					regpass.setText("");
					repass.setText("");
					setTitle("Register");
					revalidate();
					repaint();
				}
			}
		});
		itemExit = new JMenuItem("Exit");
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		ImageIcon iconExit = new ImageIcon(getClass().getResource("/main/resources/ui/exit.png"));
		// set size for iconExit
		iconExit = new ImageIcon(iconExit.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemExit.setIcon(iconExit);
		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(upTime() + " Exit app");
				System.exit(0);
			}
		});
		itemPrintList = new JMenuItem("Print List");
		itemPrintList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		ImageIcon iconPrintList = new ImageIcon(getClass().getResource("/main/resources/auth/printlist.png"));
		// set size for iconPrintList
		iconPrintList = new ImageIcon(iconPrintList.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemPrintList.setIcon(iconPrintList);
		itemPrintList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printList.doClick();
				itemPrintList.setText("Reload");
			}
		});
		itemAddUser = new JMenuItem("Add User");
		itemAddUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		ImageIcon iconAddUser = new ImageIcon(getClass().getResource("/main/resources/auth/add.png"));
		// set size for iconAddUser
		iconAddUser = new ImageIcon(iconAddUser.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemAddUser.setIcon(iconAddUser);
		itemAddUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addUser.doClick();
			}
		});
		itemDelUser = new JMenuItem("Delete User");
		itemDelUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		ImageIcon iconDelUser = new ImageIcon(getClass().getResource("/main/resources/auth/delete.png"));
		// set size for iconDelUser
		iconDelUser = new ImageIcon(iconDelUser.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemDelUser.setIcon(iconDelUser);
		itemDelUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delUser.doClick();
			}
		});
		itemShowApp = new JMenuItem("Show App");
		itemShowApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		ImageIcon iconShowApp = new ImageIcon(getClass().getResource("/main/resources/exam/showapp.png"));
		// set size for iconShowApp
		iconShowApp = new ImageIcon(iconShowApp.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemShowApp.setIcon(iconShowApp);
		itemShowApp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showApp.doClick();
			}
		});
		itemUnlock = new JMenuItem("Unlock");
		itemUnlock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		ImageIcon iconUnlock = new ImageIcon(getClass().getResource("/main/resources/auth/unlock.png"));
		// set size for iconUnlock
		iconUnlock = new ImageIcon(iconUnlock.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemUnlock.setIcon(iconUnlock);
		itemUnlock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unlock.doClick();
			}
		});
		itemLock = new JMenuItem("Lock");
		itemLock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		ImageIcon iconLock = new ImageIcon(getClass().getResource("/main/resources/auth/lock.png"));
		// set size for iconLock
		iconLock = new ImageIcon(iconLock.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemLock.setIcon(iconLock);
		itemLock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lock.doClick();
			}
		});
		itemLogout = new JMenuItem("Logout");
		itemLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		ImageIcon iconLogout = new ImageIcon(getClass().getResource("/main/resources/ui/logout.png"));
		// set size for iconLogout
		iconLogout = new ImageIcon(iconLogout.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemLogout.setIcon(iconLogout);
		itemLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logout.doClick();
			}
		});
		menuOption.add(itemLogin);
		menuOption.add(itemRegister);
		menuOption.add(itemExit);
		menuHelp = new JMenu("Help");
		itemAbout = new JMenuItem("About");
		ImageIcon iconAbout = new ImageIcon(getClass().getResource("/main/resources/common/about.png"));
		// set size for iconAbout
		iconAbout = new ImageIcon(iconAbout.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		itemAbout.setIcon(iconAbout);
		itemAbout.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Developed by river0077.");
		});
		menuHelp.add(itemAbout);
		barmenu.add(menuOption);
		barmenu.add(menuUser);
		barmenu.add(menuHelp);
		setJMenuBar(barmenu);
		panelMN = new JPanel(new BorderLayout());
		panelMN.add(scrollPane, BorderLayout.CENTER);
		panelMN.add(panelM3, BorderLayout.NORTH);
		panelMN.add(labelLoading, BorderLayout.SOUTH);
		panelMN.add(panel001, BorderLayout.PAGE_END);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println(upTime() + " Exit app");
				System.setOut(SwingLogin.originalOut);
				System.setErr(SwingLogin.originalErr);
				System.exit(0);
			}
		});
		setVisible(true);
		// Data
		loginsystem = new LoginSystem();
	}

	// ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == register) {
			getContentPane().remove(loginPanel);
			getContentPane().add(registerPanel);
			reguser.setText("");
			regpass.setText("");
			repass.setText("");
			setTitle("Register");
			revalidate();
			repaint();
		} else if (e.getSource() == loginInReG) {
			getContentPane().remove(registerPanel);
			getContentPane().add(loginPanel);
			user.setText("");
			pass.setText("");
			setTitle("Login");
			revalidate();
			repaint();
		} else if (e.getSource() == login || e.getSource() == pass || e.getSource() == user) {
			String username = user.getText();
			String password = new String(pass.getText());
			if (loginsystem.authenticate(username, password)) {
				if (username.equals("root")) {
					System.out.println(upTime() + " Logined successfully as root");
					JOptionPane.showMessageDialog(this, "LOGIN AS ROOT");
					isRoot = true;
					countWrong = 0;
					getContentPane().remove(loginPanel);
					getContentPane().remove(registerPanel);
					menuUser.remove(itemNonRoot);
					menuUser.add(itemPrintList);
					menuUser.add(itemAddUser);
					menuUser.add(itemDelUser);
					menuUser.add(itemShowApp);
					menuUser.add(itemUnlock);
					menuUser.add(itemLock);
					menuUser.add(itemLogout);
					setTitle("Management");
					getContentPane().add(panelMN);
					printList.doClick();
					revalidate();
					repaint();

				} else {
					for (User<String, String> u : loginsystem.getUserList()) {
						if (u.getUser().equals(username)) {
							if (u.getStatus() != null && (u.getStatus().equals("PASS") || u.getStatus().equals("FAIL")
									|| u.getStatus().equals("CHEAT"))) {
								JOptionPane.showMessageDialog(this, "YOU HAVE TAKEN THE EXAM! AUTO LOG OUT!");
								System.out.println(upTime() + " Logout - User: " + username + "(auto log out)");
								user.setText("");
								pass.setText("");
								return;
							} else if (u.getStatus() != null && u.getStatus().equals("LOCKED")) {
								JOptionPane.showMessageDialog(this,
										"YOUR ACCOUNT HAS BEEN LOCKED! PLEASE CONTACT ADMIN!");
								System.out.println(upTime() + " Login - User: " + username + " Fail! (account locked)");
								user.setText("");
								pass.setText("");
								return;
							}
						}
					}
					JOptionPane.showMessageDialog(this, "LOGIN SUCCESSFULLY");
					System.out.println(upTime() + " Login - User: " + username + " (logined successfully)");
					countWrong = 0;
					user.setText("");
					pass.setText("");
					ExamTest exam = new ExamTest();
					exam.setUserName(username);
					exam.setTitle("Thi - User: " + username);
					exam.setVisible(true);
					exam.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							try {
								for (User<String, String> u : loginsystem.getUserList()) {
									if (u.getUser().equals(username)) {
										u.setScore(exam.getScore());
										u.setStatus(exam.getStatus());
										loginsystem.addUser(u, false);
									}
								}
							} catch (Exception ex) {
								System.out.println();
							}
						}
					});
				}
			} else {
				if (loginsystem.wrongPass(username, password)) {
					JOptionPane.showMessageDialog(this, "WRONG PASSWORD");
					System.out.println(upTime() + " Login - User: " + username + " Fail! (wrong password "
							+ (countWrong + 1) + "/3)");
					countWrong++;
					if (countWrong == 3) {
						JOptionPane.showMessageDialog(this,
								"YOU HAVE ENTERED WRONG PASSWORD 3 TIMES. APPLICATION WILL FREEZE FEW SECOND!");
						System.out.println(upTime() + " Login - User: " + username + " Fail! (freeze 5 seconds, "
								+ (countWrong2 + 1) + "/3)");
						countWrong = 0;
						countWrong2++;
						wait(5000);
					}
					if (countWrong2 == 3) {
						System.out.println(upTime() + " Login - User: " + username + " Fail! (account locked)");
						JOptionPane.showMessageDialog(this, "YOUR ACCOUNT HAS BEEN LOCKED! PLEASE CONTACT ADMIN!");
						countWrong2 = 0;
						for (User<String, String> u : loginsystem.getUserList()) {
							if (u.getUser().equals(username)) {
								u.setStatus("LOCKED");
								loginsystem.addUser(u, false);
								return;
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(this, "NOT FOUND ACCOUNT. Check your username again!");
				}
			}
		} else if (e.getSource() == createUser || e.getSource() == regpass || e.getSource() == reguser
				|| e.getSource() == repass) {
			String username = reguser.getText();
			String password = regpass.getText();
			if (username.trim().isEmpty() || password.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "PLEASE ENTER CHARACTERS");
				return;
			}
			if (!password.equals(repass.getText())) {
				JOptionPane.showMessageDialog(this, "PASSWORDS DO NOT MATCH!");
				return;
			}
			for (User<String, String> u : loginsystem.getUserList()) {
				if (u.getUser().equals(username)) {
					JOptionPane.showMessageDialog(this, "USERNAME ALREADY EXISTS!");
					return;
				}
			}
			loginsystem.addUser(new User<String, String>(username, password, 0.0, null), false);
			System.out.println(upTime() + " Register - User: " + username + " (registered successfully)");
			JOptionPane.showMessageDialog(this, "REGISTER SUCCESSFULLY");
			getContentPane().remove(registerPanel);
			getContentPane().add(loginPanel);
			user.setText("");
			pass.setText("");
			revalidate();
			repaint();
		} else if (e.getSource() == printList) {
			printList.setText("Reload");
			ImageIcon iconloading = new ImageIcon(getClass().getResource("/main/resources/ui/loading.gif"));
			panelLoading = new JPanel();
			panelLoading.add(new JLabel(iconloading), BorderLayout.CENTER);
			getContentPane().remove(panelMN);
			getContentPane().add(panelLoading);
			revalidate();
			repaint();
			SwingWorker<Void, Void> worker = new SwingWorker<>() {
				@Override
				protected Void doInBackground() throws Exception {
					Thread.sleep(1000);
					return null;
				}

				@Override
				protected void done() {
					getContentPane().remove(panelLoading);
					getContentPane().add(panelMN);
					revalidate();
					repaint();
					tableModel.setRowCount(0); // Clear existing data
					for (User<String, String> u : loginsystem.getUserList()) {
						tableModel.addRow(new Object[] { u.getUser(), u.getPass(), u.getScore(), u.getStatus() });
					}
				}
			};
			worker.execute();
		} else if (e.getSource() == addUser) {
			String username = JOptionPane.showInputDialog(this, "Enter username:");
			if (username == null || username.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "PLEASE ENTER CHARACTERS");
				return;
			}
			String password = JOptionPane.showInputDialog(this, "Enter password:");
			if (password == null || password.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "PLEASE ENTER CHARACTERS");
				return;
			}
			loginsystem.addUser(new User<String, String>(username, password, 0.0, null), true);
			printList.doClick();
		} else if (e.getSource() == delUser) {
			String username = JOptionPane.showInputDialog(this, "Enter username to delete:");
			if (username != null && !username.trim().isEmpty()) {
				loginsystem.deleteUser(username);
				printList.doClick();
			} else {
				JOptionPane.showMessageDialog(this, "Invalid username.");
			}

		} else if (e.getSource() == showApp) {
			testExam = new ExamTest();
			testExam.setTitle("Thi - User: root");
			testExam.setVisible(true);
			testExam.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					try {
						for (User<String, String> u : loginsystem.getUserList()) {
							if (u.getUser().equals("root")) {
								u.setScore(testExam.getScore());
								u.setStatus(testExam.getStatus());
								loginsystem.addUser(u, false);
							}
						}
					} catch (Exception ex) {
						System.out.println();
					}
				}
			});
		} else if (e.getSource() == logout) {
			System.out.println(upTime() + " Logout - User: root (log out)");
			JOptionPane.showMessageDialog(this, "LOGOUT SUCCESSFULLY");
			isRoot = false;
			getContentPane().remove(panelMN);
			ImageIcon iconloading = new ImageIcon(getClass().getResource("/main/resources/ui/loading.gif"));
			panelLoading = new JPanel();
			panelLoading.add(new JLabel(iconloading), BorderLayout.CENTER);
			getContentPane().add(panelLoading);
			revalidate();
			repaint();
			SwingWorker<Void, Void> loadingWorker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					Thread.sleep(1500);
					return null;
				}

				@Override
				protected void done() {
					getContentPane().remove(panelLoading);
					getContentPane().add(loginPanel);
					revalidate();
					repaint();
				}
			};
			loadingWorker.execute();
			menuUser.remove(itemPrintList);
			menuUser.remove(itemAddUser);
			menuUser.remove(itemDelUser);
			menuUser.remove(itemShowApp);
			menuUser.remove(itemUnlock);
			menuUser.remove(itemLock);
			menuUser.remove(itemLogout);
			menuUser.add(itemNonRoot);
			setTitle("Login");
			user.setText("");
			pass.setText("");
			revalidate();
			repaint();
		} else if (e.getSource() == unlock) {
			String username = JOptionPane.showInputDialog(this, "Enter username to unlock:");
			boolean userFound = false;
			for (User<String, String> u : loginsystem.getUserList()) {
				if (u.getUser().equals(username)) {
					userFound = true;
					if (u.getStatus() == null) {
						JOptionPane.showMessageDialog(this, "ACCOUNT IS NOT LOCKED");
						System.out.println(upTime() + " Unlock - User: " + username + " (not locked)");
						return;
					} else {
						u.setStatus(null);
						JOptionPane.showMessageDialog(this, "UNLOCKED SUCCESSFULLY");
						u.setScore(0.0);
						System.out.println(upTime() + " Unlock - User: " + username + " (unlocked successfully)");
						loginsystem.addUser(u, false);
						printList.doClick();
						return;
					}
				}
			}
			if (!userFound) {
				JOptionPane.showMessageDialog(this, "NOT FOUND ACCOUNT");
			}
		} else if (e.getSource() == lock) {
			String username = JOptionPane.showInputDialog(this, "Enter username to lock:");
			boolean userFound = false;
			for (User<String, String> u : loginsystem.getUserList()) {
				if (u.getUser().equals(username)) {
					userFound = true;
					if (u.getUser().equals("root")) {
						JOptionPane.showMessageDialog(this, "YOU CAN NOT LOCK ROOT ACCOUNT");
						System.out.println(upTime() + " Lock - User: root (cannot lock root account)");
						return;
					} else if (u.getStatus() != null) {
						JOptionPane.showMessageDialog(this, "ACCOUNT IS ALREADY LOCKED");
						System.out.println(upTime() + " Lock - User: " + username + " (already locked)");
						return;
					} else {
						u.setStatus("LOCKED");
						JOptionPane.showMessageDialog(this, "LOCKED SUCCESSFULLY");
						System.out.println(upTime() + " Lock - User: " + username + " (locked successfully)");
						u.setScore(0.0);
						loginsystem.addUser(u, false);
						printList.doClick();
						return;
					}
				}
			}
			if (!userFound) {
				JOptionPane.showMessageDialog(this, "NOT FOUND ACCOUNT");
			}
		}
	}

	// Method wait for ms
	public void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	// Method format time
	public String upTime() {
		dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String dtfor = dt.format(format);
		return dtfor;
	}

	private static PrintStream originalOut;
	private static PrintStream originalErr;
	private static final String LOG_DIRECTORY = "src/main/logs/";

	// Main
	public static void main(String[] args) {
		originalOut = System.out;
		originalErr = System.err;

		try {
			File logDir = new File(LOG_DIRECTORY);
			if (!logDir.exists()) {
				logDir.mkdirs();
			}

			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			String logFileName = LOG_DIRECTORY + File.separator + "app_log_" + now.format(formatter) + ".txt";

			PrintStream fileOut = new PrintStream(new FileOutputStream(logFileName, true));
			System.setOut(fileOut);
			System.setErr(fileOut);

		} catch (Exception e) {
			originalErr.println("Error redirecting console output to file: " + e.getMessage());
			e.printStackTrace(originalErr);
		}
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new SwingLogin();
	}
}