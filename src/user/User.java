package user;

public class User<K, V> {
	private K user;
	private V pass;
	
	public User() {
		super();
	}

	public User(K user, V pass) {
		super();
		this.user = user;
		this.pass = pass;
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
	
}
