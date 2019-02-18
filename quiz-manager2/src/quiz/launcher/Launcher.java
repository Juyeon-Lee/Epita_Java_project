package quiz.launcher;

import quiz.datamodel.MCQQuestion;
import quiz.datamodel.Question;
import quiz.datamodel.Student;
import quiz.services.MCQQuestionJDBCDAO;
import quiz.services.QuestionJDBCDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
 
/**
 * Code Explanation There are Two part 
 * ##admin questions//be able to operate
 * CRUD on Open Questions and MCQ Questions (questions and valid answers are
 * stored in a database or an XML file or a JSON file) 
 * -insert(create) -update -delete -show all(read) -show (search by topics)
 * 
 * ##Solve a quiz user guide(solve a quiz) 
 * Users are divided into admin and
 * student. admin is a quiz manager who can use quiz CRUD. Student can quiz by
 * all , topics, and difficulty levels. The first database contains 12 quizzes
 * and correct answers of three types(mcq, open , associative) Students can
 * choose one's type of quiz after all the quizzes are over, the student answers
 * whether they can extract the problem that they solve. Finally, the student
 * scores are printed and the quiz ends. // every quiz includes 3 question
 * types(mcq, open, associative) 
 * -insert student info //be able to assemble
 * automatically a quiz (a quiz is a set of questions) that gathers all the
 * questions covering a given list of topics. 
 * -solve a quiz(ALL) 
 * -solve a quiz(by topics)//be able to search questions based on topics 
 * -solve a quiz(by difficulty)//Write an algorithm (or use an existing one) that will allow to
 * get quiz based on a complexity rate. This overall complexity required by the
 * user can be calculated on the difficulty property. 
 * -export file?//export this
 * quiz under a plain text format 
 * -show score//run the evaluation and provide
 * the automatic mark in the end of this execution
 * 
 * @author Moeun & Juyeon
 * @version 1.0(2019-02-16)
 *
 */
public class Launcher {

	private static Scanner scanner;

	static int grade = 0;

	public static void main(String[] args) throws SQLException {
		scanner = new Scanner(System.in);
		QuestionJDBCDAO dao = new QuestionJDBCDAO();
		MCQQuestionJDBCDAO mcqDao = new MCQQuestionJDBCDAO();
		List<Question> fileQuestion = new ArrayList<Question>();
		Student student = new Student();
		String answer;

		System.out.println("Quiz Manager");
		System.out.println("-----------------------");
		System.out.println("Please enter your type");
		System.out.println("1. admin");
		System.out.println("2. student(solve a quiz)");
		answer = scanner.nextLine();

		if (answer.equals("1")) {// admin
			adminStartPage();

			switch (scanner.nextLine().charAt(0)) {
			case '1': // insert
				insertQuestion(scanner);
				break;
			case '2': // update
				updateQuestion(scanner, dao, mcqDao);
				break;
			case '3': // delete
				deleteQuestion(scanner, dao, mcqDao);
				break;
			case '4': // show all questions
				showAll(dao);
				break;
			case '5': // search by topics
				searchByTopic(scanner);
				break;
			default:
				System.out.println("You typed wrong number. Please try again.");
				// error process
				return;
			}

		} else if (answer.equals("2")) {// student
			studentLogin(scanner, student);// student info
			System.out.println("Please select a menu");
			System.out.println("1. solve a quiz(All)");
			System.out.println("2. solve a quiz(by topic)");
			System.out.println("3. solve a quiz(by difficulty)");
			answer = scanner.nextLine();
			String condition = " ";
			if (answer.equals("1")) { // solve a quiz(ALL)
				fileQuestion = solveQuiz(fileQuestion, condition);

			} else if (answer.equals("2")) { // solve a quiz(by topic)
				condition = null;
				System.out.println("select the topic");
				System.out.println("1. Java Language Basics");
				System.out.println("2. Java Operators");
				System.out.println("3. Java Object Oriented Programming");
				int key = scanner.nextInt();
				switch (key) {
				case 1:
					condition = " where topic = 'Java Language Basics'";
					break;
				case 2:
					condition = " where topic = 'Java Operators'";
					break;
				case 3:
					condition = " where topic = 'Java Object Oricented Programming'";
					break;
				default:
					break;
				}

				answer = scanner.nextLine();

				fileQuestion = solveQuiz(fileQuestion, condition);

			} else if (answer.equals("3")) { // solve a quiz(by difficulty)
				condition = null;
				System.out.println("select the difficulty(1 to 3)");
				int key = scanner.nextInt();
				switch (key) {
				case 1:
					condition = " where difficulty = 1";
					break;
				case 2:
					condition = " where difficulty = 2";
					break;
				case 3:
					condition = " where difficulty = 3";
					break;
				default:
					break;
				}

				fileQuestion = solveQuiz(fileQuestion, condition);

			} else {
				System.out.println("You did not enter a valid answer!");
			}

			extractQuizToFile(mcqDao, fileQuestion);

			System.out.println("[ Student Name : " + student.getName() + "\n" + "Student ID : " + student.getId() + "\n"
					+ "Student Score : " + grade);

		}
		scanner.close();
	}
	
