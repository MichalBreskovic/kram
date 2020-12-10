DROP DATABASE IF EXISTS kram;
CREATE DATABASE IF NOT EXISTS kram;
USE kram;

DROP TABLE IF EXISTS `predmet`;
CREATE TABLE `predmet`(
	predmet_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(45) NOT NULL,
	short VARCHAR(10)
);

DROP TABLE IF EXISTS `zameranie`;
CREATE TABLE `zameranie`(
	zameranie_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	predmet_id INT NOT null,
	title VARCHAR(45) NOT NULL,
	FOREIGN KEY(predmet_id) REFERENCES Predmet(predmet_id)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
	user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	heslo VARCHAR(45) NOT NULL,
	teacher BOOLEAN
);

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`(
	question_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(100),
    # question MEDIUMBLOB,
	zameranie_id INT NOT NULL,
	FOREIGN KEY (zameranie_id) REFERENCES Zameranie(zameranie_id)
);

DROP TABLE IF EXISTS `option`;
CREATE TABLE `option`(
	option_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100)
    # answer MEDIUMBLOB
); 

DROP TABLE IF EXISTS `question_option`;
CREATE TABLE `question_option`(
	question_id INT NOT NULL,
    option_id INT NOT NULL,
    correct BOOLEAN,
    FOREIGN KEY(question_id) REFERENCES question(question_id),
    FOREIGN KEY(option_id) REFERENCES `option`(option_id),
    PRIMARY KEY(question_id, option_id)
);
 
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`(
	test_id INT NOT NULL PRIMARY KEY,
	user_id INT NOT NULL,
	time_start datetime,
	time_end datetime,
    hodnotenie int,
	FOREIGN KEY (user_id) REFERENCES User(user_id)
);

DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`(
	test_id INT NOT NULL,
	question_id INT NOT NULL,
    option_id INT NOT NULL,
    FOREIGN KEY (question_id , option_id) REFERENCES question_option (question_id , option_id),
    FOREIGN KEY(test_id) REFERENCES test(test_id),
	PRIMARY KEY (test_id, question_id, option_id)
);

INSERT INTO predmet(title, short) VALUES ("matematika", "MAT");
INSERT INTO predmet(title, short) VALUES ("informatika", "INF");
INSERT INTO predmet(title, short) VALUES ("geografia", "GEO");

INSERT INTO zameranie(predmet_id, title) VALUES (1, "algebra");
INSERT INTO zameranie(predmet_id, title) VALUES (1, "analyza");

INSERT INTO question(title, zameranie_id) VALUES ("5 + 5 = ", 1);
INSERT INTO question(title, zameranie_id) VALUES ("5 + 6 = ", 1);

INSERT INTO `option`(title) VALUES ("5");
INSERT INTO `option`(title) VALUES ("6");
INSERT INTO `option`(title) VALUES ("7");
INSERT INTO `option`(title) VALUES ("10");
INSERT INTO `option`(title) VALUES ("11");
INSERT INTO `option`(title) VALUES ("12");
INSERT INTO `option`(title) VALUES ("13");
INSERT INTO `option`(title) VALUES ("14");

INSERT INTO question_option(question_id, option_id, correct) VALUES(1,1, false);
INSERT INTO question_option(question_id, option_id, correct) VALUES(1,2, false);
INSERT INTO question_option(question_id, option_id, correct) VALUES(1,3, false);
INSERT INTO question_option(question_id, option_id, correct) VALUES(1,4, true);

INSERT INTO question_option(question_id, option_id, correct) VALUES(2,1, false);
INSERT INTO question_option(question_id, option_id, correct) VALUES(2,2, false);
INSERT INTO question_option(question_id, option_id, correct) VALUES(2,3, false);
INSERT INTO question_option(question_id, option_id, correct) VALUES(2,5, true);

SELECT * FROM User;
SELECT p.title, z.title FROM predmet p LEFT OUTER JOIN zameranie z USING(predmet_id);
SELECT z.title, q.title, o.title, qo.correct FROM question_option qo JOIN question q USING(question_id) JOIN `option` o USING(option_id) JOIN zameranie z USING(zameranie_id);

SELECT * FROM zameranie;
SELECT * FROM question;
SELECT * FROM test;
 
ALTER TABLE user ADD COLUMN username VARCHAR(45) NOT NULL unique;