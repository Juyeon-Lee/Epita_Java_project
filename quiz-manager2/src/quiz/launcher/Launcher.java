package quiz.launcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import quiz.datamodel.Answer;
import quiz.datamodel.MCQQuestion;
import quiz.datamodel.Question;
import quiz.datamodel.Student;
import quiz.services.MCQQuestionJDBCDAO;
import quiz.services.QuestionJDBCDAO;

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
        return grade;
    }

    private static Student studentAccount(Scanner scanner) {
        System.out.println("Please enter a name");
        String name = scanner.nextLine();
        System.out.println("Please enter a id");
        String id = scanner.nextLine();

        Student student = new Student(name, id);

        System.out.println("Sign in");
        return student;
    }

}
