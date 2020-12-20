package kram.storage.test;

import java.util.List;

import kram.storage.EntityNotFoundException;

public interface TestDao {

	List<KramTest> getAll() throws EntityNotFoundException;
	KramTest getById(Long id) throws EntityNotFoundException;
	KramTest saveTest(KramTest kramTest) throws EntityNotFoundException;
	KramTest saveTest2(KramTest kramTest) throws EntityNotFoundException;
	KramTest saveTestToCourse(KramTest kramTest, long idCourse) throws EntityNotFoundException;
	KramTest deleteTest(Long id) throws EntityNotFoundException;
	
	List<KramTest> getAllBySubjectId(Long id) throws EntityNotFoundException;
	List<KramTest> getAllInfo(long userId) throws EntityNotFoundException;
	List<KramTest> getAllInfoByCourse(long idUser, long idCourse) throws EntityNotFoundException;
	List<KramTest> getAllByCourseTeacherId(Long id, Long idUser) throws EntityNotFoundException;
	List<KramTest> getAllByCourseId(Long id) throws EntityNotFoundException;
	List<KramTest> getAllByTopicId(Long id) throws EntityNotFoundException;
	List<KramTest> getAllBySubjectUserId(Long id, Long userId) throws EntityNotFoundException;
	List<KramTest> getAllByTopicUserId(Long id, Long userId) throws EntityNotFoundException;
}
