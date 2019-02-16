package quiz.launcher;

import quiz.datamodel.Answer;
import quiz.datamodel.MCQQuestion;
import quiz.datamodel.Question;
import quiz.datamodel.Student;
import quiz.services.MCQQuestionJDBCDAO;
import quiz.services.QuestionJDBCDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/**@author moeun & juyeon**/

/*//
 *Code Explanation
 *There are Two part
	�� admin questions//be able to operate CRUD on Open Questions and MCQ Questions (questions and valid answers are stored in a database or an XML file or a JSON file)
-insert(create)
-update
-delete
-show all(read)
-show (search by topics)

	�� Solve a quiz
user guide(solve a quiz)
Users are divided into admin and student.
admin is a quiz manager who can use quiz CRUD.
Student can quiz by all , topics, and difficulty levels.
The first database contains 12 quizzes and correct answers of three types(mcq, open , associative)
Students can choose one's type of quiz
after all the quizzes are over, the student answers whether they can extract the problem that they solve.
Finally, the student scoes are printed and the quiz ends.
// every quiz includes 3 question types(mcq, open, associative)	
-insert student info
	//be able to assemble automatically a quiz (a quiz is a set of questions) that gathers all the questions covering a given list of topics.
	-solve a quiz(ALL)
	-solve a quiz(by topics)//be able to search questions based on topics
	-solve a quiz(by difficulty)//Write an algorithm (or use an existing one) that will allow to get quiz based on a complexity rate. This overall complexity required by the user can be calculated on the difficulty property.
		-export file?//export this quiz under a plain text format
		-show score//run the evaluation and provide the automatic mark in the end of this execution

*/
public class Launcher {

    private static Scanner scanner;

    static int grade = 0;

