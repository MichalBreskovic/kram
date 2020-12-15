package kram.storage.test;

import kram.storage.EntityNotFoundException;

interface TestDao {

	Test getAll() throws EntityNotFoundException;
	Test getById(Long id) throws EntityNotFoundException;
	Test saveTest(Test test) throws EntityNotFoundException;
	Test deleteUser(Long id) throws EntityNotFoundException;
	
}
