package main.java.com.example.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingWorker;

import main.java.com.example.auth.DataProcess;
import main.java.com.example.auth.User;
import main.java.com.example.exam.ExamTest;
import main.java.com.example.ui.SwingApp;
import main.java.com.example.ui.SwingAppUI;

public class SwingAppController implements ActionListener {
    private SwingAppUI ui;
    private DataProcess loginsystem;
    private ExamTest testExam;
    private int countWrong = 0;
    private int countWrong2 = 0;
    private LocalDateTime dt;

    public SwingAppController(SwingAppUI ui, DataProcess loginsystem) {
        this.ui = ui;
        this.loginsystem = loginsystem;
        addListeners();
    }

    private void addListeners() {
        ui.register.addActionListener(this);
        ui.loginInReG.addActionListener(this);
        ui.login.addActionListener(this);
        ui.pass.addActionListener(this);
        ui.user.addActionListener(this);
        ui.createUser.addActionListener(this);
        ui.regpass.addActionListener(this);
        ui.reguser.addActionListener(this);
        ui.repass.addActionListener(this);
        ui.printList.addActionListener(this);
        ui.addUser.addActionListener(this);
        ui.delUser.addActionListener(this);
        ui.showApp.addActionListener(this);
        ui.unlock.addActionListener(this);
        ui.lock.addActionListener(this);
        ui.logout.addActionListener(this);

        ui.itemLogin.addActionListener(e -> handleMenuItemLogin());
        ui.itemRegister.addActionListener(e -> handleMenuItemRegister());
        ui.itemExit.addActionListener(e -> handleMenuItemExit());
        ui.itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(ui, "Developed by river0077."));
        ui.itemPrintList.addActionListener(e -> ui.printList.doClick());
        ui.itemAddUser.addActionListener(e -> ui.addUser.doClick());
        ui.itemDelUser.addActionListener(e -> ui.delUser.doClick());
        ui.itemShowApp.addActionListener(e -> ui.showApp.doClick());
        ui.itemUnlock.addActionListener(e -> ui.unlock.doClick());
        ui.itemLock.addActionListener(e -> ui.lock.doClick());
        ui.itemLogout.addActionListener(e -> ui.logout.doClick());

