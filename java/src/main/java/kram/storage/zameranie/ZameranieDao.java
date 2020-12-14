package kram.storage.zameranie;

import java.util.List;

import kram.storage.EntityNotFoundException;

public interface ZameranieDao {
	List<Zameranie>getAllBySubjectId(long subjectId)throws EntityNotFoundException;

	List<Zameranie> getAll() throws EntityNotFoundException;

	List<Zameranie> getBySubstring(String sub) throws NullPointerException;
}
