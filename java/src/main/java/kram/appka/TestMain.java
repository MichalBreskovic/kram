package kram.appka;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.jdbc.object.MappingSqlQueryWithParameters;

import kram.storage.DaoFactory;
import kram.storage.Mail;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.test.TestDao;

public class TestMain {
	
	TestDao testDao = DaoFactory.INSTATNCE.getTestDao();
	
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	
	public static void main(String[] args) {
		QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
		OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();
//		Mail.send("");
		int numOfQuestions = 50;
		for (int i = 0; i < numOfQuestions; i++) {
			int a = (int)(Math.random()*20);
			int b = (int)(Math.random()*30);
			int c = a + b;
			int d = a - b;
			int e = a * b;
			Map<Option,Boolean> options = new HashMap<Option,Boolean>();
			options.put(optionDao.saveOption(new Option(c + "")), true);
			options.put(optionDao.saveOption(new Option(d + "")), false);
			options.put(optionDao.saveOption(new Option(e + "")), false);
//			options = optionDao.saveOptions(options);
			questionDao.saveQuestion(new Question( a + " + " + b + " ="  , 1L, 1L, options));
		}
		System.out.println("saved " + numOfQuestions + " questions");
	}

}
