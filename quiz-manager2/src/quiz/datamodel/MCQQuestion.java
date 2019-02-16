package quiz.datamodel;

public class MCQQuestion{

	/*public MCQQuestion(String question, List<String> topics, Integer difficulty) {
		super(question, topics, difficulty);
	}*/
	int MCQID;
	String CHOICE1,CHOICE2,CHOICE3,CHOICE4,Answer;

	public int getMCQID() {
		return MCQID;
	}

	public void setMCQID(int mCQID) {
		MCQID = mCQID;
	}

	public String getCHOICE1() {
		return CHOICE1;
	}

	public void setCHOICE1(String cHOICE1) {
		CHOICE1 = cHOICE1;
	}

	public String getCHOICE2() {
		return CHOICE2;
	}

	public void setCHOICE2(String cHOICE2) {
		CHOICE2 = cHOICE2;
	}

	public String getCHOICE3() {
		return CHOICE3;
	}

	public void setCHOICE3(String cHOICE3) {
		CHOICE3 = cHOICE3;
	}

	public String getCHOICE4() {
		return CHOICE4;
	}

	public void setCHOICE4(String cHOICE4) {
		CHOICE4 = cHOICE4;
	}

	public String getAnswer() {
		return Answer;
	}

	public void setAnswer(String answer) {
		Answer = answer;
	}

	public MCQQuestion(int mCQID, String cHOICE1, String cHOICE2, String cHOICE3, String cHOICE4,
					   String answer) {

		MCQID = mCQID;
		CHOICE1 = cHOICE1;
		CHOICE2 = cHOICE2;
		CHOICE3 = cHOICE3;
		CHOICE4 = cHOICE4;
		Answer = answer;
	}

	public MCQQuestion() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {

		/*return "Question [id=" + id + ", question=" + question + ", topics=" + topics + ", difficulty=" + difficulty
				+ "]";*/
		return "choice1 :" + CHOICE1+'\n'+"choice2 :" + CHOICE2+'\n'+"choice3 :" + CHOICE3+'\n'+"choice4 :" + CHOICE4+'\n';
	}


}
