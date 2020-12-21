package kram.storage.zameranie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kram.storage.DaoFactory;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.user.UserDao;

class MysqlZameranieDaoTest {
	Zameranie newTopic;

	ZameranieDao topicDao;
	Subject subject;

	SubjectDao subjectDao;
	
	public MysqlZameranieDaoTest() {

		topicDao = DaoFactory.INSTATNCE.getZameranieDao();
		subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	
	}

	@BeforeEach
	void setUp() throws Exception {
		subject = subjectDao.saveSubject(new Subject("subject", "SBJ"));
		newTopic = topicDao.saveZameranie(new Zameranie(subject.getIdSubject(),"CurrentTopic"));
	}

	@AfterEach
	void tearDown() throws Exception {
		topicDao.deleteZameranie(newTopic.getIdZameranie());
		subjectDao.deleteSubject(subject.getIdSubject());
	}

	@Test
	void testGetById() {
		assertEquals(newTopic.getIdZameranie(), topicDao.getById(newTopic.getIdZameranie()).getIdZameranie());
		assertEquals(newTopic.getTitle(), topicDao.getById(newTopic.getIdZameranie()).getTitle());
	}

}
