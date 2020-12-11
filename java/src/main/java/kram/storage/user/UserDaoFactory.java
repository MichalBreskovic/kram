package kram.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

import kram.storage.MysqlUserDao;



public enum UserDaoFactory {

	INSTATNCE;	
	private UserDao userDao;
	
	public UserDao getUserDao() {
		if (userDao == null){
			//userDao = new MemoryUserDao();
			userDao = getMysqlUserDao();
		}
		return userDao;
	}
	
	private UserDao getMysqlUserDao() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("kram");
		dataSource.setPassword("heslo");
		//dataSource.setDatabaseName("entrance");

		dataSource.setUrl("jdbc:mysql://localhost/kram?serverTimezone=Europe/Bratislava");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return new MysqlUserDao(jdbcTemplate);
	}
}
