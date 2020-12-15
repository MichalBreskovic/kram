package kram.storage.subject;

import java.util.List;

import kram.storage.EntityNotFoundException;




public interface SubjectDao {
	Subject saveSubject(Subject subject)throws EntityNotFoundException,NullPointerException;
	List<Subject> getAll();	
	Subject delete(long id) throws EntityNotFoundException;
	List<Subject> getBySubstring(String sub) throws NullPointerException;
	List<Subject> getAllForTeacher(long idUser)throws NullPointerException;
	
}
