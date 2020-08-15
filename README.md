# Epita_Java_project

Author : Juyeon Lee , Moeun Son
Date : 19.02.2019

## MAIN FEATURES

The scope that we covered by this application is to :
* Database ‘H2’ for store and manage data
* Be able to operate Create/Update/Delete/Read of all Questions which are stored in a database
*	Use console application to play the program
*	Be able to search questions based on topics
*	Be able to assemble automatically a quiz (a quiz is a set of questions) that gathers all the questions covering a given list of topics.
*	Export this quiz under a plain text format if user want to export
*	Run the evaluation and provide the automatic mark in the end of this execution 

## COMPILATION & INSTALLATION NOTICE

Before start using our application, the quiz manager works on the eclipse(console) and the database uses H2.

1. Go to the downloaded h2 folder and follow the instructions

[ h2 - bin - h2.bat(click) - connet the h2 console
jdbc url : jdbc:h2:~/test
user name : sa ]

2. Copy queries in 'sturcture_question.sql' at sql folder in our project file and paste them into the h2 console, then execute.

3. Copy queries in 'sampledata_question.sql' at sql folder in our project file and paste them into the h2 console, then execute.

4. Finally, The initial data is saved after execution.

5. Terminate the h2 console.

6. Right click of our project file, and go run as-run configuration-arguments-VM arguments, then enter "-Dconf.location=app.properties"

7. Run the Launcher at eclipse. (press ‘ctrl+F11’)

## ORDER OF FUNCTION

When you run the program, there will be two parts; admin questions/student(solve a quiz).
The ‘admin questions’ part will be able to operate CRUD on Open Questions and MCQ Questions, and the ‘student(solve a quiz)’ part is for quizzes which questions are collected by nothing(so all questions), topics, and difficulty levels in them. 
Let’s talk about the ‘admin questions’ part. First, a manager can create/update/delete both open question and mcq question as many as he or she wants. Before update or delete, the manager can read a list of questions. It will include all information of questions. This function is same with ‘show all’. It will print everything in console. Plus, a manager can search questions by a topic.
Next is the ‘student(solve a quiz)’ part. First, a student can choose to one's quiz by nothing, topic, or difficulty and solve the quiz. Second, student can choose to extract the quiz to a file after every quiz is finished. Finally, the student scores are printed and the quiz ends. 

launcher  
> admin questions  
>  > insert(create)  
update  
delete  
show all(read)  
search by topics  

> student(solve a quiz)  
>  > insert student info  
>  >  > solve a quiz(all)  
solve a quiz(by topics)  
solve a quiz(by difficulty)  
  

## SCENARIO OF DATA MANAGEMENT
You can see figures at `User guide.docx`

1.	[Figure 1] is start page.

2.	If I enter ‘1’, the [Figure 2] is admin questions page. You should enter number from 1 to 5.
 
3.	Insert mode page [Figure 3]
 
4.	[Figure 4] You can insert a open question or a mcq question. Many questions are more than one line, so you should enter ‘end’ when you want to finish entering the question. 
You can enter several topics, and you have to enter ‘end’ when you want to finish entering it.
Because I answered ‘y’ to the question, I would enter a mcq question and enter the choices and answer. Otherwise, I would not see a screen to enter the choices and answer.
After the insertion is successful, a message and logs would appear, like “insert success” and “a question is created.” Then the program would ask me, “Do you want to insert again?”. If you answer “y”, you can insert again and again.
 
5.	Update and delete is almost similar. About question “What do you want to do?”, if you answer “2”, you’d go to update page, or if you answer “3”, you’d go to delete page.
At the [Figure 5], there is a delete page.
Before update or delete, you can see a list of questions if you want. You should enter ‘y’ or ‘n’.

6.	You can read all questions, because you enter ‘y’. After all questions, you can delete by following the form. [Figure 6]
You should select table by answering as ‘1’ or ‘2’.

7.	After select a table which the data you want to delete is in it, you can delete a row by entering a id. If it’s a MCQquestion, the linked MCQ info also will be deleted. Once it’s successful, there will be a sentence “A question is deleted.” Then if you want to delete another again, you must enter ‘y’. [Figure 7]

8.	This picture is for solving a quiz. At the start page, if you answer ‘2’, you can solve a quiz. Before start of the quiz, you should enter your name and id. Then select a type of quiz. At the picture, there was a quiz by difficulty1. [Figure 8]

9.	After finishing the quiz, you can extract the quiz to a file, and see your score of the quiz. [Figure 9]


## SCHEMAS

We have two tables in database, which are ‘Question’ and ‘MCQQuestion’. At table Question, the primary key is ID and it’s integer. Also, there are QUESTION and TOPIC of varchar type, and there are MCQ and DIFFICULTY of integer type.

  `Question`  
INT -	VARCHAR(1024) - INT	- VARCHAR(60) -	INT  
ID - QUESTION -	MCQ	- TOPIC	- DIFFICULTY

At table MCQQuestion, the primary key is MCQID and it’s integer. Also, there are CHOICE1, CHOICE2, CHOICE3, CHOICE4, and ANSWER of varchar type.

  `MCQQuestion`  
INT	-	VARCHAR(1024)	-	VARCHAR(1024)	-	VARCHAR(1024)	-	VARCHAR(1024)	-	VARCHAR(1024)  
MCQID	-	CHOICE1	-	CHOICE2	-	CHOICE3	-	CHOICE4	-	Answer
