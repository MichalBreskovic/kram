package kram.storage.course;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import kram.storage.DaoFactory;
import kram.storage.EntityNotFoundException;
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
	User tempStudent2;
	User tempStudent3;
	
	KramTest tempTest;
	
	public MysqlCourseDaoTest() {
		courseDao = DaoFactory.INSTATNCE.getCourseDao();
		userDao = DaoFactory.INSTATNCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		tempTeacher = userDao.saveUser(new User("TestName", "testTeacher", "TestSurname", "TestPassword", true, "a@b"));
		newCourse = new Course(tempTeacher.getIdUser(), "Test course");
		tempStudent = userDao.saveUser(new User("TestName", "testStudent", "TestSurname", "TestPassword", false, "a@b"));
		tempStudent2 = userDao.saveUser(new User("TestName2", "testStudent2", "TestSurname2", "TestPassword2", false, "a@b"));
		tempStudent3 = userDao.saveUser(new User("TestName3", "testStudent3", "TestSurnameš", "TestPassword3", false, "a@b"));
		newCourse.addStudent(tempStudent.getIdUser());
		newCourse.addStudent(tempStudent2.getIdUser());
		
		tempTest = new KramTest(tempStudent.getIdUser(), (long) 1, "2020", "2020", 100);
		newCourse.addTest(tempTest.getIdTest());
		savedCourse = courseDao.saveCourse(newCourse);
		courseDao.addToCourse(savedCourse.getIdCourse(), tempStudent.getIdUser());
		courseDao.addToCourse(savedCourse.getIdCourse(), tempStudent2.getIdUser());
		courseDao.acceptDismissStudent(1, tempStudent2.getIdUser(), savedCourse.getIdCourse());
	}

	@AfterEach
	void tearDown() throws Exception {
		courseDao.deleteCourse(savedCourse.getIdCourse());
		userDao.deleteUser(tempTeacher.getIdUser());
		userDao.deleteUser(tempStudent.getIdUser());
		userDao.deleteUser(tempStudent2.getIdUser());
		userDao.deleteUser(tempStudent3.getIdUser());
	}

	@Test
	void testGetAll() {
		List<Course> courses = courseDao.getAll();
        assertTrue(courses.size() > 0);
        assertNotNull(courses.get(0).getName());
        assertNotNull(courses.get(0).getIdCourse());
        assertNotNull(courses.get(0).getIdUser());
        assertNotNull(courses.get(0).getIdUser());
        List<User> waitin =userDao.getAllWaitingInCourse(savedCourse.getIdCourse());
        assertTrue(waitin.size() > 0);
        assertTrue(waitin.get(0).getIdUser()==tempStudent.getIdUser());
//        assertTrue(waitin.get(1).getIdUser()==tempStudent2.getIdUser());
        assertTrue(waitin.get(0).getName().equals(tempStudent.getName()));
//        assertTrue(waitin.get(1).getName().equals(tempStudent2.getName()));

        
	}
	//not throwing entity not found due to left outer join, throwing null
	@Test
	void testGetById() {
		assertNull(courseDao.getById(-1L));
		assertTrue(savedCourse.getName().equals(courseDao.getById(savedCourse.getIdCourse()).getName()));
		assertTrue(savedCourse.getIdUser()==courseDao.getById(savedCourse.getIdCourse()).getIdUser());
		
	}
	@Test
	void testAddToCourse() {
		assertThrows(EntityNotFoundException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				courseDao.addToCourse(savedCourse.getIdCourse(), -1L);
				
			}
		});
		courseDao.addToCourse(savedCourse.getIdCourse(), tempStudent3.getIdUser());
		assertTrue(userDao.getAllWaitingInCourse(savedCourse.getIdCourse()).get(0).getIdUser()==tempStudent3.getIdUser());
	}
	
	

}
