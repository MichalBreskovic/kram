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
import kram.storage.option.Option;
import kram.storage.option.OptionDao;

class MysqlQuestionDaoTest {

	QuestionDao questionDao;
	OptionDao optionDao;
	Question newQuestion;
	Question savedQuestion;
	List<Question> questions;
	Map<Option,Boolean> options;
	
	public MysqlQuestionDaoTest() {
		questionDao = DaoFactory.INSTATNCE.getQuestionDao(); 
		optionDao = DaoFactory.INSTATNCE.getOptionDao();  
	}
	
	
	@BeforeEach
	void setUp() throws Exception {
		options = new HashMap<Option,Boolean>();
		options.put(optionDao.saveOption(new Option("KramTest option1")), true);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		options.put(optionDao.saveOption(new Option("KramTest option1")), false);
		newQuestion = new Question("Je test test?", (long) 1, (long) 1, options);
		savedQuestion = questionDao.saveQuestion(newQuestion);
	}

	@AfterEach
	void tearDown() throws Exception {
		questionDao.deleteQuestion(savedQuestion.getIdQuestion());
		for (Map.Entry<Option, Boolean> pair : options.entrySet()) {
			optionDao.deleteOption(pair.getKey().getIdOption());
		}
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
