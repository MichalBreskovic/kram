package kram.storage.subject;

import java.util.List;

import kram.storage.EntityNotFoundException;




public interface SubjectDao {
	
	Subject saveSubject(Subject subject) throws EntityNotFoundException;
	Subject deleteSubject(long id) throws EntityNotFoundException;
	
	Subject getById(Long id) throws EntityNotFoundException;
	List<Subject> getAll() throws EntityNotFoundException;	
	List<Subject> getBySubstring(String sub) throws EntityNotFoundException;
	List<Subject> getAllForUser(long idUser) throws EntityNotFoundException;
//	List<Subject> getAllByTestUserId(long idTest, long idUser) throws EntityNotFoundException;
	
}
