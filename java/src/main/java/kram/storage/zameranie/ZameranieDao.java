package kram.storage.zameranie;

import java.util.List;

import kram.storage.EntityNotFoundException;

public interface ZameranieDao {
	List<Zameranie>getAllBySubjectId(long subjectId)throws EntityNotFoundException;
}
