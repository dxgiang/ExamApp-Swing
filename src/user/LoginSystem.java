package user;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginSystem extends JFrame {
	private static final long serialVersionUID = 1L;
	private List<User<String, String>> list;

	public LoginSystem() {
		list = new ArrayList<>();
	}

	public void addUser(User<String, String> userAdd, boolean showMes) {
		for (User<String, String> user : list) {
			if (user.getUser().equals(userAdd.getUser())) {
				if (showMes == true) {
					JOptionPane.showMessageDialog(this, "TAI KHOAN DA TON TAI");
					return;
				} else if (userAdd.getUser().equals("")) {
					if (showMes == true) {
						JOptionPane.showMessageDialog(this, "Vui Long nhap dung ky tu");
						return;
					}
				}
			}
		}
		list.add(userAdd);
		if (showMes == true) {
			JOptionPane.showMessageDialog(this, "ADDED ACCOUNT!");
		}
	}

	public boolean authenticate(String username, String password) {
		for (User<String, String> user : list) {
			if (user.getUser().equals(username) && user.getPass().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public void printList() {
		System.out.println("user          password");
		for (User<String, String> user : list) {
			user.printList();
		}
	}

	public List<User<String, String>> getUserList() {
		return list;
	}

	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		boolean notFound = false;
		for (User<String, String> user : list) {
			if (user.getUser().equals(username) == false) {
				notFound = true;
			}
			if (user.getUser().equals(username)) {
				if (user.getUser().equals("root")) {
					JOptionPane.showMessageDialog(this, "YOU CAN'T DELETE ROOT ACCOUNT!");
					return;
				} else {
					list.remove(user);
					JOptionPane.showMessageDialog(this, "DELETED ACCOUNT!");
					return;
				}
			}
		}
		if (notFound == true) {
			JOptionPane.showMessageDialog(this, "NOT FOUND ACCOUNT!");
		}
	}

}
