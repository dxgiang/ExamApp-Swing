package main.java.com.example.auth;

public class User<K, V> {
	private K user;
	private V pass;
	private double score;
	private String status;
	
	public User() {
		super();
	}

	public User(K user, V pass, double score, String status) {
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

	public K getUser() {
		return user;
	}

	public void setUser(K user) {
		this.user = user;
	}

	public V getPass() {
		return pass;
	}

	public void setPass(V pass) {
		this.pass = pass;
	}

	public String printList() {
		return (user + "          " + pass + "\n");
	}
	
}
