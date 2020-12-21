package kram.storage.subject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kram.storage.DaoFactory;

class MysqlSubjectDaoTest {
	
	SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	
	Subject newSubject;
	Subject savedSubject;
	
	@BeforeEach
	void setUp() throws Exception {
		newSubject = new Subject("Test subject");
		savedSubject = subjectDao.saveSubject(newSubject);
	}

	@AfterEach
	void tearDown() throws Exception {
		subjectDao.deleteSubject(savedSubject.getIdSubject());
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
