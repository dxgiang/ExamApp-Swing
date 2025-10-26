package main.java.com.example.auth;

public class User {
	private String user;
	private String pass;
	private double score;
	private String status;

	public User() {
		super();
	}

	public User(String user, String pass, double score, String status) {
		super();
		this.user = user;
		this.pass = pass;
		this.score = score;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String printList() {
		return (user + "          " + pass + "\n");
	}

}
