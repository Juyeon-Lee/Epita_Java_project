package quiz.datamodel;

import java.util.List;
import java.util.Random;

public class Question {

	private int id;
	private String question;
	private int mcq;
	private List<String> topics;
	private Integer difficulty;
	Random random = new Random();

	@Override
	public String toString() {

		/*return "Question [id=" + id + ", question=" + question + ", topics=" + topics + ", difficulty=" + difficulty
				+ "]";*/
		return "Question :"+question + "]";
	}

	public int getMcq() {
		return mcq;
	}

	public void setMcq(int mcq) {
		this.mcq = mcq;
	}

	public Question(int i) {
		this.id = i;
	}

	public Question(String question, List<String> topics, Integer difficulty) {
		this.question = question;
		this.topics = topics;
		this.difficulty = difficulty;
	}

	public Question(int id, String question, int mcq, List<String> topics, Integer difficulty) {
		this.id = id;
		this.question = question;
		this.mcq = mcq;
		this.topics = topics;
		this.difficulty = difficulty;
	}

	//209-02-09 m update > new
	public Question(int id, int difficulty, String question) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.difficulty = difficulty;
		this.question = question;

	}

	public Question(String question) {
		// TODO Auto-generated constructor stub
		this.question = question;
	}




	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getTopics() {
		return topics;
	}
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
