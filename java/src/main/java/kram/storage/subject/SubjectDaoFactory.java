package kram.storage.subject;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;


public enum SubjectDaoFactory {

	INSTATNCE;	
	private SubjectDao subjectDao;
	
	public SubjectDao getSubjectDao() {
		if (subjectDao == null){
			//userDao = new MemoryUserDao();
			subjectDao = getMysqlSubjectDao();
		}
		return subjectDao;
	}
	
	private SubjectDao getMysqlSubjectDao() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("kram");
		dataSource.setPassword("heslo");
		//dataSource.setDatabaseName("entrance");

		dataSource.setUrl("jdbc:mysql://localhost/kram?serverTimezone=Europe/Bratislava");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return new MysqlSubjectDao(jdbcTemplate);
	}
}
