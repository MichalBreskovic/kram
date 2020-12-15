package kram.storage.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kram.storage.DaoFactory;
import kram.storage.option.Option;

class MysqlUserDaoTest {

	UserDao userDao;
	User newUser;
	User savedUser;
	
	public MysqlUserDaoTest() {
		userDao = DaoFactory.INSTATNCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		newUser = new User("Peter", "piko" ,"Vysoký", "12345", true);
		savedUser = userDao.saveUser(newUser);
	}

	@AfterEach
	void tearDown() throws Exception {
		userDao.deleteUser(savedUser.getIdUser());
	}

	@Test
	void test() {
//		fail("Not yet implemented");
	}

}
