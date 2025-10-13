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
    
    public JPanel loginPanel, registerPanel, panelMN, panelM3, panelLoading;
    public JTextField user, reguser;
    public JPasswordField pass, regpass, repass;
    public JButton createUser, login, register, loginInReG, printList, addUser, delUser, showApp, unlock, lock, logout,
            hidePass, hideRePass, hideRegPass;
    public JMenuItem itemPrintList, itemAddUser, itemDelUser, itemShowApp, itemUnlock, itemLock, itemLogout, itemNonRoot,
            itemLogin, itemRegister, itemExit, itemAbout;
    public JTable userTable;
    public DefaultTableModel tableModel;
    public JMenu menuUser;
    
    public boolean isRoot = false;
    public JLabel labelLoading;
    private JLabel labelLogin, labelRegister, labelNote, labelCopyright;
    private JMenuBar barmenu;
    private JMenu menuOption, menuHelp;
    
    public SwingAppUI(DataProcess loginsystem) throws HeadlessException {
        super();
        setSize(550, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setTitle("Login");
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/resources/common/icon.jpg"));
        setIconImage(icon.getImage());
        
        initComponents();
        
    }

    private void initComponents() {
        // --- UI LOGIN ---
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(6, 1, 10, 10));
        
        labelLogin = new JLabel("LOGIN");
        labelLogin.setFont(new Font("Serif", 1, 25));
        
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
        
        hidePass = new JButton("⦿");
        hidePass.setBackground(Color.white);
        hidePass.setFont(pass.getFont().deriveFont(9f));
        
        JPanel panel3 = new JPanel();
        panel3.add(pass);
        panel3.add(hidePass);
        panel3.setBackground(Color.white);
        
        login = new JButton("Login");
        login.setBackground(Color.green);
        login.setFont(new Font("Serif", 0, 13));
        
        register = new JButton("Register");
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
        
        hideRegPass = new JButton("⦿");
        hideRegPass.setBackground(Color.white);
        hideRegPass.setFont(regpass.getFont().deriveFont(9f));
        
        JPanel panel7 = new JPanel();
        panel7.add(regpass);
        panel7.add(hideRegPass);
        panel7.setBackground(Color.white);
        
        repass = new JPasswordField(17);
        repass.setBorder(BorderFactory.createTitledBorder("RePassword"));
        repass.setBackground(Color.white);
        
        hideRePass = new JButton("⦿");
        hideRePass.setBackground(Color.white);
        hideRePass.setFont(repass.getFont().deriveFont(9f));
        
        JPanel panel8 = new JPanel();
        panel8.add(repass);
        panel8.add(hideRePass);
        panel8.setBackground(Color.white);
        
        loginInReG = new JButton("Login");
        loginInReG.setFont(new Font("Serif", 0, 10));
        
        createUser = new JButton("Create");
        createUser.setBackground(Color.blue);
        createUser.setFont(new Font("Serif", 0, 13));
        
        JPanel panel9 = new JPanel();
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
        
        // --- UI MANAGEMENT ---
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
        printList.setFont(printList.getFont().deriveFont(10.5f));
        
        addUser = new JButton("Add User");
        addUser.setBackground(Color.green);
        addUser.setFont(addUser.getFont().deriveFont(10.5f));
        
        delUser = new JButton("Delete User");
        delUser.setBackground(Color.red);
        delUser.setFont(delUser.getFont().deriveFont(10.5f));
        
        showApp = new JButton("Show App");
        showApp.setBackground(Color.blue);
        showApp.setFont(showApp.getFont().deriveFont(10.5f));
        
        unlock = new JButton("Unlock");
        unlock.setBackground(Color.pink);
        unlock.setFont(unlock.getFont().deriveFont(10.5f));
        
        lock = new JButton("Lock");
        lock.setBackground(Color.red);
        lock.setFont(lock.getFont().deriveFont(10.5f));
        
        logout = new JButton("Logout");
        logout.setBackground(Color.orange);
        logout.setFont(logout.getFont().deriveFont(10.5f));
        
        panelM3.add(printList);
        panelM3.add(addUser);
        panelM3.add(delUser);
        panelM3.add(showApp);
        panelM3.add(unlock);
        panelM3.add(lock);
        panelM3.add(logout);
        panelM3.setBackground(Color.yellow);
        
        panelLoading = new JPanel( new BorderLayout());
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
        
        JPanel panel001 = new JPanel();
        labelCopyright = new JLabel("© • Copyright by river0077");
        panel001.add(labelCopyright);
        panel001.setBackground(Color.yellow);
        
        // --- Menu ---
        barmenu = new JMenuBar();
        menuOption = new JMenu("Option");
        menuUser = new JMenu("User");
        menuHelp = new JMenu("Help");
        
        itemNonRoot = new JMenuItem("You can not access this!");
        itemNonRoot.setEnabled(false);
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
        iconAbout = new ImageIcon(iconAbout.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        itemAbout.setIcon(iconAbout);
        menuHelp.add(itemAbout);
        
        barmenu.add(menuOption);
        barmenu.add(menuUser);
        barmenu.add(menuHelp);
        setJMenuBar(barmenu);
        
        panelMN = new JPanel(new BorderLayout());
        panelMN.add(scrollPane, BorderLayout.CENTER);
        panelMN.add(panelM3, BorderLayout.NORTH);
        panelMN.add(panel001, BorderLayout.PAGE_END);
    }
}