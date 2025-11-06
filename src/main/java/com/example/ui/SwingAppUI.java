package main.java.com.example.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import main.java.com.example.auth.DataProcess;

public class SwingAppUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color BACKGROUND_COLOR = new Color(0x283C4F);
    private static final String COPYRIGHT = "© • Exam App 2025 - Copyright by dxgiang • ©";
    public JPanel loginPanel, registerPanel, panelMN, panelM3, panelLoading;
    public JTextField user, reguser;
    public JPasswordField pass, regpass, repass;
    public JButton createUserButton, loginButton, registerButton, loginInReGButton, printListButton, addUserButton,
            delUserButton, showAppButton, unlockButton, lockButton, logoutButton,
            hidePassButton, hideRePassButton, hideRegPassButton;
    public JMenuItem itemPrintList, itemAddUser, itemDelUser, itemShowApp, itemUnlock, itemLock, itemLogout,
            itemLogin, itemRegister, itemExit, itemAbout, itemEditQues, itemEditUser, itemCheckLog;
    public JMenuItem itemNonRoot = new JMenuItem("You can not access this!"), itemNonRootClone = new JMenuItem("You can not access this!");
    public JTable userTable;
    public DefaultTableModel tableModel;

    public boolean isRoot = false;
    public JLabel labelLoading;
    private JLabel labelLogin, labelRegister, labelNote;
    public JLabel labelAnalysis;
    private JLabel labelCopyright;
    private JMenuBar barmenu;
    public JMenu menuOption, menuUser, menuSettings, menuHelp;

    public SwingAppUI(DataProcess loginsystem) throws HeadlessException {
        super();
        setSize(580, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Login");
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/resources/common/icon.jpg"));
        setIconImage(icon.getImage());
        setBackground(BACKGROUND_COLOR);
        initComponents();

    }

    private void initComponents() {
        // --- UI LOGIN ---
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(6, 1, 10, 10));
        loginPanel.setBackground(Color.white);

        labelLogin = new JLabel("LOGIN");
        labelLogin.setFont(new Font("Serif", 1, 25));
        labelLogin.setForeground(Color.black);
        labelLogin.setBackground(Color.white);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel1.setBackground(Color.white);
        panel1.add(labelLogin);

        user = new JTextField(22);
        user.setBorder(BorderFactory.createTitledBorder("Username"));
        user.setBackground(Color.white);

        JPanel panel2 = new JPanel();
        panel2.add(user);
        panel2.setBackground(Color.white);

        pass = new JPasswordField(17);
        pass.setBorder(BorderFactory.createTitledBorder("Password"));
        pass.setBackground(Color.white);

        hidePassButton = new JButton("⦿");
        hidePassButton.setBackground(BACKGROUND_COLOR);
        hidePassButton.setFont(pass.getFont().deriveFont(10f));

        JPanel panel3 = new JPanel();
        panel3.add(pass);
        panel3.add(hidePassButton);
        panel3.setBackground(Color.white);

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.green);
        loginButton.setFont(new Font("Serif", 0, 13));
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(true);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Serif", 0, 10));
        registerButton.setContentAreaFilled(false);
        registerButton.setOpaque(true);

        JPanel panel4 = new JPanel();
        panel4.add(registerButton);
        panel4.add(loginButton);
        panel4.setBackground(Color.white);

        labelNote = new JLabel(
                "<html>Note: If you enter wrong password 3 times,<br><div style='text-align:center;'>application will freeze few seconds!</div></html>");
        labelNote.setForeground(Color.red);
        labelNote.setFont(new Font("Serif", 0, 13));

        JPanel panel0 = new JPanel();
        panel0.add(labelNote);
        panel0.setBackground(Color.white);

        labelCopyright = new JLabel(COPYRIGHT);
        labelCopyright.setFont(new Font("Serif", 0, 13));
        labelCopyright.setForeground(Color.black);

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
        getContentPane().setBackground(BACKGROUND_COLOR);

        // --- UI REGISTER ---
        registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(6, 1, 10, 10));

        labelRegister = new JLabel("REGISTER");
        labelRegister.setFont(new Font("Serif", 1, 25));

        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel5.add(labelRegister);
        panel5.setBackground(Color.white);

        reguser = new JTextField(22);
        reguser.setBorder(BorderFactory.createTitledBorder("Username"));
        reguser.setBackground(Color.white);

        JPanel panel6 = new JPanel();
        panel6.add(reguser);
        panel6.setBackground(Color.white);

        regpass = new JPasswordField(17);
        regpass.setBorder(BorderFactory.createTitledBorder("Password"));
        regpass.setBackground(Color.white);

        hideRegPassButton = new JButton("⦿");
        hideRegPassButton.setBackground(Color.white);
        hideRegPassButton.setFont(regpass.getFont().deriveFont(10f));

        JPanel panel7 = new JPanel();
        panel7.add(regpass);
        panel7.add(hideRegPassButton);
        panel7.setBackground(Color.white);

        repass = new JPasswordField(17);
        repass.setBorder(BorderFactory.createTitledBorder("RePassword"));
        repass.setBackground(Color.white);

        hideRePassButton = new JButton("⦿");
        hideRePassButton.setBackground(Color.white);
        hideRePassButton.setFont(repass.getFont().deriveFont(9f));

        JPanel panel8 = new JPanel();
        panel8.add(repass);
        panel8.add(hideRePassButton);
        panel8.setBackground(Color.white);

        loginInReGButton = new JButton("Login");
        loginInReGButton.setFont(new Font("Serif", 0, 10));
        loginInReGButton.setContentAreaFilled(false);
        loginInReGButton.setOpaque(true);

        createUserButton = new JButton("Create");
        createUserButton.setBackground(Color.blue);
        createUserButton.setFont(new Font("Serif", 0, 13));
        createUserButton.setContentAreaFilled(false);
        createUserButton.setOpaque(true);

        JPanel panel9 = new JPanel();
        panel9.add(loginInReGButton);
        panel9.add(createUserButton);
        panel9.setBackground(Color.white);

        JPanel panel10 = new JPanel();
        labelCopyright = new JLabel(COPYRIGHT);
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

        // --- UI MANAGEMENT ---
        String[] columnNames = { "Username", "Password", "Score", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setEnabled(true);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setPreferredSize(new Dimension(450, 255));

        panelM3 = new JPanel();
        printListButton = new JButton("Print List");
        printListButton.setBackground(Color.gray);
        printListButton.setFont(printListButton.getFont().deriveFont(10.5f));
        printListButton.setContentAreaFilled(false);
        printListButton.setOpaque(true);

        addUserButton = new JButton("Add User");
        addUserButton.setBackground(Color.green);
        addUserButton.setFont(addUserButton.getFont().deriveFont(10.5f));
        addUserButton.setContentAreaFilled(false);
        addUserButton.setOpaque(true);

        delUserButton = new JButton("Delete User");
        delUserButton.setBackground(Color.red);
        delUserButton.setFont(delUserButton.getFont().deriveFont(10.5f));
        delUserButton.setContentAreaFilled(false);
        delUserButton.setOpaque(true);

        showAppButton = new JButton("Show App");
        showAppButton.setBackground(Color.blue);
        showAppButton.setFont(showAppButton.getFont().deriveFont(10.5f));
        showAppButton.setContentAreaFilled(false);
        showAppButton.setOpaque(true);

        unlockButton = new JButton("Unlock");
        unlockButton.setBackground(Color.pink);
        unlockButton.setFont(unlockButton.getFont().deriveFont(10.5f));
        unlockButton.setContentAreaFilled(false);
        unlockButton.setOpaque(true);

        lockButton = new JButton("Lock");
        lockButton.setBackground(Color.red);
        lockButton.setFont(lockButton.getFont().deriveFont(10.5f));
        lockButton.setContentAreaFilled(false);
        lockButton.setOpaque(true);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.orange);
        logoutButton.setFont(logoutButton.getFont().deriveFont(10.5f));
        logoutButton.setContentAreaFilled(false);
        logoutButton.setOpaque(true);

        panelM3.add(printListButton);
        panelM3.add(addUserButton);
        panelM3.add(delUserButton);
        panelM3.add(showAppButton);
        panelM3.add(unlockButton);
        panelM3.add(lockButton);
        panelM3.add(logoutButton);
        panelM3.setBackground(Color.yellow);

        panelLoading = new JPanel(new BorderLayout());
        panelLoading.setBackground(Color.yellow);
        labelLoading = new JLabel("");
        labelLoading.setHorizontalAlignment(JLabel.CENTER);
        try {
            ImageIcon iconloading = new ImageIcon(getClass().getResource("/main/resources/ui/loading.gif"));
            iconloading = new ImageIcon(iconloading.getImage().getScaledInstance(550, 380, Image.SCALE_DEFAULT));
            labelLoading.setIcon(iconloading);
        } catch (Exception e) {
            labelLoading.setText("LOADING...");
        }
        panelLoading.add(labelLoading, JLabel.CENTER);

        JPanel panel001 = new JPanel(new BorderLayout());
        labelAnalysis = new JLabel("Locked: 0, Passed: 0, Failed: 0, Cheated: 0", JLabel.CENTER);
        labelCopyright.setFont(new Font("Serif", 0, 13));
        labelCopyright.setForeground(Color.black);
        labelCopyright = new JLabel(COPYRIGHT, JLabel.CENTER);
        panel001.add(labelAnalysis, BorderLayout.NORTH);
        panel001.add(labelCopyright, BorderLayout.SOUTH);
        panel001.setBackground(Color.yellow);

        // --- Menu ---
        itemNonRoot.setEnabled(false);
        barmenu = new JMenuBar();
        menuOption = new JMenu("Option");
        menuUser = new JMenu("User");
        menuHelp = new JMenu("Help");
        menuSettings = new JMenu("Settings");
        menuOption.setMnemonic(KeyEvent.VK_O);
        menuUser.setMnemonic(KeyEvent.VK_U);
        menuHelp.setMnemonic(KeyEvent.VK_H);
        menuSettings.setMnemonic(KeyEvent.VK_S);

        menuUser.add(itemNonRoot);

        itemLogin = new JMenuItem("Login");
        itemLogin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        ImageIcon iconLogin = new ImageIcon(getClass().getResource("/main/resources/ui/login.png"));
        iconLogin = new ImageIcon(iconLogin.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemLogin.setIcon(iconLogin);

        itemRegister = new JMenuItem("Register");
        itemRegister.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        ImageIcon iconRegister = new ImageIcon(getClass().getResource("/main/resources/ui/register.png"));
        iconRegister = new ImageIcon(iconRegister.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemRegister.setIcon(iconRegister);

        itemExit = new JMenuItem("Exit");
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        ImageIcon iconExit = new ImageIcon(getClass().getResource("/main/resources/ui/exit.png"));
        iconExit = new ImageIcon(iconExit.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemExit.setIcon(iconExit);

        itemPrintList = new JMenuItem("Print List");
        itemPrintList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        ImageIcon iconPrintList = new ImageIcon(getClass().getResource("/main/resources/auth/printlist.png"));
        iconPrintList = new ImageIcon(iconPrintList.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemPrintList.setIcon(iconPrintList);

        itemAddUser = new JMenuItem("Add User");
        itemAddUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        ImageIcon iconAddUser = new ImageIcon(getClass().getResource("/main/resources/auth/add.png"));
        iconAddUser = new ImageIcon(iconAddUser.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemAddUser.setIcon(iconAddUser);

        itemDelUser = new JMenuItem("Delete User");
        itemDelUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        ImageIcon iconDelUser = new ImageIcon(getClass().getResource("/main/resources/auth/delete.png"));
        iconDelUser = new ImageIcon(iconDelUser.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemDelUser.setIcon(iconDelUser);

        itemShowApp = new JMenuItem("Show App");
        itemShowApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        ImageIcon iconShowApp = new ImageIcon(getClass().getResource("/main/resources/exam/showapp.png"));
        iconShowApp = new ImageIcon(iconShowApp.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemShowApp.setIcon(iconShowApp);

        itemUnlock = new JMenuItem("Unlock");
        itemUnlock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        ImageIcon iconUnlock = new ImageIcon(getClass().getResource("/main/resources/auth/unlock.png"));
        iconUnlock = new ImageIcon(iconUnlock.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemUnlock.setIcon(iconUnlock);

        itemLock = new JMenuItem("Lock");
        itemLock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        ImageIcon iconLock = new ImageIcon(getClass().getResource("/main/resources/auth/lock.png"));
        iconLock = new ImageIcon(iconLock.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemLock.setIcon(iconLock);

        itemLogout = new JMenuItem("Logout");
        itemLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        ImageIcon iconLogout = new ImageIcon(getClass().getResource("/main/resources/ui/logout.png"));
        iconLogout = new ImageIcon(iconLogout.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemLogout.setIcon(iconLogout);

        menuOption.add(itemLogin);
        menuOption.add(itemRegister);
        menuOption.add(itemExit);

        itemAbout = new JMenuItem("About");
        ImageIcon iconAbout = new ImageIcon(getClass().getResource("/main/resources/common/about.png"));
        itemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        iconAbout = new ImageIcon(iconAbout.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemAbout.setIcon(iconAbout);
        menuHelp.add(itemAbout);

        itemNonRootClone.setEnabled(false);
        menuSettings.add(itemNonRootClone);
        itemEditQues = new JMenuItem("Edit Questions");
        ImageIcon iconQues = new ImageIcon(getClass().getResource("/main/resources/ui/question.png"));
        itemEditQues.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        iconQues = new ImageIcon(iconQues.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemEditQues.setIcon(iconQues);


        itemEditUser = new JMenuItem("Edit Users");
        ImageIcon iconUser = new ImageIcon(getClass().getResource("/main/resources/ui/user.png"));
        itemEditUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        iconUser = new ImageIcon(iconUser.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemEditUser.setIcon(iconUser);

        itemCheckLog = new JMenuItem("Check Log");
        ImageIcon iconLog = new ImageIcon(getClass().getResource("/main/resources/ui/log.png"));
        itemCheckLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        iconLog = new ImageIcon(iconLog.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemCheckLog.setIcon(iconLog);

        barmenu.add(menuOption);
        barmenu.add(menuHelp);
        barmenu.add(menuUser);
        barmenu.add(menuSettings);
        setJMenuBar(barmenu);

        panelMN = new JPanel(new BorderLayout());
        panelMN.add(scrollPane, BorderLayout.CENTER);
        panelMN.add(panelM3, BorderLayout.NORTH);
        panelMN.add(panel001, BorderLayout.PAGE_END);
    }
}