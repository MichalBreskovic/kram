package kram.storage.zameranie;

import java.util.List;

import kram.storage.EntityNotFoundException;
import kram.storage.subject.Subject;

public interface ZameranieDao {
	
	Zameranie saveZameranie(Zameranie zameranie) throws EntityNotFoundException, NullPointerException;
	Zameranie getById(long idUser) throws NullPointerException;

	List<Zameranie> getAll() throws EntityNotFoundException;
	List<Zameranie> getAllBySubjectId(long subjectId)throws EntityNotFoundException,NullPointerException;
	List<Zameranie> getBySubstring(String sub) throws NullPointerException;
	List<Zameranie> getAllForTeacher(long idUser) throws NullPointerException;
	List<Zameranie> getAllForTeacherBySubjectId(long idUser, long idSubject) throws NullPointerException;
	List<Zameranie> getBySubstringSubjectId(String sub, long subjectId) throws NullPointerException;
//	List<Zameranie> getAllByTestUserId(long idTest, long idUser) throws EntityNotFoundException;
	Zameranie deleteZameranie(long id) throws EntityNotFoundException;
}
