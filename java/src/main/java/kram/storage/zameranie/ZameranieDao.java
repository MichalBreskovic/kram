package kram.storage.zameranie;

import java.util.List;

import kram.storage.EntityNotFoundException;
import kram.storage.subject.Subject;

public interface ZameranieDao {
	List<Zameranie>getAllBySubjectId(long subjectId)throws EntityNotFoundException;

	List<Zameranie> getAll() throws EntityNotFoundException;

	List<Zameranie> getBySubstring(String sub) throws NullPointerException;

	List<Zameranie> getAllForTeacher(long idUser) throws NullPointerException;



	Zameranie saveZameranie(Zameranie zameranie) throws EntityNotFoundException, NullPointerException;
}
