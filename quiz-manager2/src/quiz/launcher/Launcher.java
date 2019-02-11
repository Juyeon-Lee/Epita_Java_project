package quiz.launcher;

import quiz.datamodel.Answer;
import quiz.datamodel.MCQQuestion;
import quiz.datamodel.Question;
import quiz.datamodel.Student;
import quiz.services.MCQQuestionJDBCDAO;
import quiz.services.QuestionJDBCDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
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
public class Launcher {

    private static Scanner scanner;


    public static void main(String[] args) throws SQLException {
        scanner = new Scanner(System.in);
        int grade;
        String answer;

        boolean continueAddition = true;

        grade = startPage(scanner);
        System.out.println(grade);

    }

    private static int startPage(Scanner scanner) {
        String answer;
        boolean continueAddition = true;
        int grade=0;
        QuestionJDBCDAO dao = new QuestionJDBCDAO();
        MCQQuestionJDBCDAO mcq = new MCQQuestionJDBCDAO();
        int i=1;
        dao.showAllID();
        System.out.println("Quiz Manager");
        System.out.println("-----------------------");
        while (continueAddition) {
            System.out.println("Please enter your type");
            System.out.println("1. admin");
            System.out.println("2. student(solve a quiz)");
            answer = scanner.nextLine();

            if (answer.equals("1")) {

            } else if (answer.equals("2")) {
                System.out.println("Quiz Start");
                System.out.println("-----------------------");

                while(i<4) {
                    dao.searchWhereID(new Question(i));
                    System.out.println(dao.searchWhereID(new Question(i)));
                    System.out.println("-----------------------");
                    System.out.println(mcq.showChoice(i));
                    System.out.println("Please enter your answer");
                    answer = scanner.nextLine();
                    if(mcq.correctAnswer(i, answer))
                    {
                        grade++;
                    }
                    i++;
                }
            } else {
                System.out.println("You did not enter a valid answer!");
            }
            System.out.println("do you want to quit solving a quiz? (Y/N)");
            answer = scanner.nextLine();
            continueAddition = "N".equals(answer); // answer.equals("Y");
        }

        insertQuestion(scanner);
        scanner.close();
        return grade;
    }

	private static void insertQuestion(Scanner scanner){
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
