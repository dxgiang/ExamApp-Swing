package user;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginSystem extends JFrame {
	private List<User<String, String>> list;

	public LoginSystem() {
		list = new ArrayList<>();
	}

	public void addUser(User<String, String> userAdd, boolean showMes) {
		for (User<String, String> user : list) {
			if (user.getUser().equals(userAdd.getUser())) {
				if(showMes == true) {
				JOptionPane.showMessageDialog(this, "TAI KHOAN DA TON TAI");
				return;}
			}
		}
		list.add(userAdd);
		if(showMes == true) {
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
}
