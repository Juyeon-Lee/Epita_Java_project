package quiz.datamodel;

import java.util.List;

public class Question {

	private int id;
	
	private String question; 
	private List<String> topics;	 
	private Integer difficulty;
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", question=" + question + ", topics=" + topics + ", difficulty=" + difficulty
				+ "]";
	}

	public Question() {
	}
	
	public Question(String question, List<String> topics, Integer difficulty) {
		this.question = question;
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