	/**
	 * Print administrator's start Page
	 * @author LeeJuyeon
	 * 
	 */
	private static void adminStartPage() {
		System.out.println("-----------------------");
		System.out.println("**Admin questions**");
		System.out.println("What do you want to do?");
		System.out.println("1. Insert(create)");
		System.out.println("2. Update");
		System.out.println("3. Delete");
		System.out.println("4. Show all questions(read)");
		System.out.println("5. Show (search by topics)");
	}

	/**
	 * You can insert a open question or MCQquestion in administrator's mode :
	 * Once you go to {@link #insertGeneralInfo(Scanner)}, 
	 * if you want to create a MCQquestion, you'll go to {@link #insertMCQInfo(Scanner)}.
	 * Then, linked DAO's create method will execute a create query.
	 * If you want to create questions more, you can do it.
	 * @see quiz.services.QuestionJDBCDAO#create(Question, int)
	 * @see quiz.services.MCQQuestionJDBCDAO#create(List)
	 * 
	 * @param scanner - It will scan some information
	 * @author LeeJuyeon
	 */
	private static void insertQuestion(Scanner scanner) {
		System.out.println("******Insert Question******");

		while (true) {
			System.out.println("Do you want to insert a MCQQuestion? : y/n");
			char mcq = scanner.nextLine().charAt(0);

			if (mcq == 'y') {
				Question question = insertGeneralInfo(scanner);
				List<String> mcqInfo = insertMCQInfo(scanner);

				// insert MCQQuestion query
				MCQQuestionJDBCDAO mcqDao = new MCQQuestionJDBCDAO();
				int mcqId = mcqDao.create(mcqInfo);

				// insert Question query
				QuestionJDBCDAO dao = new QuestionJDBCDAO();
				dao.create(question, mcqId);
			} else {
				Question question = insertGeneralInfo(scanner);

				// insert Question query
				QuestionJDBCDAO dao = new QuestionJDBCDAO();
				dao.create(question, 0);
			}
			System.out.println("A question is created.");
			System.out.println("Do you want to Insert again? : y/n");
			char again = scanner.nextLine().charAt(0);
			if (again == 'n')
				break;
		}
	}
	
