USE kram_test;

DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`(
	subject_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(45) NOT NULL,
	short VARCHAR(10)
);

DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`(
	topic_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	subject_id INT NOT null,
	title VARCHAR(45) NOT NULL,
	FOREIGN KEY(subject_id) REFERENCES `subject`(subject_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
	user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL unique,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	password VARCHAR(45) NOT NULL,
	teacher BOOLEAN
);

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`(
	question_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
	topic_id INT NOT NULL,
	title VARCHAR(100),
    # question MEDIUMBLOB,
	FOREIGN KEY (topic_id) REFERENCES topic(topic_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
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
    FOREIGN KEY(question_id) REFERENCES question (question_id) ON DELETE CASCADE,
    FOREIGN KEY(option_id) REFERENCES `option`(option_id) ON DELETE CASCADE,
    PRIMARY KEY(question_id, option_id)
);
 
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`(
	test_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_id INT,
    topic_id INT NOT NULL,
	time_start datetime,
	time_end datetime,
    hodnotenie int,
    FOREIGN KEY (topic_id) REFERENCES `topic`(topic_id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`(
	test_id INT NOT NULL,
	question_id INT NOT NULL,
    option_id INT NOT NULL,
    FOREIGN KEY (question_id , option_id) REFERENCES question_option (question_id , option_id) ON DELETE CASCADE,
    FOREIGN KEY (test_id) REFERENCES test(test_id) ON DELETE CASCADE,
	PRIMARY KEY (test_id, question_id, option_id)
);

INSERT INTO `subject`(title, short) VALUES ("matematika", "MAT");
INSERT INTO `subject`(title, short) VALUES ("informatika", "INF");
INSERT INTO `subject`(title, short) VALUES ("geografia", "GEO");

INSERT INTO topic(subject_id, title) VALUES (1, "algebra");
INSERT INTO topic(subject_id, title) VALUES (1, "analyza");

INSERT INTO user(name, surname, username, password, teacher) VALUES("teacher", "teacher", "teacher", "teacher", 1);
INSERT INTO user(name, surname, username, password, teacher) VALUES("user", "user", "user", "user", 0);
INSERT INTO user(name, surname, username, password, teacher) VALUES("Janko", "Múdry", "janko", "12345", 0);
INSERT INTO user(name, surname, username, password, teacher) VALUES("Jurko", "Hlúpy", "jurko", "54321", 0);

INSERT INTO question(title, topic_id, user_id) VALUES ("5 + 5 = ", 1, 1);
INSERT INTO question(title, topic_id, user_id) VALUES ("5 + 6 = ", 1, 2);
INSERT INTO question(title, topic_id, user_id) VALUES ("5 + 7 = ", 1, 1);

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

INSERT INTO `test`(user_id, topic_id, time_start, time_end, hodnotenie) VALUES(1, 1, "2020-12-15 21:21:15", "2020-12-21 08:00:00", "100");

INSERT INTO answer(test_id, question_id, option_id) VALUES(1, 1, 1);

