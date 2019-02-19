INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(1,'Equal', 'Not Equal', 'The code will not compile',
'The code will compile but will throw run time error', 'Not Equal');
	
INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES 
('What will happen when you compile and run the following code?
public class Test{
	
	public static void main(String[] args){
		
		double d1 = Double.NaN;		
		double d2 = d1;
		
		if(d1 == d2)
			System.out.println("Equal");
		else
			System.out.println("Not Equal");
	}
	
}

', 1,'Java Language Basics',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(2,'10 25','35','67','The code will not compile', '10 25');
	
INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES 
('What will happen when you compile and run the following code?
public class Test{	
	
	public static void main(String[] args){
		int i = 10;
		int j = 25;
		System.out.println(i + " " + j);
	}
}

', 2,'Java Language Basics',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(3,'13','23','22','12', '23');
	
INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES 
('What will happen when you compile and run the following code?
public class Test {
	
	public static void main(String[] args){		
		
		int i = 10;
		int j = 12;
		i+=++j;
		System.out.println(i);

	}
}

', 3,'Java Language Basics',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(4,'Compilation error','Runtime error','8','10','8');

INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES 
('What will happen when you compile and run the following code?
public class Test {
	
	public static void main(String[] args){
		int i = 010;
		System.out.println(i);
	}
}

', 4,'Java Language Basics',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(5,'1','3','2','4','2');

INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES
('What will happen when you compile and run the following code?
public class Test{
	
	public static void main(String[] args) {
		int i = 0;
		int j = i++ + ++i;
		System.out.println( j );
	}
}

', 5,'Java Operators',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(6,'3','2','4','1','2');

INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES
('What will happen when you compile and run the following code?
public class Test{
	
	public static void main(String[] args) {
		int i = 0;
		int j = ++i + i++;
		System.out.println( j );
	}
}

', 6,'Java Operators',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(7,'10','0','Compilation error','None of the above','0');

INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES
('What will happen when you compile and run the following code?
public class Test{
	
	public static void main(String[] args) {
		int i = 100;
		int j = 10;
		System.out.println( i%j );
	}
}

', 7,'Java Operators',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(8,'1','2','Compilation error','Runtime error','Runtime error');

INSERT INTO QUESTION (QUESTION,TOPIC,DIFFICULTY) VALUES
('What will happen when you compile and run the following code?
public class Test{
	
	public static void main(String[] args) {
		int i = 0;
		int j = 1;
		if(!i && j)
			System.out.println("1");
		else
			System.out.println("2");
	}
}

', 'Java Operators',1);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(9,'1','2','Compilation error','Runtime error','1');

INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES
('What will happen when you compile and run the following code?
class One{
	int i = 1;
	public int getInt(){
		return i;
	}
}

class Two extends One{
	int i = 2;
	public int getInt(){
		return i;
	}	
}
public class Test{
	public static void main(String[] args) {
		One one = new One();
		Two two = (Two)one;
		System.out.println( two.getInt() );
	}
}

', 8,'Java Object Oriented Programming',3);

INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES
('What will happen when you compile and run the following code?
class One{	
	public static void print(){
		System.out.println("1");
	}
}

class Two extends One{
	public static void print(){
		System.out.println("2");
	}
}

public class Test{	
	public static void main(String args[]){
		One one = new Two();
		one.print();
	}	
}

', 9,'Java Object Oriented Programming',3);

INSERT INTO MCQQUESTION (MCQID,CHOICE1,CHOICE2,CHOICE3,CHOICE4,ANSWER) VALUES
(10,'Parent','Child','Compilation error','Runtime error','Parent');

INSERT INTO QUESTION (QUESTION,MCQ,TOPIC,DIFFICULTY) VALUES
('What will happen when you compile and run the following code?
class One{	
	public static void print(int i){
		System.out.println("Parent");
	}
}

class Two extends One{
	public static void print(byte b){
		System.out.println("Child");
	}
}

public class Test{
	
	public static void main(String args[]){
		One one = new Two();
		one.print(10);
	}	
}

', 10,'Java Object Oriented Programming',3);

INSERT INTO QUESTION (QUESTION,TOPIC,DIFFICULTY) VALUES
('Will this code compile successfully?
class One{
	public void process(){
		System.out.println("Parent");
	}
}

public abstract class Test extends One{
	public abstract void process();
}

','Java Object Oriented Programming',2);

INSERT INTO QUESTION (QUESTION,TOPIC,DIFFICULTY) VALUES
('What is your university name?','communication,introduction',1);

ALTER TABLE MCQQUESTION
  ALTER COLUMN MCQID
  INT AUTO_INCREMENT