	/**
	 * You can update a open question or MCQquestion in administrator's mode
	 * If you don't know well about questions, you can see the list of questions 
	 * You must follow the rule of typing.
	 * If you choice 1, you will go to quesion's update and the other choice will send you to mcqquestion's update
	 * If you want to update questions more, you can do it.
	 * @see quiz.services.QuestionJDBCDAO#printAll()
	 * @see quiz.services.QuestionJDBCDAO#update(ArrayList)
	 * @see quiz.services.MCQQuestionJDBCDAO#update(ArrayList)
	 * 
	 * @param scanner - It will scan some information
	 * @param dao - QuestionJDBCDAO
	 * @param mcq - MCQQuestionJDBCDAO
	 * 
	 * @author LeeJuyeon
	 */
	private static void updateQuestion(Scanner scanner, QuestionJDBCDAO dao, MCQQuestionJDBCDAO mcq) {
		System.out.println("******Update Question******");
		while (true) {
			System.out.println("Do you need to read all questions? : y/n");
			if (scanner.nextLine().charAt(0) == 'y') {
				dao.printAll();
			}

			System.out.println("-----------------------");
			System.out.println("Now you can update");
			System.out.println("*Tip : You should follow this form and enter things. >");
			System.out.println(
					"[Case 1] UPDATE QUESTION SET QUESTION=__(1)__, MCQ=__(2)__, TOPIC=__(3)__, DIFFICULTY=__(4)__ WHERE ID=__(5)__");
			System.out.println(
					"[Case 2] UPDATE MCQQUESTION SET CHOICE1=__(1)__, CHOICE2=__(2)__, CHOICE3=__(3)__, CHOICE4=__(4)__, ANSWER =__(5)__ WHERE MCQID=__(6)__");

			System.out.print("Which table do you want to update? 1 -> QUESTION / 2 -> MCQQUESTION ");
			ArrayList<String> list = new ArrayList<String>();
			char choiceTableForUpdate = scanner.nextLine().charAt(0);
			if (choiceTableForUpdate == '1') {
				for (int i = 0; i < 5; i++) {
					System.out.print(i + 1 + " : ");
					list.add(scanner.nextLine());
				}
				dao.update(list);
			} else if (choiceTableForUpdate == '2') {
				for (int i = 0; i < 6; i++) {
					System.out.print(i + 1 + " : ");
					list.add(scanner.nextLine());
				}
				mcq.update(list);
			} else {
				System.out.println("You typed wrong number.");
				continue;
			}

			System.out.print("Do you want to Update again? : y/n ");
			char again = scanner.nextLine().charAt(0);
			if (again == 'n')
				break;
		}

	}

	/**
	 * You can delete a open question or MCQquestion in administrator's mode
	 * If you don't know well about questions, you can see the list of questions 
	 * You must follow the rule of typing.
	 * If you choice 1, you will go to quesion's delete and the other choice will send you to mcqquestion's delete
	 * If you want to delete questions more, you can do it.
	 * @see quiz.services.QuestionJDBCDAO#printAll()
	 * @see quiz.services.QuestionJDBCDAO#delete(int)
	 * @see quiz.services.MCQQuestionJDBCDAO#delete(int)
	 * 
	 * @param scanner - It will scan some information
	 * @param dao - QuestionJDBCDAO
	 * @param mcqDao - MCQQuestionJDBCDAO
	 * 
	 * @author LeeJuyeon
	 */
	private static void deleteQuestion(Scanner scanner, QuestionJDBCDAO dao, MCQQuestionJDBCDAO mcqDao) {
		System.out.println("******Delete Question******");

		while (true) {
			System.out.println("Do you need to read all questions? : y/n");
			if (scanner.nextLine().charAt(0) == 'y') {
				dao.printAll();
			}

			System.out.println("-----------------------");
			System.out.println("Now you can delete");
			System.out.println("*Tip : You should follow this form and enter things. >");
			System.out.println("[Case 1] DELETE FROM QUESTION WHERE ID=__(1)__");
			System.out.println("[Case 2] DELETE FROM MCQQUESTION WHERE MCQID=__(1)__");

			System.out.print("Which table do you want to delete? 1 -> QUESTION / 2 -> MCQQUESTION ");
			String data = new String();
			char choiceTableForDelete = scanner.nextLine().charAt(0);
			if (choiceTableForDelete == '1') {
				System.out.println("*Tip : If it's a MCQquestion, the linked MCQ info also will be deleted.");
				System.out.print("ID : ");
				data = scanner.nextLine();
				dao.delete(Integer.parseInt(data));
			} else if (choiceTableForDelete == '2') {
				System.out.println("*Tip : You can delete only unlinked data which any question don't have it.");
				System.out.print("MCQID : ");
				int d = Integer.parseInt(scanner.nextLine());
				int linked = dao.ifLinkedornotWithMCQ(d);
				if (linked != 0) {
					System.out.println("A ID " + linked + " question is linked with this data. You can't delete it.");
				} else {
					mcqDao.delete(d);
				}
			} else {
				System.out.println("You typed wrong number.");
				continue;
			}

			System.out.print("Do you want to delete again? : y/n ");
			char again = scanner.nextLine().charAt(0);
			if (again == 'n')
				break;
		}

	}

