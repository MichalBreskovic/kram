package kram.storage.question;

import static org.junit.jupiter.api.Assertions.*;

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
	Question newQuestion;
	Question savedQuestion;
	
	public MysqlQuestionDaoTest() {
//		DaoFactory.INSTANCE.testing();
		questionDao = DaoFactory.INSTATNCE.getQuestionDao();  
	}
	
	
	@BeforeEach
	void setUp() throws Exception {
		newQuestion = new Question("Je test test?", (long) 1);
		savedQuestion = questionDao.saveQuestion(newQuestion);
	}

	@AfterEach
	void tearDown() throws Exception {
		questionDao.deleteQuestion(savedQuestion.getIdQuestion());
	}

	@Test
	void test() {
		assertThrows(EntityNotFoundException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				questionDao.getQuestion(-1L);
			}
			
		});
	}

}
