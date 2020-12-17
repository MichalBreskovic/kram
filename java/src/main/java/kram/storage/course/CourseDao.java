package kram.storage.course;

import java.util.List;

import kram.storage.EntityNotFoundException;

public interface CourseDao {

	List<Course> getAll() throws EntityNotFoundException;
	Course getById(Long id) throws EntityNotFoundException;
	List<Course> getAllByTeacherId(Long id) throws EntityNotFoundException;
	List<Course> getAllByStudentId(Long id) throws EntityNotFoundException;
	Course saveCourse(Course question) throws EntityNotFoundException;
	Course deleteCourse(Long id) throws EntityNotFoundException;
	
}