	/**
	 * You can see the list of questions in administrator's mode
	 * 
	 * @see quiz.services.QuestionJDBCDAO#printAll()
	 * @param dao
	 * @author LeeJuyeon
	 */
	private static void showAll(QuestionJDBCDAO dao) {
		dao.printAll();
	}

	/**
	 * This will scan a topic from user, and send it to searchByTopic of dao.
	 * Finally, it will receive a resultList from searchByTopic.
	 * In for Loop, it will print all information of suited question which's open question or MCQ question.
	 * For doing the process, it use toStringofTopics to make list of topics to String.
	 * @see quiz.services.QuestionJDBCDAO#searchByTopic(String)
	 * @see quiz.services.QuestionJDBCDAO#toStringofTopics(List)
	 * 
	 * @param scanner - It will scan some information
	 * @author LeeJuyeon
	 */
	private static void searchByTopic(Scanner scanner) {
		QuestionJDBCDAO dao = new QuestionJDBCDAO();
		MCQQuestionJDBCDAO mcq = new MCQQuestionJDBCDAO();
		System.out.println("******Search Questions by Topic******");
		System.out.print("Enter one topic. : ");
		String topic = scanner.nextLine();
		List<Question> resultList;
		resultList = dao.searchByTopic(topic);
		for (int i = 0; i < resultList.size(); i++) {
			Question q = resultList.get(i);
			System.out.print("ID : " + q.getId() + " / Question : " + q.getQuestion() + " / Topic : "
					+ dao.toStringofTopics(q.getTopics()) + " / Difficulty : " + q.getDifficulty());
			if (q.getMcq() == 0) {
				System.out.println(" / This question is not a MCQ question.");
			} else {
				System.out.println(" / linked MCQ id : " + q.getMcq());
				mcq.print(q.getMcq());
			}
		}
	}
	
	/**
	 * This method will get inputs(question, topics, difficulty) from the user,
	 *  and return question to {@link #insertQuestion(Scanner)} by Question{@link quiz.datamodel.Question} form.
	 * 
	 * @param scanner - It will scan some information
	 * @return Question - to return Question to insertQuestion
	 * @author LeeJuyeon
	 */
	private static Question insertGeneralInfo(Scanner scanner) {
		System.out.println("Question contents: (Enter 'end' at the last line When you want stop typing for question.)");
		String s = scanner.nextLine();
		String question = "";
		while (!(s.equals("end"))) {
			question = question.concat(s).concat("\n");
			s = scanner.nextLine();
		}

		System.out.println("Topic : (Enter 'end' at the last line When you want stop typing for topics.)");
		List<String> topics = new ArrayList<String>();

		s = scanner.nextLine();
		while (true) {
			topics.add(s);
			s = scanner.nextLine();
			if (s.equals("end"))
				break;
		}

		System.out.println("Difficulty : ");
		int difficulty = scanner.nextInt();
		scanner.nextLine();

		Question q = new Question(question, topics, difficulty);
		return q;
	}

