package kram.storage.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kram.storage.DaoFactory;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.test.KramTest;
import kram.storage.user.User;
import kram.storage.user.UserDao;

class MysqlCourseDaoTest {

	CourseDao courseDao;
	Course newCourse;
	Course savedCourse;
	
	UserDao userDao;
	User tempTeacher;
	User tempStudent;
	
	KramTest tempTest;
	
	public MysqlCourseDaoTest() {
		courseDao = DaoFactory.INSTATNCE.getCourseDao();
		userDao = DaoFactory.INSTATNCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		tempTeacher = userDao.saveUser(new User("TestName", "testTeacher", "TestSurname", "TestPassword", true));
		newCourse = new Course(tempTeacher.getIdUser(), "Test course");
		tempStudent = userDao.saveUser(new User("TestName", "testStudent", "TestSurname", "TestPassword", false));
		newCourse.addStudent(tempStudent.getIdUser());
		tempTest = new KramTest(tempStudent.getIdUser(), (long) 1, "2020", "2020", 100);
		newCourse.addTest(tempTest.getIdTest());
		savedCourse = courseDao.saveCourse(newCourse);
	}

	@AfterEach
	void tearDown() throws Exception {
		courseDao.deleteCourse(savedCourse.getIdCourse());
		userDao.deleteUser(tempTeacher.getIdUser());
		userDao.deleteUser(tempStudent.getIdUser());
	}

	@Test
	void test() {
//		fail("Not yet implemented");
	}

}
