package kram.storage;

import org.springframework.jdbc.core.JdbcTemplate;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import kram.storage.course.CourseDao;
import kram.storage.course.MysqlCourseDao;
import kram.storage.option.MysqlOptionDao;
import kram.storage.option.OptionDao;
import kram.storage.question.MysqlQuestionDao;
import kram.storage.question.QuestionDao;
import kram.storage.subject.MysqlSubjectDao;
import kram.storage.subject.SubjectDao;
import kram.storage.user.*;
import kram.storage.zameranie.MysqlZameranieDao;
import kram.storage.zameranie.ZameranieDao;
import kram.storage.test.MysqlTestDao;
import kram.storage.test.TestDao;

public enum DaoFactory {

	INSTATNCE;	
	private JdbcTemplate jdbcTemplate;
	private SubjectDao subjectDao;
	private UserDao userDao;
	private ZameranieDao zameranieDao;
	private QuestionDao questionDao;
	private OptionDao optionDao;
	private TestDao testDao;
	private CourseDao courseDao;
	
	private static final boolean MSSQL = false;
	private static final boolean TEST = true;
	
	public ZameranieDao getZameranieDao() {
		if (zameranieDao == null){
			zameranieDao = new MysqlZameranieDao(getJdbcTemplate());
		}
		return zameranieDao;
	}
	
	public SubjectDao getSubjectDao() {
		if (subjectDao == null){
			subjectDao = new MysqlSubjectDao(getJdbcTemplate());
		}
		return subjectDao;
	}
	
	public UserDao getUserDao() {
		if (userDao == null){
			userDao = new MysqlUserDao(getJdbcTemplate());
		}
		return userDao;
	}
	
	public QuestionDao getQuestionDao() {
		if (questionDao == null){
			questionDao = new MysqlQuestionDao(getJdbcTemplate());
		}
		return questionDao;
	}
	
	public OptionDao getOptionDao() {
		if (optionDao == null){
			optionDao = new MysqlOptionDao(getJdbcTemplate());
		}
		return optionDao;
	}
	
	public TestDao getTestDao() {
		if (testDao == null){
			testDao = new MysqlTestDao(getJdbcTemplate());
		}
		return testDao;
	}
	
	public CourseDao getCourseDao() {
		if (courseDao == null){
			courseDao = new MysqlCourseDao(getJdbcTemplate());
		}
		return courseDao;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		if(jdbcTemplate == null) {
			if(MSSQL) {
				SQLServerDataSource ds = new SQLServerDataSource();
		        ds.setUser("Michal");
		        ds.setServerName("MICHAL-PC");
		        ds.setURL("jdbc:sqlserver://localhost/kram");
				jdbcTemplate = new JdbcTemplate(ds);
			} else {
				MysqlDataSource dataSource = new MysqlDataSource();
				dataSource.setUser("data_access");
				dataSource.setPassword("m9TBqahvjE");
				if(TEST) dataSource.setUrl("jdbc:mysql://34.65.200.19:3306/kram_test");
				else dataSource.setUrl("jdbc:mysql://34.65.200.19:3306/kram");
				//dataSource.setUser("kram");
				//dataSource.setPassword("ahoj12345");
				//dataSource.setUrl("jdbc:mysql://localhost/kram?serverTimezone=Europe/Bratislava");
				jdbcTemplate = new JdbcTemplate(dataSource);
			}
		}
		return jdbcTemplate;
	}
}
