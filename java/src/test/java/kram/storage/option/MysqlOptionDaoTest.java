package kram.storage.option;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import kram.storage.EntityNotFoundException;
import kram.storage.*;


class MysqlOptionDaoTest {

	OptionDao optionDao;
	Option newOption;
	Option savedOption;
	
	public MysqlOptionDaoTest() {
		optionDao = DaoFactory.INSTATNCE.getOptionDao();  
	}

	@BeforeEach
	void setUp() throws Exception {
		newOption = new Option("TestTest");
		savedOption = optionDao.saveOption(newOption);
	}

	@AfterEach
	void tearDown() throws Exception {
		optionDao.deleteOption(savedOption.getIdOption());
	}

	@Test
	void testGetById() {
		assertThrows(EntityNotFoundException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				optionDao.getById(-1L);
			}
			
		});
//		Option byId = optionDao.getOption(savedOption.getIdOption());
//		assertEquals(savedOption.getIdOption() , byId.getIdOption());
//		assertEquals(newOption.getTitle(), byId.getTitle());
	}

}
