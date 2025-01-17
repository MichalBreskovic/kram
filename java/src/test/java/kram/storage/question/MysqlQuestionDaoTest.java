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
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
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
	Subject newSubject;
	UserDao userDao;
	ZameranieDao topicDao;
	SubjectDao subjectDao;
	
	public MysqlQuestionDaoTest() {
		questionDao = DaoFactory.INSTATNCE.getQuestionDao(); 
		optionDao = DaoFactory.INSTATNCE.getOptionDao();  
		userDao = DaoFactory.INSTATNCE.getUserDao();
		topicDao = DaoFactory.INSTATNCE.getZameranieDao();
		subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	}
	
	
	@BeforeEach
	void setUp() throws Exception {
		newUser = userDao.saveUser(new User("Peter", "piko" ,"Vysok�", SHA256.getHash("12345"), true, "a@b"));
		newSubject = subjectDao.saveSubject(new Subject("Test Subject", "TST"));
//		System.out.println(newSubject);
		newTopic = topicDao.saveZameranie(new Zameranie(newSubject.getIdSubject(), "CurrentTopic"));
		options = new HashMap<Option,Boolean>();
		options.put(optionDao.saveOption(new Option("KramTest option1")), true);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		
		newQuestion = new Question("Je test test?", newTopic.getIdZameranie(), newUser.getIdUser(), options);
		savedQuestion = questionDao.saveQuestion(newQuestion);
	}

	@AfterEach
	void tearDown() throws Exception {
		questionDao.deleteQuestion(savedQuestion.getIdQuestion());
		optionDao.deleteOptions(options);
		userDao.deleteUser(newUser.getIdUser());
		topicDao.deleteZameranie(newTopic.getIdZameranie());
		subjectDao.deleteSubject(newSubject.getIdSubject());
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
		questions = questionDao.getAllByUserId(newUser.getIdUser());
		for (Question question : questions) {
//			System.out.println(question.getIdQuestion() + " " + question + " " + question.getIdTopic() + " " + question.getIdUser());
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
