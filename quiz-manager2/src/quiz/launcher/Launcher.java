package quiz.launcher;

import quiz.datamodel.MCQQuestion;
import quiz.datamodel.Question;
import quiz.services.MCQQuestionJDBCDAO;
import quiz.services.QuestionJDBCDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Launcher {

	/*
	■ admin questions
-insert(create)
-update
-delete
-show all(read)
-show (search by topics)

	■ Solve a quiz
-insert student info
 : solving a quiz > select Q randomly by topics > show score > export quiz?: Yes?no
-Search(topic, difficulty)
-show (search by topics)

	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		insertQuestion();
	}

	private static void insertQuestion(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("******Insert Question******");
		System.out.println("Do you want to insert a MCQQuestion? : y/n");
		char mcq = scanner.nextLine().charAt(0);

		if(mcq=='y'){
			Question question =  insertGeneralInfo(scanner);
			List<String> mcqInfo = insertMCQInfo(scanner);

			// insert MCQQuestion query
			MCQQuestionJDBCDAO mcqDao = new MCQQuestionJDBCDAO();
			int mcqId = mcqDao.create(mcqInfo);

			// insert Question query
			QuestionJDBCDAO dao = new QuestionJDBCDAO();
			dao.create(question,mcqId);
		}
		else{
			Question question = insertGeneralInfo(scanner);

			// insert Question query
			QuestionJDBCDAO dao = new QuestionJDBCDAO();
			dao.create(question, 0);
		}
	}

	private static Question insertGeneralInfo(Scanner scanner) {
		System.out.println("Question contents: (Enter 'end' at the last line When you want stop typing for question.)");
		String s = scanner.nextLine();
		String question="";
		while(!(s.equals("end")))
		{
			question = question.concat(s).concat("\n");
			s = scanner.nextLine();
		}
		//System.out.println(question);

		System.out.println("Topic : (Enter 'end' at the last line When you want stop typing for topics.)");
		List<String> topics = new ArrayList<String>();

		do{
			s = scanner.nextLine();
			topics.add("s");
		}while(!(s.equals("end")));

		System.out.println("Difficulty : ");
		int difficulty = scanner.nextInt();
		scanner.nextLine();

		Question q = new Question(question, topics, difficulty);
		return q;
	}
	private static List<String> insertMCQInfo(Scanner scanner){
		List<String> s = new ArrayList<String>();

		System.out.println("choice1 : ");
		s.add(scanner.nextLine());

		System.out.println("choice2 : ");
		s.add(scanner.nextLine());

		System.out.println("choice3 : ");
		s.add(scanner.nextLine());

		System.out.println("choice4 : ");
		s.add(scanner.nextLine());

		System.out.println("Answer : ");
		s.add(scanner.nextLine());

		return s;
	}
}
