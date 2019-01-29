package quiz.datamodel;

import java.util.List;

public class MCQQuestion extends Question{
	
	public MCQQuestion(String question, List<String> topics, Integer difficulty) {
		super(question, topics, difficulty);
	}
}