    public static void main(String[] args) throws SQLException {
        scanner = new Scanner(System.in);
        String answer;
        FileWriter writer = null;
        QuestionJDBCDAO dao = new QuestionJDBCDAO();
        MCQQuestionJDBCDAO mcqDao = new MCQQuestionJDBCDAO();
        List<Question> fileQuestion = new ArrayList<Question>();
        Student student = new Student();
        int r = (int) Math.random();

        System.out.println("Quiz Manager");
        System.out.println("-----------------------");
        System.out.println("Please enter your type");
        System.out.println("1. admin");
        System.out.println("2. student(solve a quiz)");
        answer = scanner.nextLine();

        if (answer.equals("1")) {// admin
            adminStartPage();

            switch(scanner.nextLine().charAt(0)){
                case '1': //insert
                    insertQuestion(scanner);
                    break;
                case '2': //update
                    updateQuestion(scanner, dao, mcqDao);
                    break;
                case '3': //delete
                    deleteQuestion(scanner);
                    break;
                case '4': //show all questions
                    showAll(dao);
                    break;
                case '5': //search by topics
                    searchByTopics(scanner);
                    break;
                default :
                    System.out.println("You typed wrong number. Please try again.");
                    //error process
                    return;
            }

        } else if (answer.equals("2")) {// student
            studentLogin(scanner, student);// student info
            System.out.println("Please select a menu");
            System.out.println("1. solve a quiz(All)");
            System.out.println("2. solve a quiz(by topic)");
            System.out.println("3. solve a quiz(by difficulty)");
            answer = scanner.nextLine();
            if (answer.equals("1")) {// solve a quiz(ALL)
                System.out.println("Quiz Start");
                System.out.println("-----------------------");

                List<Question> q = dao.showAll(" ");
                for (int j = 0; j < q.size(); j++) {
                    System.out.println("Question " + (j + 1));
                    System.out.println(q.get(j).getQuestion());
                    int a = q.get(j).getMcq();

                    fileQuestion.add(q.get(j));
                    if (a != 0) {

                        List<MCQQuestion> m = mcqDao.showWhereMcqid(a);
                        System.out.print("CHOICE 1 : ");
                        System.out.println(m.get(0).getCHOICE1());
                        System.out.print("CHOICE 2 : ");
                        System.out.println(m.get(0).getCHOICE2());
                        System.out.print("CHOICE 3 : ");
                        System.out.println(m.get(0).getCHOICE3());
                        System.out.print("CHOICE 4 : ");
                        System.out.println(m.get(0).getCHOICE4());

                        System.out.println("-----------------------");
                        System.out.println("Please enter your answer");

                        answer = scanner.nextLine();
                        if (m.get(0).getAnswer().equals(answer))
                            grade++;
                    } else {
                        System.out.println("-----------------------");
                        System.out.println("Please enter your answer");
                        answer = scanner.nextLine();
                        grade++;

                    }
                }
            } else if (answer.equals("2")) {
                String condition = null;
                System.out.println("select the topic");
                System.out.println("1. Java Language Basics");
                System.out.println("2. Java Operators");
                System.out.println("3. Java Object Oriented Programming");
                int key = scanner.nextInt();
                switch (key) {
                    case 1:
                        condition = "where topic = 'Java Language Basics'";
                        break;
                    case 2:
                        condition = "where topic = 'Java Operators'";
                        break;
                    case 3:
                        condition = "where topic = 'Java Object Oricented Programming'";
                        break;
                    default:
                        break;
                }

                System.out.println("Quiz Start");
                System.out.println("-----------------------");
                List<Question> q = dao.showAll(condition);

                for (int j = 0; j < q.size(); j++) {
                    System.out.println("Question " + (j + 1));
                    System.out.println(q.get(j).getQuestion());
                    int a = q.get(j).getMcq();

                    fileQuestion.add(q.get(j));
                    if (a != 0) {

                        List<MCQQuestion> m = mcqDao.showWhereMcqid(a);
                        System.out.print("CHOICE 1 : ");
                        System.out.println(m.get(0).getCHOICE1());
                        System.out.print("CHOICE 2 : ");
                        System.out.println(m.get(0).getCHOICE2());
                        System.out.print("CHOICE 3 : ");
                        System.out.println(m.get(0).getCHOICE3());
                        System.out.print("CHOICE 4 : ");
                        System.out.println(m.get(0).getCHOICE4());

                        System.out.println("-----------------------");
                        System.out.println("Please enter your answer");
                        if (j == 0)
                            answer = scanner.nextLine();
                        answer = scanner.nextLine();
                        if (m.get(0).getAnswer().equals(answer))
                            grade++;
                    } else {
                        System.out.println("-----------------------");
                        System.out.println("Please enter your answer");
                        answer = scanner.nextLine();
                        grade++;
                    }
                }
            }else if(answer.equals("3")) {
                String condition = null;
                System.out.println("select the difficulty(1 to 3)");
                int key = scanner.nextInt();
                switch (key) {
                    case 1:
                        condition = "where difficulty = 1";
                        break;
                    case 2:
                        condition = "where difficulty = 2";
                        break;
                    case 3:
                        condition = "where difficulty = 3";
                        break;
                    default:
                        break;
                }

                System.out.println("Quiz Start");
                System.out.println("-----------------------");
                List<Question> q = dao.showAll(condition);

                for (int j = 0; j < q.size(); j++) {
                    System.out.println("Question " + (j + 1));
                    System.out.println(q.get(j).getQuestion());
                    int a = q.get(j).getMcq();

                    fileQuestion.add(q.get(j));
                    if (a != 0) {

                        List<MCQQuestion> m = mcqDao.showWhereMcqid(a);
                        System.out.print("CHOICE 1 : ");
                        System.out.println(m.get(0).getCHOICE1());
                        System.out.print("CHOICE 2 : ");
                        System.out.println(m.get(0).getCHOICE2());
                        System.out.print("CHOICE 3 : ");
                        System.out.println(m.get(0).getCHOICE3());
                        System.out.print("CHOICE 4 : ");
                        System.out.println(m.get(0).getCHOICE4());

                        System.out.println("-----------------------");
                        System.out.println("Please enter your answer");
                        if (j == 0)
                            answer = scanner.nextLine();
                        answer = scanner.nextLine();
                        if (m.get(0).getAnswer().equals(answer))
                            grade++;
                    } else {
                        System.out.println("-----------------------");
                        System.out.println("Please enter your answer");
                        answer = scanner.nextLine();
                        grade++;
                    }
                }

            }else {
                System.out.println("You did not enter a valid answer!");
            }

            System.out.println("Do you want to extract the quiz to a file?? (Y/N)");
            answer = scanner.nextLine();
            if (answer.equals("Y")) {
                File file = new File("test1.txt");
                for (int k = 0; k < fileQuestion.size() - 1; k++) {
                    try {
                        writer = new FileWriter(file, true);
                        writer.write(fileQuestion.get(k).getQuestion());
                        writer.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("[ Student Name : " + student.getName() + "\n" + "Student ID : " + student.getId()
                    + "\n" + "Student Score : " + grade);

        }
        scanner.close();
    }

    private static void studentLogin(Scanner scanner, Student student) {
        System.out.println("Student Login");
        System.out.println("-----------------------");
        System.out.println("Please enter your name");
        String name = scanner.nextLine();
        student.setName(name);
        System.out.println("Please enter your id");
        String id = scanner.nextLine();
        student.setName(id);
    }

    private static void searchByTopics(Scanner scanner) {

    }

    //2019-02-15 Juyeon wrote
    private static void showAll(QuestionJDBCDAO dao) {
        dao.printAll();
    }

    //2019-02-13 Juyeon update
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

    //2019-02-15 Juyeon update
    private static void updateQuestion(Scanner scanner, QuestionJDBCDAO dao, MCQQuestionJDBCDAO mcq) {
        System.out.println("******Update Question******");
        while(true) {
            System.out.println("Do you need to read all questions? : y/n");
            if (scanner.nextLine().charAt(0) == 'y') {
                dao.printAll();
            }

            System.out.println("-----------------------");
            System.out.println("Now you can update");
            System.out.println("You should follow this form and enter things. >");
            System.out.println("[Case 1] UPDATE QUESTION SET QUESTION=__(1)__, MCQ=__(2)__, TOPIC=__(3)__, DIFFICULTY=__(4)__ WHERE ID=__(5)__");
            System.out.println("[Case 2] UPDATE MCQQUESTION SET CHOICE1=__(1)__, CHOICE2=__(2)__, CHOICE3=__(3)__, CHOICE4=__(4)__, ANSWER =__(5)__ WHERE MCQID=__(6)__");

            System.out.print("Which table do you want to update? 1 -> QUESTION / 2 -> MCQQUESTION");
            ArrayList<String> list = new ArrayList<String>();
            if(scanner.nextLine().charAt(0) == 1){
                for (int i = 0; i < 5; i++) {
                    System.out.print(i+1 + " : ");
                    list.add(scanner.nextLine());
                }
                dao.update(list);
            }else if(scanner.nextLine().charAt(0) == 2){
                for (int i = 0; i < 6; i++) {
                    System.out.print(i+1 + " : ");
                    list.add(scanner.nextLine());
                }
                mcq.update(list);
            }else {
                System.out.println("You typed wrong number.");
                continue;
            }

            System.out.print("Do you want to Update again? : y/n");
            char again = scanner.nextLine().charAt(0);
            if(again == 'n')
                break;
        }

    }

    private static void deleteQuestion(Scanner scanner) {
        System.out.println("******Delete Question******");
        //show all questions include Ids of questions
        System.out.println("Do you want to insert a MCQQuestion? : y/n");


    }

    private static void insertQuestion(Scanner scanner){
        System.out.println("******Insert Question******");

        while(true){
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

            System.out.print("Do you want to Insert again? : y/n");
            char again = scanner.nextLine().charAt(0);
            if(again == 'n')
                break;
        }
    }

    private static Question insertGeneralInfo(Scanner scanner) {
        System.out.println("Question contents: (Enter 'end' at the last line When you want stop typing for question.)");
        String s = scanner.nextLine();
        String question = "";
        while (!(s.equals("end"))) {
            question = question.concat(s).concat("\n");
            s = scanner.nextLine();
        }
        // System.out.println(question);

        System.out.println("Topic : (Enter 'end' at the last line When you want stop typing for topics.)");
        List<String> topics = new ArrayList<String>();

        do {
            s = scanner.nextLine();
            topics.add("s");
        } while (!(s.equals("end")));

        System.out.println("Difficulty : ");
        int difficulty = scanner.nextInt();
        scanner.nextLine();

        Question q = new Question(question, topics, difficulty);
        return q;
    }

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
}