        ui.hidePass.addActionListener(e -> togglePasswordVisibility(ui.pass, ui.hidePass));
        ui.hideRePass.addActionListener(e -> togglePasswordVisibility(ui.repass, ui.hideRePass));
        ui.hideRegPass.addActionListener(e -> togglePasswordVisibility(ui.regpass, ui.hideRegPass));

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
        if (e.getSource() == ui.register) {
            handleRegisterTransition();
        } else if (e.getSource() == ui.loginInReG) {
            handleLoginTransition();
        } else if (e.getSource() == ui.login || e.getSource() == ui.pass || e.getSource() == ui.user) {
            handleLoginAttempt();
        } else if (e.getSource() == ui.createUser || e.getSource() == ui.regpass || e.getSource() == ui.reguser || e.getSource() == ui.repass) {
            handleCreateUser();
        } else if (e.getSource() == ui.printList) {
            handlePrintList();
        } else if (e.getSource() == ui.addUser) {
            handleAddUser();
        } else if (e.getSource() == ui.delUser) {
            handleDeleteUser();
        } else if (e.getSource() == ui.showApp) {
            handleShowApp();
        } else if (e.getSource() == ui.logout) {
            handleLogout();
        } else if (e.getSource() == ui.unlock) {
            handleUnlockUser();
        } else if (e.getSource() == ui.lock) {
            handleLockUser();
        }
    }


    private void handleMenuItemLogin() {
        if (ui.isRoot) {
            ui.logout.doClick();
        } else {
            handleLoginTransition();
            clearManagementMenu();
            ui.user.setText("");
            ui.pass.setText("");
            ui.setTitle("Login");
        }
    }

    private void handleMenuItemRegister() {
        if (ui.isRoot) {
            ui.logout.doClick();
        } else {
            handleRegisterTransition();
            clearManagementMenu();
            ui.reguser.setText("");
            ui.regpass.setText("");
            ui.repass.setText("");
            ui.setTitle("Register");
        }
    }
    
    private void handleMenuItemExit() {
        System.out.println(upTime() + " Exit app");
        System.exit(0);
    }
    
    private void clearManagementMenu() {
        ui.menuUser.remove(ui.itemPrintList);
        ui.menuUser.remove(ui.itemAddUser);
        ui.menuUser.remove(ui.itemDelUser);
        ui.menuUser.remove(ui.itemShowApp);
        ui.menuUser.remove(ui.itemUnlock);
        ui.menuUser.remove(ui.itemLock);
        ui.menuUser.remove(ui.itemLogout);
        ui.menuUser.add(ui.itemNonRoot);
    }

    private void togglePasswordVisibility(JPasswordField field, JButton button) {
        if (button.getText().equals("⦿")) {
            button.setText("⦾");
            field.setEchoChar((char) 0); // Show password
        } else {
            button.setText("⦿");
            field.setEchoChar('*'); // Hide password
        }
    }

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

    private void handleLoginTransition() {
        ui.getContentPane().remove(ui.registerPanel);
        ui.getContentPane().add(ui.loginPanel);
        ui.user.setText("");
        ui.pass.setText("");
        ui.setTitle("Login");
        ui.revalidate();
        ui.repaint();
    }

    private void handleLoginAttempt() {
        String username = ui.user.getText();
        String password = new String(ui.pass.getPassword());

        if (loginsystem.authenticate(username, password)) {
            if (username.equals("root")) {
                handleRootLogin(username);
            } else {
                handleRegularUserLogin(username);
            }
        } else {
            handleFailedLogin(username, password);
        }
    }

    private void handleRootLogin(String username) {
        System.out.println(upTime() + " Logined successfully as root");
        JOptionPane.showMessageDialog(ui, "LOGIN AS ROOT");
        ui.isRoot = true;
        countWrong = 0;
        
        ui.getContentPane().remove(ui.loginPanel);
        ui.getContentPane().remove(ui.registerPanel);
        
        ui.menuUser.remove(ui.itemNonRoot);
        ui.menuUser.add(ui.itemPrintList);
        ui.menuUser.add(ui.itemAddUser);
        ui.menuUser.add(ui.itemDelUser);
        ui.menuUser.add(ui.itemShowApp);
        ui.menuUser.add(ui.itemUnlock);
        ui.menuUser.add(ui.itemLock);
        ui.menuUser.add(ui.itemLogout);
        
        ui.setTitle("Management");
        ui.getContentPane().add(ui.panelMN);
        ui.printList.doClick();
        ui.revalidate();
        ui.repaint();
    }

    private void handleRegularUserLogin(String username) {
        for (User<String, String> u : loginsystem.getUserList()) {
            if (u.getUser().equals(username)) {
                if (u.getStatus() != null && (u.getStatus().equals("PASS") || u.getStatus().equals("FAIL") || u.getStatus().equals("CHEAT"))) {
                    JOptionPane.showMessageDialog(ui, "YOU HAVE TAKEN THE EXAM! AUTO LOG OUT!");
                    System.out.println(upTime() + " Logout - User: " + username + "(auto log out)");
                    ui.user.setText("");
                    ui.pass.setText("");
                    return;
                } else if (u.getStatus() != null && u.getStatus().equals("LOCKED")) {
                    JOptionPane.showMessageDialog(ui, "YOUR ACCOUNT HAS BEEN LOCKED! PLEASE CONTACT ADMIN!");
                    System.out.println(upTime() + " Login - User: " + username + " Fail! (account locked)");
                    ui.user.setText("");
                    ui.pass.setText("");
                    return;
                }
            }
        }
        
        JOptionPane.showMessageDialog(ui, "LOGIN SUCCESSFULLY");
        System.out.println(upTime() + " Login - User: " + username + " (logined successfully)");
        countWrong = 0;
        ui.user.setText("");
        ui.pass.setText("");

        ExamTest exam = new ExamTest();
        exam.setUserName(username);
        exam.setTitle("Exam Text - User: " + username);
        exam.setVisible(true);
        
        exam.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (User<String, String> u : loginsystem.getUserList()) {
                            if (u.getUser().equals(username)) {
                                u.setScore(exam.getScore());
                                u.setStatus(exam.getStatus());
                                loginsystem.addUser(u, false);
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

    private void handleFailedLogin(String username, String password) {
        if (loginsystem.wrongPass(username, password)) {
            JOptionPane.showMessageDialog(ui, "WRONG PASSWORD");
            System.out.println(upTime() + " Login - User: " + username + " Fail! (wrong password " + (countWrong + 1) + "/3)");
            countWrong++;
            
            if (countWrong == 3) {
                JOptionPane.showMessageDialog(ui, "YOU HAVE ENTERED WRONG PASSWORD 3 TIMES. APPLICATION WILL FREEZE FEW SECOND!");
                System.out.println(upTime() + " Login - User: " + username + " Fail! (freeze 5 seconds, " + (countWrong2 + 1) + "/3)");
                countWrong = 0;
                countWrong2++;
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.sleep(5000);
                        return null;
                    }
                    @Override
                    protected void done() {
                        if (countWrong2 == 3) {
                            handleAccountLockout(username);
                        }
                    }
                }.execute();
            }
        } else {
            JOptionPane.showMessageDialog(ui, "NOT FOUND ACCOUNT. Check your username again!");
        }
    }

    private void handleAccountLockout(String username) {
        System.out.println(upTime() + " Login - User: " + username + " Fail! (account locked)");
        JOptionPane.showMessageDialog(ui, "YOUR ACCOUNT HAS BEEN LOCKED! PLEASE CONTACT ADMIN!");
        countWrong2 = 0;
        for (User<String, String> u : loginsystem.getUserList()) {
            if (u.getUser().equals(username)) {
                u.setStatus("LOCKED");
                loginsystem.addUser(u, false);
                return;
            }
        }
    }

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
        for (User<String, String> u : loginsystem.getUserList()) {
            if (u.getUser().equals(username)) {
                JOptionPane.showMessageDialog(ui, "USERNAME ALREADY EXISTS!");
                return;
            }
        }
        
        loginsystem.addUser(new User<String, String>(username, password, 0.0, null), false);
        System.out.println(upTime() + " Register - User: " + username + " (registered successfully)");
        JOptionPane.showMessageDialog(ui, "REGISTER SUCCESSFULLY");
        
        handleLoginTransition();
    }

    private void handlePrintList() {
        ui.printList.setText("Reload");
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
                Thread.sleep(1000);
                return null;
            }

            @Override
            protected void done() {
                ui.getContentPane().remove(ui.panelLoading);
                ui.getContentPane().add(ui.panelMN);
                ui.revalidate();
                ui.repaint();
                
                ui.tableModel.setRowCount(0);
                for (User<String, String> u : loginsystem.getUserList()) {
                    ui.tableModel.addRow(new Object[] { u.getUser(), u.getPass(), u.getScore(), u.getStatus() });
                }
            }
        };
        worker.execute();
    }

    private void handleAddUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username:");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ui, "PLEASE ENTER CHARACTERS");
            return;
        }
        String password = JOptionPane.showInputDialog(ui, "Enter password:");
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ui, "PLEASE ENTER CHARACTERS");
            return;
        }

        for (User<String, String> u : loginsystem.getUserList()) {
            if (u.getUser().equals(username)) {
                JOptionPane.showMessageDialog(ui, "USERNAME ALREADY EXISTS! Updating user details.");
                break;
            }
        }
        
        loginsystem.addUser(new User<String, String>(username, password, 0.0, null), true);
        ui.printList.doClick();
    }

    private void handleDeleteUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username to delete:");
        if (username != null && !username.trim().isEmpty()) {
            loginsystem.deleteUser(username);
            ui.printList.doClick();
        } else {
            JOptionPane.showMessageDialog(ui, "Invalid username.");
        }
    }

    private void handleShowApp() {
        testExam = new ExamTest();
        testExam.setTitle("Exam Text - User: root");
        testExam.setVisible(true);
        testExam.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (User<String, String> u : loginsystem.getUserList()) {
                            if (u.getUser().equals("root")) {
                                u.setScore(testExam.getScore());
                                u.setStatus(testExam.getStatus());
                                loginsystem.addUser(u, false);
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

    private void handleLogout() {
        System.out.println(upTime() + " Logout - User: root (log out)");
        JOptionPane.showMessageDialog(ui, "LOGOUT SUCCESSFULLY");
        ui.isRoot = false;
        
        ui.getContentPane().remove(ui.panelMN);
        
        // Hiển thị loading
        ImageIcon iconloading = new ImageIcon(ui.getClass().getResource("/main/resources/ui/loading.gif"));
        ui.labelLoading.setIcon(iconloading);
        ui.panelLoading.setVisible(true);
        ui.getContentPane().add(ui.panelLoading);
        ui.revalidate();
        ui.repaint();

        SwingWorker<Void, Void> loadingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(1500);
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

    private void handleUnlockUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username to unlock:");
        boolean userFound = false;
        if (username == null || username.trim().isEmpty()) return;
        
        for (User<String, String> u : loginsystem.getUserList()) {
            if (u.getUser().equals(username)) {
                userFound = true;
                if (u.getStatus() == null) {
                    JOptionPane.showMessageDialog(ui, "ACCOUNT IS NOT LOCKED OR IS ALREADY PASSED/FAILED/CHEATED");
                    System.out.println(upTime() + " Unlock - User: " + username + " (not locked)");
                    return;
                } else if (!(u.getStatus() == null)) {
                    u.setStatus(null);
                    u.setScore(0.0);
                    loginsystem.addUser(u, false);
                    JOptionPane.showMessageDialog(ui, "UNLOCKED SUCCESSFULLY! Score/Status reset.");
                    System.out.println(upTime() + " Unlock - User: " + username + " (unlocked successfully)");
                    ui.printList.doClick();
                    return;
                }
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(ui, "NOT FOUND ACCOUNT");
        }
    }

    private void handleLockUser() {
        String username = JOptionPane.showInputDialog(ui, "Enter username to lock:");
        boolean userFound = false;
        if (username == null || username.trim().isEmpty()) return;

        for (User<String, String> u : loginsystem.getUserList()) {
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
                    loginsystem.addUser(u, false);
                    JOptionPane.showMessageDialog(ui, "LOCKED SUCCESSFULLY! Score/Status reset.");
                    System.out.println(upTime() + " Lock - User: " + username + " (locked successfully)");
                    ui.printList.doClick();
                    return;
                }
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(ui, "NOT FOUND ACCOUNT");
        }
    }

    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public String upTime() {
        dt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dtfor = dt.format(format);
        return dtfor;
    }
}