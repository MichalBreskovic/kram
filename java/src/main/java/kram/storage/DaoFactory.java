package kram.storage;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

import kram.storage.option.MysqlOptionDao;
import kram.storage.option.OptionDao;
import kram.storage.question.MysqlQuestionDao;
import kram.storage.question.QuestionDao;
import kram.storage.subject.MysqlSubjectDao;
import kram.storage.subject.SubjectDao;
import kram.storage.user.*;
import kram.storage.zameranie.MysqlZameranieDao;
import kram.storage.zameranie.ZameranieDao;


public enum DaoFactory {

	INSTATNCE;	
	private JdbcTemplate jdbcTemplate;
	private SubjectDao subjectDao;
	private UserDao userDao;
	private ZameranieDao zameranieDao;
	private QuestionDao questionDao;
	private OptionDao optionDao;
	
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
	
	private JdbcTemplate getJdbcTemplate() {
		if(jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("sql7381900");
			dataSource.setPassword("m9TBqahvjE");
			dataSource.setUrl("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7381900");
//			dataSource.setUser("kram");
//			dataSource.setPassword("12345");
//			dataSource.setUrl("jdbc:mysql://localhost/kram?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
