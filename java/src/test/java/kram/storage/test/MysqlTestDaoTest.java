package kram.storage.test;

import java.util.ArrayList;
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
	Question question2;
	
	QuestionDao questionDao;
	OptionDao optionDao;
	List<KramTest> tests;
	Map<Option,Boolean> options;
	
	public MysqlTestDaoTest() {
		testDao = DaoFactory.INSTATNCE.getTestDao(); 
		questionDao = DaoFactory.INSTATNCE.getQuestionDao(); 
		optionDao = DaoFactory.INSTATNCE.getOptionDao();  
	}

	@BeforeEach
	void setUp() throws Exception {
		
		Option choosen1 = optionDao.saveOption(new Option("Test test option true 1 "));
		Option choosen2 = optionDao.saveOption(new Option("Test test option true 2 "));
		options = new HashMap<Option,Boolean>();
		options.put(choosen1, true);
		options.put(choosen2, false);
		options.put(optionDao.saveOption(new Option("Test option false 3")), false);
		options.put(optionDao.saveOption(new Option("Test option false 4")), false);
		
		question = questionDao.saveQuestion(new Question("Test Question 2?", (long) 1, (long) 1, options));
		question2 = questionDao.saveQuestion(new Question("Test Question 3?", (long) 1, (long) 1, options));
		
		List<Question> questions = new ArrayList<Question>();
		questions.add(question);
		questions.add(question2);
		
		newTest = new KramTest((long) 1, (long) 1, "2020-12-15 18:21:15", "2020-12-21 18:00:00", 69);
		newTest.setQuestions(questions);
//		for (Map.Entry<Question, Option> entry : newTest.getAnswers().entries()) {
//			System.out.println(newTest.getAnswers().get(entry.getKey()));
//		}
//		newTest.addAnswer(question, question.getOption(choosen1.getIdOption()));
//		newTest.addAnswer(question, question.getOption(choosen2.getIdOption()));
		savedTest = testDao.saveTest(newTest);
	}

	@AfterEach
	void tearDown() throws Exception {
		testDao.deleteTest(savedTest.getIdTest());
//		System.out.println(question);
		questionDao.deleteQuestion(question.getIdQuestion());
		optionDao.deleteOptions(options);
	}

	@Test
	void test() {
//		fail("Not yet implemented");
	}

}
