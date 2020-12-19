package kram.storage.subject;

import java.util.List;

import kram.storage.EntityNotFoundException;




public interface SubjectDao {
	Subject saveSubject(Subject subject) throws EntityNotFoundException;
	List<Subject> getAll() throws EntityNotFoundException;	
	Subject deleteSubject(long id) throws EntityNotFoundException;
	List<Subject> getBySubstring(String sub) throws EntityNotFoundException;
	List<Subject> getAllForTeacher(long idUser) throws EntityNotFoundException;
	Subject getById(Long id) throws EntityNotFoundException;
	List<Subject> getAllByTestUserId(long idTest, long idUser) throws EntityNotFoundException;
	
}
