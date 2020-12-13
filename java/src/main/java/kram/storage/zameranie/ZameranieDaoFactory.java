package kram.storage.zameranie;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;



public enum ZameranieDaoFactory {

	INSTATNCE;	
	private ZameranieDao zameranieDao;

	public ZameranieDao getZameranieDao() {
		if (zameranieDao == null){
			//userDao = new MemoryUserDao();
			zameranieDao = getMysqlUserDao();
		}
		return zameranieDao;
	}
	
	private ZameranieDao getMysqlUserDao() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("kram");
		dataSource.setPassword("heslo");
		//dataSource.setDatabaseName("entrance");

		dataSource.setUrl("jdbc:mysql://localhost/kram?serverTimezone=Europe/Bratislava");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return new MysqlZameranieDao(jdbcTemplate);
	}
}

