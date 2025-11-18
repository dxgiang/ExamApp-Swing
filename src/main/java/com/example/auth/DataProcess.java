package main.java.com.example.auth;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DataProcess extends JFrame {

	private static final long serialVersionUID = 1L;
	private LocalDateTime dt;
	private static final String DATA_DIRECTORY = "data";
	private static final String USER_DATA_FILE = DATA_DIRECTORY + File.separator + "users.txt";
	private static final String LOCK_FILE = DATA_DIRECTORY + File.separator + "users.lock";

	public DataProcess() {
		File dataDir = new File(DATA_DIRECTORY);
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}

		File dataFile = new File(USER_DATA_FILE);
		if (!dataFile.exists()) {
			List<User> initialList = new ArrayList<>();
			initialList.add(new User(000, "root", "admin", 0.0, null));
			saveUsersToFile(initialList);
			System.out.println(upTime() + " Initial user data file created.");
		}
	}

	private <R> R performAtomicOperation(Function<List<User>, R> operation, boolean isReadOnly) {
		File lockFile = new File(LOCK_FILE);
		R result = null;

		try (RandomAccessFile raf = new RandomAccessFile(lockFile, "rw");
				FileChannel channel = raf.getChannel();
				FileLock lock = channel.lock()) {

			List<User> currentList = loadUsersFromFile();

			result = operation.apply(currentList);

			if (!isReadOnly) {
				saveUsersToFile(currentList);
			}

		} catch (Exception e) {
			System.err.println(
					upTime() + " Fatal error while performing file synchronization operation: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Fatal data sync error. Please restart the application.",
					"Data error", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

	public void addUser(User userAdd, boolean showMes) {
		performAtomicOperation(list -> {
			for (int i = 0; i < list.size(); i++) {
				User user = list.get(i);
				if (user.getUser().equals(userAdd.getUser())) {
					list.remove(i);
					break;
				}
			}
			list.add(userAdd);
			return null;
		}, false);

		if (showMes) {
			JOptionPane.showMessageDialog(this, "ADDED ACCOUNT!");
			System.out.println(upTime() + " Add User - User: " + userAdd.getUser() + " (Added user successfully)");
		}
	}

	public boolean authenticate(String username, String password) {
		return performAtomicOperation(list -> {
			for (User user : list) {
				if (user.getUser().equals(username) && user.getPass().equals(password)) {
					return true;
				}
			}
			return false;
		}, true);
	}

	public boolean wrongPass(String username, String password) {
		return performAtomicOperation(list -> {
			for (User user : list) {
				if (user.getUser().equals(username) && user.getPass().equals(password) == false) {
					return true;
				}
			}
			return false;
		}, true);
	}

	public void printList() {
		System.out.println("user \t password");
		List<User> userList = getUserList();
		for (User user : userList) {
			user.printList();
		}
	}

	public List<User> getUserList() {
		return performAtomicOperation(ArrayList::new, true);
	}

	public void deleteUser(String username) {
		int result = performAtomicOperation(list -> {
			for (int i = 0; i < list.size(); i++) {
				User user = list.get(i);
				if (user.getUser().equals(username)) {
					if (user.getUser().equals("root")) {
						return 2;
					} else {
						list.remove(i);
						return 1;
					}
				}
			}
			return 0;
		}, false);

		if (result == 1) {
			JOptionPane.showMessageDialog(this, "DELETED ACCOUNT!");
			System.out.println(upTime() + " Delete User - User: " + username + " (deleted successfully)");
		} else if (result == 2) {
			JOptionPane.showMessageDialog(this, "YOU CAN'T DELETE ROOT ACCOUNT!");
		} else {
			JOptionPane.showMessageDialog(this, "NOT FOUND ACCOUNT!");
		}
	}

	public boolean lockUser(String username) {
		return performAtomicOperation(list -> {
			for (User user : list) {
				if (user.getUser().equals(username)) {
					return true;
				}
			}
			return false;
		}, true);
	}

	public String upTime() {
		dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String dtfor = dt.format(format);
		return dtfor;
	}

	private void saveUsersToFile(List<User> list) {
		File dataDir = new File(DATA_DIRECTORY);
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
			list.sort(new Comparator<User>() {

				@Override
				public int compare(User arg0, User arg1) {
					return Integer.compare(arg0.getId(), arg1.getId());
				}

			});
			for (User user : list) {
				writer.write(user.getId() + "," + user.getUser() + "," + user.getPass() + "," + user.getScore() + ","
						+ (user.getStatus() != null ? user.getStatus() : "null"));
				writer.newLine();
			}
			System.out.println(upTime() + " User data saved to " + USER_DATA_FILE);
		} catch (IOException e) {
			System.err.println(
					upTime() + " Error saving user data to file: " + USER_DATA_FILE + " (" + e.getMessage() + ")");
		}
	}

	private List<User> loadUsersFromFile() {
		List<User> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 5) {
					int id = Integer.parseInt(parts[0]);
					String username = parts[1];
					String password = parts[2];
					double score = Double.parseDouble(parts[3]);
					String status = parts[4].equals("null") ? null : parts[4];
					list.add(new User(id, username, password, score, status));
				}
			}
			System.out.println(upTime() + " User data loaded from " + USER_DATA_FILE);
		} catch (IOException e) {
			System.err.println(upTime() + " No existing user data file found or error loading: " + USER_DATA_FILE + " ("
					+ e.getMessage() + ")");
			list.clear();
			list.add(new User(000, "root", "admin", 0.0, null));
		} catch (NumberFormatException e) {
			System.err.println(
					upTime() + " Error parsing user data from file: " + USER_DATA_FILE + " (" + e.getMessage() + ")");
			list.clear();
			list.add(new User(000, "root", "admin", 0.0, null));
		}
		return list;
	}

	public boolean isAccountLocked(String username) {
		return performAtomicOperation(list -> {
			for (User user : list) {
				if (user.getUser().equals(username)) {
					return "LOCKED".equals(user.getStatus()) || "CHEAT".equals(user.getStatus());
				}
			}
			return false;
		}, true);
	}

	public boolean isAccountCompleted(String username) {
		return performAtomicOperation(list -> {
			for (User user : list) {
				if (user.getUser().equals(username)) {
					return "PASS".equals(user.getStatus()) || "FAIL".equals(user.getStatus());
				}
			}
			return false;
		}, true);
	}

	private int countByStatus(String status) {
		return performAtomicOperation(list -> {
			int count = 0;
			for (User user : list) {
				if (status.equals(user.getStatus())) {
					count++;
				}
			}
			return count;
		}, true);
	}

	public int returnNewID() {
		return performAtomicOperation(list -> {
			list.sort(new Comparator<User>() {

				@Override
				public int compare(User arg0, User arg1) {
					return Integer.compare(arg0.getId(), arg1.getId());
				}
			});
			if (list.isEmpty()) {
				return 1;
			}
			for (int i = 0; i < list.size() - 1; i++) {
				int currentId = list.get(i).getId();
				int nextId = list.get(i + 1).getId();

				if (nextId - currentId > 1) {
					return currentId + 1;
				}
			}
			return list.get(list.size() - 1).getId() + 1;
		}, true);
	}

	public int countLocked() {
		return countByStatus("LOCKED");
	}

	public int countPassed() {
		return countByStatus("PASS");
	}

	public int countFailed() {
		return countByStatus("FAIL");
	}

	public int countCheated() {
		return countByStatus("CHEAT");
	}

	public String getDataFilePath() {
		return USER_DATA_FILE;
	}
}