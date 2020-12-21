package kram.storage.question;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import kram.storage.DaoFactory;
import kram.storage.EntityNotFoundException;
import kram.storage.SHA256;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.user.User;
import kram.storage.user.UserDao;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

class MysqlQuestionDaoTest {

	QuestionDao questionDao;
	OptionDao optionDao;
	Question newQuestion;
	Question savedQuestion;
	List<Question> questions;
	Map<Option,Boolean> options;
	User newUser;
	Zameranie newTopic;
	UserDao userDao;
	ZameranieDao topicDao;
	
	public MysqlQuestionDaoTest() {
		questionDao = DaoFactory.INSTATNCE.getQuestionDao(); 
		optionDao = DaoFactory.INSTATNCE.getOptionDao();  
		userDao = DaoFactory.INSTATNCE.getUserDao();
		topicDao = DaoFactory.INSTATNCE.getZameranieDao();
	}
	
	
	@BeforeEach
	void setUp() throws Exception {
		newUser = userDao.saveUser(new User("Peter", "piko" ,"Vysoký", SHA256.getHash("12345"), true, "a@b"));
		newTopic = topicDao.saveZameranie(new Zameranie("CurrentTopic"));
		options = new HashMap<Option,Boolean>();
		options.put(optionDao.saveOption(new Option("KramTest option1")), true);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		optionDao.saveOptions(options);
		newQuestion = new Question("Je test test?", newTopic.getIdZameranie(), newUser.getIdUser(), options);
		savedQuestion = questionDao.saveQuestion(newQuestion);
	}

	@AfterEach
	void tearDown() throws Exception {
		questionDao.deleteQuestion(savedQuestion.getIdQuestion());
		optionDao.deleteOptions(options);
		userDao.deleteUser(newUser.getIdUser());
		
	}

//	@KramTest
//	void testGetAll() {
//		questions = questionDao.getAll();
//		for (Question question : questions) {
////			System.out.println(question.getIdQuestion() + " " + question + " " + question.getIdTopic() + " " + question.getIdUser());
//		}
//	}
	
	@Test
	void testGetAllByUserId() {
		questions = questionDao.getAllByUserId((long) 5);
		for (Question question : questions) {
			System.out.println(question.getIdQuestion() + " " + question + " " + question.getIdTopic() + " " + question.getIdUser());
		}
	}
	
//	@KramTest
//	void test() {
////		assertThrows(EntityNotFoundException.class, new Executable() {
////
////			@Override
////			public void execute() throws Throwable {
////				questionDao.getById(-1L);
////			}
////			
////		});
//	}

}
