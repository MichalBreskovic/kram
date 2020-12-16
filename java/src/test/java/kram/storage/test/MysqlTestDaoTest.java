package kram.storage.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;

class MysqlTestDaoTest {

	TestDao testDao;
	KramTest newTest;
	KramTest savedTest;
	Question question;
	
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
		
		Option choosen = optionDao.saveOption(new Option("Test test option true 1 "));
		
		options = new HashMap<Option,Boolean>();
		options.put(choosen, true);
		options.put(optionDao.saveOption(new Option("Test test option false 2")), false);
		options.put(optionDao.saveOption(new Option("Test test option false 3")), false);
		options.put(optionDao.saveOption(new Option("Test test option false 4")), false);
		
		Question question = questionDao.saveQuestion(new Question("Test Question 1?", (long) 1, (long) 1, options));
		
		newTest = new KramTest((long) 1, (long) 1, "2020-12-15 18:21:15", "2020-12-21 18:00:00", 69);
		newTest.addAnswer(question, question.getOption(choosen.getIdOption()));
		savedTest = testDao.saveTest(newTest);
	}

	@AfterEach
	void tearDown() throws Exception {
		testDao.deleteTest(savedTest.getIdTest());
		System.out.println(question);
		questionDao.deleteQuestion(question.getIdQuestion());
//		for(Map.Entry<Option, Option> pair : answers.entrySet()) {
//			optionDao.deleteOption(pair.getKey().getIdOption());
//		}
	}

	@Test
	void test() {
//		fail("Not yet implemented");
	}

}
