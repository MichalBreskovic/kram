package kram.storage.test;

import java.util.List;

import kram.storage.EntityNotFoundException;

public interface TestDao {

	List<KramTest> getAll() throws EntityNotFoundException;
	KramTest getById(Long id) throws EntityNotFoundException;
	KramTest saveTest(KramTest kramTest) throws EntityNotFoundException;
	KramTest deleteTest(Long id) throws EntityNotFoundException;
	List<KramTest> getByUserId(Long id) throws EntityNotFoundException;
	KramTest getByTopicId(Long id) throws EntityNotFoundException;
	
}
