package quiz.launcher;

        import java.util.Scanner;

        import quiz.datamodel.Question;

public class Launcher {

    public static void main(String[] args) {
        // TODO Auto-generated method st
        Scanner scanner = new Scanner(System.in);
        String answer;
        boolean continueAddition = true;

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
            } else {
                System.out.println("You did not enter a valid answer!");
            }
            System.out.println("do you want to continue solving a quiz? (Y/N)");
            answer = scanner.nextLine();
            continueAddition = "Y".equals(answer); //answer.equals("Y");
        }
    }


}