	/**
	 * This method will get inputs(choices and answer) from the user,
	 *  and return the list to {@link #insertQuestion(Scanner)} by List<String> form.
	 * 
	 * @param scanner
	 * @return List<String> - choice1~4, answer
	 * @author LeeJuyeon
	 */
	private static List<String> insertMCQInfo(Scanner scanner) {
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

	private static void studentLogin(Scanner scanner, Student student) {
		// student login method > it stores student information in the student class
		System.out.println("Student Login");
		System.out.println("-----------------------");
		System.out.println("Please enter your name");
		String name = scanner.nextLine();
		student.setName(name);
		System.out.println("Please enter your id");
		String id = scanner.nextLine();
		student.setId(id);
		
	}
	
	private static void extractQuizToFile(MCQQuestionJDBCDAO mcqDao, List<Question> fileQuestion) {
		// This is a method that saves the quizzes to a file.
		String answer;
		FileWriter writer = null;

		System.out.println("Do you want to extract the quiz to a file?? (Y/N)");
		answer = scanner.nextLine();
		if (answer.equals("Y")) {
			File file = new File("test1.txt");
			for (int k = 0; k < fileQuestion.size() - 1; k++) {
				try {
					writer = new FileWriter(file, true);
					writer.write("# Q. " + fileQuestion.get(k).getQuestion());
					writer.write(System.getProperty("line.separator"));
					int mcqid = fileQuestion.get(k).getMcq();
					if (mcqid != 0) {
						writer.write("Choice1 : " + mcqDao.showWhereMcqid(mcqid).getCHOICE1());
						writer.write(System.getProperty("line.separator"));
						writer.write("Choice2 : " + mcqDao.showWhereMcqid(mcqid).getCHOICE2());
						writer.write(System.getProperty("line.separator"));
						writer.write("Choice3 : " + mcqDao.showWhereMcqid(mcqid).getCHOICE3());
						writer.write(System.getProperty("line.separator"));
						writer.write("Choice4 : " + mcqDao.showWhereMcqid(mcqid).getCHOICE4());
						writer.write(System.getProperty("line.separator"));
						writer.write("Answer : " + mcqDao.showWhereMcqid(mcqid).getAnswer());
						writer.write(System.getProperty("line.separator"));
						writer.write(System.getProperty("line.separator"));
					}
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("A file is extracted in this project folder.");
		} else {
			System.out.println("You wrote something not 'Y'");
		}
	}

	private static List<Question> solveQuiz(List<Question> fileQuestion, String condition) {
		// Show problem and example according to condition.
		QuestionJDBCDAO dao = new QuestionJDBCDAO();
		MCQQuestionJDBCDAO mcqDao = new MCQQuestionJDBCDAO();
		System.out.println("Quiz Start");
		System.out.println("-----------------------");
		List<Question> q = dao.showAll(condition);
		String answer;
		for (int j = 0; j < q.size(); j++) {
			System.out.println("Question " + (j + 1));
			System.out.println(q.get(j).getQuestion());
			int a = q.get(j).getMcq();

			fileQuestion.add(q.get(j));
			if (a != 0) {

				MCQQuestion m = mcqDao.showWhereMcqid(a);
				System.out.print("CHOICE 1 : ");
				System.out.println(m.getCHOICE1());
				System.out.print("CHOICE 2 : ");
				System.out.println(m.getCHOICE2());
				System.out.print("CHOICE 3 : ");
				System.out.println(m.getCHOICE3());
				System.out.print("CHOICE 4 : ");
				System.out.println(m.getCHOICE4());

				System.out.println("-----------------------");
				System.out.println("Please enter your answer(You should enter not number, but the whole answer.)");

				
				answer = scanner.nextLine();
				if( answer.length() ==0) // enter key condition
					answer = scanner.nextLine();
				System.out.println("answer: "+answer);
				if (m.getAnswer().equals(answer))
					grade++;
			} else {
				System.out.println("-----------------------");
				System.out.println("Please enter your answer");
				answer = scanner.nextLine();
				grade++;
			}
		}
		return fileQuestion;
	}

	

}
