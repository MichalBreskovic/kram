DROP DATABASE IF EXISTS kram;
CREATE DATABASE IF NOT EXISTS kram;
USE kram;

DROP TABLE IF EXISTS logy;
DROP TABLE IF EXISTS tests;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS zameranie;
DROP TABLE IF EXISTS predmet;
DROP TABLE IF EXISTS user;

CREATE TABLE predmet(
	predmet_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(45) NOT NULL,
	short VARCHAR(10)
);

CREATE TABLE zameranie(
	zameranie_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	predmet_id INT NOT null,
	title VARCHAR(45) NOT NULL,
	FOREIGN KEY(predmet_id) REFERENCES Predmet(predmet_id)
);

CREATE TABLE user(
	user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	heslo VARCHAR(45) NOT NULL,
	teacher BOOLEAN
);

CREATE TABLE questions(
	question_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(100),
    # question MEDIUMBLOB,
	zameranie_id INT NOT NULL,
	FOREIGN KEY (zameranie_id) REFERENCES Zameranie(zameranie_id)
);

CREATE TABLE answers(
	answer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100)
    # answer MEDIUMBLOB
); 

CREATE TABLE questions_answers(
	question_id INT NOT NULL,
    answer_id INT NOT NULL,
    correct BOOLEAN,
    FOREIGN KEY(question_id) REFERENCES questions(question_id),
    FOREIGN KEY(answer_id) REFERENCES answers(answer_id),
    PRIMARY KEY(question_id,answer_id)
);
 
CREATE TABLE logy(
	logy_id INT NOT NULL PRIMARY KEY,
	user_id INT NOT NULL,
	time_start datetime,
	time_end datetime,
    hodnotenie int,
	FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE tests(
	logy_id INT NOT NULL,
	question_id INT NOT NULL,
    answer_id INT NOT NULL,
	FOREIGN KEY(question_id) REFERENCES questions(question_id),
    FOREIGN KEY(logy_id) REFERENCES Logy(logy_id),
    PRIMARY KEY(question_id,logy_id)
);

INSERT INTO predmet(title, short) VALUES ("matematika", "MAT");
INSERT INTO predmet(title, short) VALUES ("informatika", "INF");
INSERT INTO predmet(title, short) VALUES ("geografia", "GEO");

INSERT INTO zameranie(predmet_id, title) VALUES (1, "algebra");
INSERT INTO zameranie(predmet_id, title) VALUES (1, "analyza");

INSERT INTO questions(title, zameranie_id) VALUES ("5 + 5 = ", 1);
INSERT INTO questions(title, zameranie_id) VALUES ("5 + 6 = ", 1);

INSERT INTO answers(title) VALUES ("5");
INSERT INTO answers(title) VALUES ("6");
INSERT INTO answers(title) VALUES ("7");
INSERT INTO answers(title) VALUES ("10");
INSERT INTO answers(title) VALUES ("11");
INSERT INTO answers(title) VALUES ("12");
INSERT INTO answers(title) VALUES ("13");
INSERT INTO answers(title) VALUES ("14");

INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(1,1, false);
INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(1,2, false);
INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(1,3, false);
INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(1,4, true);

INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(2,1, false);
INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(2,2, false);
INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(2,3, false);
INSERT INTO questions_answers(question_id, answer_id, correct) VALUES(2,5, true);

SELECT * FROM User;
SELECT p.title, z.title FROM predmet p LEFT OUTER JOIN zameranie z USING(predmet_id);
SELECT z.title, q.title, a.title, qa.correct FROM questions_answers qa JOIN questions q USING(question_id) JOIN Answers a USING(answer_id) JOIN zameranie z USING(zameranie_id);

SELECT * FROM zameranie;
SELECT * FROM questions;
SELECT * FROM logy;
 