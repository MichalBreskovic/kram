package kram.storage.option;

import kram.storage.EntityNotFoundException;

public interface OptionDao {
	
	Option getOption(Long id) throws EntityNotFoundException;
	Option saveOption(Option option) throws EntityNotFoundException;
	Option deleteOption(Long id) throws EntityNotFoundException;
}
