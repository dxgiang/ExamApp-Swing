package exam;

public class User<K, V> {
	private K user;
	private V pass;
	private double score;
	
	public User() {
		super();
	}

	public User(K user, V pass, double score) {
		super();
		this.user = user;
		this.pass = pass;
		this.score = score;
	}

	public double getDiem() {
		return score;
	}

	public void setDiem(double score) {
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
