package exam;

public class Question {
	private String question;
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private int indexRightAns;

	public Question(String question, String ans1, String ans2, String ans3, String ans4, int indexRightAns) {
		this.question = question;
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.ans4 = ans4;
		this.indexRightAns = indexRightAns;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAns1() {
		return ans1;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public String getAns2() {
		return ans2;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getAns3() {
		return ans3;
	}

	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	public String getAns4() {
		return ans4;
	}

	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}

	public int getIndexRightAns() {
		return indexRightAns;
	}

	public void setIndexRightAns(int indexRightAns) {
		this.indexRightAns = indexRightAns;
	}
}
