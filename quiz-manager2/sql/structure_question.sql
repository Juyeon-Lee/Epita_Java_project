//DROP TABLE QUESTION;

CREATE TABLE QUESTION (ID INT AUTO_INCREMENT PRIMARY KEY, QUESTION VARCHAR(1024), MCQ INT, TOPIC VARCHAR(60), DIFFICULTY INT);

CREATE TABLE MCQQUESTION (MCQID INT PRIMARY KEY,
CHOICE1 VARCHAR(1024), CHOICE2 VARCHAR(1024), CHOICE3 VARCHAR(1024), CHOICE4 VARCHAR(1024), ANSWER VARCHAR(1024));

ALTER TABLE QUESTION
	ADD FOREIGN KEY (MCQ)
	REFERENCES MCQQUESTION(MCQID)
