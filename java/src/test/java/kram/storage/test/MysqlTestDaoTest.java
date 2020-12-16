package kram.storage.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.test.KramTest;

class MysqlTestDaoTest {

	TestDao testDao;
	KramTest newTest;
	KramTest savedTest;
	
	QuestionDao questionDao;
	OptionDao optionDao;
	List<KramTest> tests;
	Map<Question,Option> answers;
	Map<Option,Boolean> options;
	
	public MysqlTestDaoTest() {
		testDao = DaoFactory.INSTATNCE.getTestDao(); 
		questionDao = DaoFactory.INSTATNCE.getQuestionDao(); 
		optionDao = DaoFactory.INSTATNCE.getOptionDao();  
	}

	@BeforeEach
	void setUp() throws Exception {
		
		Option choosen = optionDao.saveOption(new Option("Test test option true"));
		
		options = new HashMap<Option,Boolean>();
		options.put(choosen, true);
		options.put(optionDao.saveOption(new Option("Test test option false")), false);
		options.put(optionDao.saveOption(new Option("Test test option false")), false);
		options.put(optionDao.saveOption(new Option("Test test option false")), false);
		
		Question q = questionDao.saveQuestion(new Question("Test Question 1?", (long) 1, (long) 1, options));
		
//		answers = new HashMap<Question,Option>();
//		answers.put(questionDao.saveQuestion(q), q.getOption(choosen.getIdOption()));
		newTest= new KramTest((long) 1, (long) 1, "2020-12-15 18:21:15", "2020-12-21 18:00:00", 69);
		newTest.addAnswer(q, q.getOption(choosen.getIdOption()));
		savedTest = testDao.saveTest(newTest);
	}

	@AfterEach
	void tearDown() throws Exception {
//		testDao.deleteTest(savedTest.getIdTest());
//		for (Map.Entry<Question, Option> pair : answers.entrySet()) {
//			optionDao.deleteOption(pair.getKey().getIdOption());
//		}
	}

	@Test
	void test() {
//		fail("Not yet implemented");
	}

}
