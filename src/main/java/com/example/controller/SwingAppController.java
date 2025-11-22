package main.java.com.example.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingWorker;

import main.java.com.example.MainApp;
import main.java.com.example.auth.DataProcess;
import main.java.com.example.auth.User;
import main.java.com.example.exam.ExamTestLogic;
import main.java.com.example.ui.ExamTestUI;
import main.java.com.example.ui.RuleUI;
import main.java.com.example.ui.SwingApp;
import main.java.com.example.ui.SwingAppUI;

public class SwingAppController implements ActionListener {
    // Attributes
    private SwingAppUI ui;
    private DataProcess data;
    private int countWrong = 0;
    private int countWrong2 = 0;
    private LocalDateTime dt;
    private SwingWorker<Void, Void> autoWorker;
    private int msDelay;

    // Constructor
    public SwingAppController(SwingAppUI ui, DataProcess data) {
        this.ui = ui;
        this.data = data;

        addListeners();
    }

    // Methods
    private void addListeners() {
        ui.menuOption.addActionListener(this);
        ui.menuUser.addActionListener(this);
        ui.menuHelp.addActionListener(this);
        ui.registerButton.addActionListener(this);
        ui.loginInReGButton.addActionListener(this);
        ui.loginButton.addActionListener(this);
        ui.pass.addActionListener(this);
        ui.user.addActionListener(this);
        ui.createUserButton.addActionListener(this);
        ui.regpass.addActionListener(this);
        ui.reguser.addActionListener(this);
        ui.repass.addActionListener(this);
        ui.printListButton.addActionListener(this);
        ui.addUserButton.addActionListener(this);
        ui.delUserButton.addActionListener(this);
        ui.showAppButton.addActionListener(this);
        ui.unlockButton.addActionListener(this);
        ui.lockButton.addActionListener(this);
        ui.logoutButton.addActionListener(this);

        ui.itemLogin.addActionListener(e -> handleMenuItemLogin());
        ui.itemRegister.addActionListener(e -> handleMenuItemRegister());
        ui.itemExit.addActionListener(e -> handleMenuItemExit());
        ui.itemRule.addActionListener(e -> new RuleUI().setVisible(true));

        ui.itemPrintList.addActionListener(e -> ui.printListButton.doClick());
        ui.itemAddUser.addActionListener(e -> ui.addUserButton.doClick());
        ui.itemDelUser.addActionListener(e -> ui.delUserButton.doClick());
        ui.itemShowApp.addActionListener(e -> ui.showAppButton.doClick());
        ui.itemUnlock.addActionListener(e -> ui.unlockButton.doClick());
        ui.itemLock.addActionListener(e -> ui.lockButton.doClick());
        ui.itemLogout.addActionListener(e -> ui.logoutButton.doClick());

        ui.itemEditQues.addActionListener(e -> openFile(ExamTestLogic.getDataFilePath()));
        ui.itemEditUser.addActionListener(e -> openFile(data.getDataFilePath()));
        ui.itemCheckLog.addActionListener(e -> openFile(MainApp.getLogFilePath()));

        ui.hidePassButton.addActionListener(e -> togglePasswordVisibility(ui.pass, ui.hidePassButton));
        ui.hideRePassButton.addActionListener(e -> togglePasswordVisibility(ui.repass, ui.hideRePassButton));
        ui.hideRegPassButton.addActionListener(e -> togglePasswordVisibility(ui.regpass, ui.hideRegPassButton));
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(upTime() + " Exit app");
                System.setOut(SwingApp.originalOut);
                System.setErr(SwingApp.originalErr);
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == ui.registerButton) {
            handleRegisterTransition();
        } else if (source == ui.loginInReGButton) {
            handleLoginTransition();
        } else if (source == ui.loginButton || source == ui.pass || source == ui.user) {
            handleLoginAttempt();
        } else if (source == ui.createUserButton || source == ui.regpass || source == ui.reguser
                || source == ui.repass) {
            handleCreateUser();
        } else if (source == ui.printListButton) {
            handlePrintList(msDelay);
        } else if (source == ui.addUserButton) {
            handleAddUser();
        } else if (source == ui.delUserButton) {
            handleDeleteUser();
        } else if (source == ui.showAppButton) {
            handleShowApp();
        } else if (source == ui.logoutButton) {
            handleLogout();
        } else if (source == ui.unlockButton) {
            handleUnlockUser();
        } else if (source == ui.lockButton) {
            handleLockUser();
        }
    }

    // Handle Login Menu Item
    private void handleMenuItemLogin() {
        if (ui.isRoot) {
            ui.logoutButton.doClick();
        } else {
            handleLoginTransition();
            clearManagementMenu();
            ui.user.setText("");
            ui.pass.setText("");
            ui.setTitle("Login");
        }
    }

    // Handle Register Menu Item
    private void handleMenuItemRegister() {
        if (ui.isRoot) {
            ui.logoutButton.doClick();
        } else {
            handleRegisterTransition();
            clearManagementMenu();
            ui.reguser.setText("");
            ui.regpass.setText("");
            ui.repass.setText("");
            ui.setTitle("Register");
        }
    }

    // Handle Exit Menu Item
    private void handleMenuItemExit() {
        System.out.println(upTime() + " Exit app");
        System.exit(0);
    }

    // Clear Management Menu
    private void clearManagementMenu() {
        ui.menuUser.remove(ui.itemPrintList);
        ui.menuUser.remove(ui.itemAddUser);
        ui.menuUser.remove(ui.itemDelUser);
        ui.menuUser.remove(ui.itemShowApp);
        ui.menuUser.remove(ui.itemUnlock);
        ui.menuUser.remove(ui.itemLock);
        ui.menuUser.remove(ui.itemLogout);
        ui.menuUser.add(ui.itemNonRoot);
        ui.menuSettings.remove(ui.itemEditQues);
        ui.menuSettings.remove(ui.itemEditUser);
        ui.menuSettings.remove(ui.itemCheckLog);
        ui.menuSettings.add(ui.itemNonRootClone);
    }

    // Toggle Password Visibility
    private void togglePasswordVisibility(JPasswordField field, JButton button) {
        if (button.getText().equals("⦿")) {
            button.setText("⦾");
            field.setEchoChar((char) 0); // Show password
        } else {
            button.setText("⦿");
            field.setEchoChar('*'); // Hide password
        }
    }

    // Register Transition
    private void handleRegisterTransition() {
        ui.getContentPane().remove(ui.loginPanel);
        ui.getContentPane().add(ui.registerPanel);
        ui.reguser.setText("");
        ui.regpass.setText("");
        ui.repass.setText("");
        ui.setTitle("Register");
        ui.revalidate();
        ui.repaint();
    }

    // Login Transition
    private void handleLoginTransition() {
        ui.getContentPane().remove(ui.registerPanel);
        ui.getContentPane().add(ui.loginPanel);
        ui.user.setText("");
        ui.pass.setText("");
        ui.setTitle("Login");
        ui.revalidate();
        ui.repaint();
    }

    // Handle Login Attempt
    private void handleLoginAttempt() {
        String username = ui.user.getText();
        String password = new String(ui.pass.getPassword());

        if (data.authenticate(username, password)) {
            if (username.equals("root")) {
                handleRootLogin(username);
            } else {
                if (data.isAccountLocked(username)) {
                    JOptionPane.showMessageDialog(ui, "YOUR ACCOUNT IS LOCKED! PLEASE CONTACT ADMIN!");
                    System.out.println(upTime() + " Login - User: " + username + " Fail! (account locked)");
                    ui.user.setText("");
                    ui.pass.setText("");
                    ui.user.requestFocus();
                    return;
                } else if (data.isAccountCompleted(username)) {
                    JOptionPane.showMessageDialog(ui,
                            "YOUR ACCOUNT HAS COMPLETED THE EXAM! PLEASE CONTACT ADMIN TO UNLOCK!");
                    System.out.println(upTime() + " Login - User: " + username + " Fail! (account completed)");
                    ui.user.setText("");
                    ui.pass.setText("");
                    ui.user.requestFocus();
                    return;
                }
                handleRegularUserLogin(username);
            }
        } else {
            handleFailedLogin(username, password);
        }
    }

    // Handle Root Login
    private void handleRootLogin(String username) {
        msDelay = 500;
        System.out.println(upTime() + " Logined successfully as root");
        JOptionPane.showMessageDialog(ui, "LOGIN AS ROOT");
        ui.isRoot = true;
        countWrong = 0;

        ui.getContentPane().remove(ui.loginPanel);
        ui.getContentPane().remove(ui.registerPanel);

        ui.menuUser.remove(ui.itemNonRoot);
        ui.menuSettings.remove(ui.itemNonRootClone);
        ui.menuUser.add(ui.itemPrintList);
        ui.menuUser.add(ui.itemAddUser);
        ui.menuUser.add(ui.itemDelUser);
        ui.menuUser.add(ui.itemShowApp);
        ui.menuUser.add(ui.itemUnlock);
        ui.menuUser.add(ui.itemLock);
        ui.menuUser.add(ui.itemLogout);
        ui.menuSettings.add(ui.itemEditQues);
        ui.menuSettings.add(ui.itemEditUser);
        ui.menuSettings.add(ui.itemCheckLog);

        ui.setTitle("Management");
        ui.getContentPane().add(ui.panelMN);
        ui.printListButton.doClick();
        ui.revalidate();
        ui.repaint();
        if (ui.getTitle().equals("Management")) {
            autoPrintList();
        } else {
            return;
        }
    }

    // Handle Regular User Login
    private void handleRegularUserLogin(String username) {
        ExamTestUI currentExamTestUI = new ExamTestUI();
        ExamTestLogic currentExamLogic = currentExamTestUI.logic;
        JOptionPane.showMessageDialog(ui, "LOGIN SUCCESSFULLY. User: " + username);
        System.out.println(upTime() + " Login - User: " + username + " (logined successfully)");
        for (User u : data.getUserList()) {
            if (u.getUser().equals(username)) {
                u.setStatus("~");
                data.addUser(u, false);
            }
        }
        countWrong = 0;
        ui.user.setText("");
        ui.pass.setText("");

        currentExamTestUI.setUserName(username);
        currentExamTestUI.setVisible(true);
        currentExamTestUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (User u : data.getUserList()) {
                            if (u.getUser().equals(username)) {
                                u.setScore(currentExamLogic.getScore());
                                u.setStatus(currentExamLogic.getStatus());
                                data.addUser(u, false);
                                break;
                            }
                        }
                        return null;
                    }
                };
                worker.execute();
            }
        });
    }

    // Handle Failed Login
    private void handleFailedLogin(String username, String password) {
        if (data.wrongPass(username, password)) {
            JOptionPane.showMessageDialog(ui, "WRONG PASSWORD");
            System.out.println(
                    upTime() + " Login - User: " + username + " Fail! (wrong password " + (countWrong + 1) + "/3)");
            countWrong++;

            if (countWrong == 3) {
                JOptionPane.showMessageDialog(ui,
                        "YOU HAVE ENTERED WRONG PASSWORD 3 TIMES. APPLICATION WILL FREEZE FEW SECOND!");
                System.out.println(upTime() + " Login - User: " + username + " Fail! (freeze 5 seconds, "
                        + (countWrong2 + 1) + "/3)");
                wait(5000);
                countWrong = 0;
                countWrong2++;
                if (countWrong2 == 3) {
                    handleAccountLockout(username);
                }
            }
        } else {
            JOptionPane.showMessageDialog(ui, "NOT FOUND ACCOUNT. Check your username again!");
        }
    }

    // Handle Account Lockout
    private void handleAccountLockout(String username) {
        System.out.println(upTime() + " Login - User: " + username + " Fail! (account locked)");
        JOptionPane.showMessageDialog(ui, "YOUR ACCOUNT HAS BEEN LOCKED! PLEASE CONTACT ADMIN!");
        for (User u : data.getUserList()) {
            if (u.getUser().equals(username)) {
                u.setStatus("LOCKED");
                data.addUser(u, false);
                ui.user.setText("");
                ui.pass.setText("");
                ui.user.requestFocus();
                return;
            }
        }
        countWrong2 = 0;
    }

    // Create User
    private void handleCreateUser() {
        String username = ui.reguser.getText();
        String password = new String(ui.regpass.getPassword());
        String rePassword = new String(ui.repass.getPassword());

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ui, "PLEASE ENTER CHARACTERS");
            return;
        }
        if (!password.equals(rePassword)) {
            JOptionPane.showMessageDialog(ui, "PASSWORDS DO NOT MATCH!");
            return;
        }
        for (User u : data.getUserList()) {
            if (u.getUser().equals(username)) {
                JOptionPane.showMessageDialog(ui, "USERNAME ALREADY EXISTS!");
                return;
            }
        }

        data.addUser(new User(data.returnNewID(), username, password, 0.0, null), false);
        System.out.println(upTime() + " Register - User: " + username + " (registered successfully)");
        JOptionPane.showMessageDialog(ui, "REGISTER SUCCESSFULLY");

        handleLoginTransition();
    }

    // Print List
    private void handlePrintList(int ms) {
        msDelay = ms;
        ui.printListButton.setText("Reload");
        ImageIcon iconloading = new ImageIcon(ui.getClass().getResource("/main/resources/ui/loading.gif"));
        ui.labelLoading.setIcon(iconloading);
        ui.panelLoading.setVisible(true);

        ui.getContentPane().remove(ui.panelMN);
        ui.getContentPane().add(ui.panelLoading);
        ui.revalidate();
        ui.repaint();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(msDelay);
                return null;
            }

            @Override
            protected void done() {
                ui.getContentPane().remove(ui.panelLoading);
                ui.getContentPane().add(ui.panelMN);
                updateAnalysisLabel();
                ui.revalidate();
                ui.repaint();
                List<User> listUsers = data.getUserList();
                ui.tableModel.setRowCount(0);
                listUsers.sort(new Comparator<User>() {

                    @Override
                    public int compare(User u1, User u2) {
                        return Integer.compare(u1.getId(), u2.getId());
                    }

                });
                for (User u : listUsers) {
                    ui.tableModel
                            .addRow(new Object[] { u.getId(), u.getUser(), u.getPass(), u.getScore(), u.getStatus() });
                }
            }
        };
        worker.execute();
    }

    // Auto printList 10 seconds
    private void autoPrintList() {
        msDelay = 00;
        autoWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (!isCancelled()) {
                    Thread.sleep(10000);
                    if (!isCancelled()) {
                        ui.printListButton.doClick();
                    }
                }
                return null;
            }
        };
        autoWorker.execute();
    }

    // Add User
    private void handleAddUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username:");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ui, "PLEASE ENTER CHARACTERS");
            return;
        }

        for (User u : data.getUserList()) {
            if (u.getUser().equals(username)) {
                JOptionPane.showMessageDialog(ui, "USERNAME ALREADY EXISTS! Updating user details.");
                return;
            }
        }

        String password = JOptionPane.showInputDialog(ui, "Enter password:");
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ui, "PLEASE ENTER CHARACTERS");
            return;
        }

        data.addUser(new User(data.returnNewID(), username, password, 0.0, null), true);
        ui.printListButton.doClick();
    }

    // Delete User
    private void handleDeleteUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username to delete:");
        if (username != null && !username.trim().isEmpty()) {
            data.deleteUser(username);
            ui.printListButton.doClick();
        } else {
            JOptionPane.showMessageDialog(ui, "Invalid username.");
        }
    }

    // Show Exam App
    private void handleShowApp() {
        ExamTestUI tempExamUI = new ExamTestUI();
        ExamTestLogic tempExamLogic = tempExamUI.logic;

        tempExamUI.setUserName("root");
        tempExamUI.setTitle("Exam Text - User: root");
        tempExamUI.setVisible(true);

        tempExamUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (User u : data.getUserList()) {
                            if (u.getUser().equals("root")) {
                                u.setScore(tempExamLogic.getScore());
                                u.setStatus(tempExamLogic.getStatus());
                                data.addUser(u, false);
                                break;
                            }
                        }
                        return null;
                    }

                    protected void done() {
                        ui.printListButton.doClick();
                    }
                };
                worker.execute();
            }
        });
    }

    // Logout
    private void handleLogout() {
        System.out.println(upTime() + " Logout - User: root (log out)");
        JOptionPane.showMessageDialog(ui, "LOGOUT SUCCESSFULLY");
        ui.isRoot = false;

        if (autoWorker != null && !autoWorker.isDone()) {
            autoWorker.cancel(true);
            System.out.println(upTime() + " Auto-refresh worker cancelled.");
        }
        ui.getContentPane().remove(ui.panelMN);

        ImageIcon iconloading = new ImageIcon(ui.getClass().getResource("/main/resources/ui/loading.gif"));
        ui.labelLoading.setIcon(iconloading);
        ui.panelLoading.setVisible(true);
        ui.getContentPane().add(ui.panelLoading);
        ui.revalidate();
        ui.repaint();

        SwingWorker<Void, Void> loadingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(500);
                return null;
            }

            @Override
            protected void done() {
                ui.getContentPane().remove(ui.panelLoading);
                ui.getContentPane().add(ui.loginPanel);
                clearManagementMenu();
                ui.setTitle("Login");
                ui.user.setText("");
                ui.pass.setText("");
                ui.revalidate();
                ui.repaint();
            }
        };
        loadingWorker.execute();
    }

    // Unlock User
    private void handleUnlockUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username to unlock:");
        boolean userFound = false;
        if (username == null || username.trim().isEmpty())
            return;

        for (User u : data.getUserList()) {
            if (u.getUser().equals(username)) {
                userFound = true;
                if (u.getStatus() == null) {
                    JOptionPane.showMessageDialog(ui, "ACCOUNT IS NOT LOCKED OR IS ALREADY PASSED/FAILED/CHEATED");
                    System.out.println(upTime() + " Unlock - User: " + username + " (not locked)");
                    return;
                } else if (!(u.getStatus() == null)) {
                    u.setStatus(null);
                    u.setScore(0.0);
                    data.addUser(u, false);
                    JOptionPane.showMessageDialog(ui, "UNLOCKED SUCCESSFULLY! Score/Status reset.");
                    System.out.println(upTime() + " Unlock - User: " + username + " (unlocked successfully)");
                    ui.printListButton.doClick();
                    return;
                }
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(ui, "NOT FOUND ACCOUNT");
        }
    }

    // Lock User
    private void handleLockUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username to lock:");
        boolean userFound = false;
        if (username == null || username.trim().isEmpty())
            return;

        for (User u : data.getUserList()) {
            if (u.getUser().equals(username)) {
                userFound = true;
                if (u.getUser().equals("root")) {
                    JOptionPane.showMessageDialog(ui, "YOU CAN NOT LOCK ROOT ACCOUNT");
                    System.out.println(upTime() + " Lock - User: root (cannot lock root account)");
                    return;
                } else if (u.getStatus() != null && u.getStatus().equals("LOCKED")) {
                    JOptionPane.showMessageDialog(ui, "ACCOUNT IS ALREADY LOCKED");
                    System.out.println(upTime() + " Lock - User: " + username + " (already locked)");
                    return;
                } else {
                    u.setStatus("LOCKED");
                    u.setScore(0.0);
                    data.addUser(u, false);
                    JOptionPane.showMessageDialog(ui, "LOCKED SUCCESSFULLY! Score/Status reset.");
                    System.out.println(upTime() + " Lock - User: " + username + " (locked successfully)");
                    ui.printListButton.doClick();
                    return;
                }
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(ui, "NOT FOUND ACCOUNT");
        }
    }

    // Update Analysis Label
    public void updateAnalysisLabel() {
        int locked = data.countLocked();
        int passed = data.countPassed();
        int failed = data.countFailed();
        int cheated = data.countCheated();
        ui.labelAnalysis
                .setText("Locked: " + locked + ", Passed: " + passed + ", Failed: " + failed + ", Cheated: " + cheated);
    }

    // Utility wait method
    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // Get current date time
    public String upTime() {
        dt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dtfor = dt.format(format);
        return dtfor;
    }

    // Open file method
    public void openFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(ui, "File not found: " + filePath);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ui, "Error opening file: " + e.getMessage());
        }
    }
}