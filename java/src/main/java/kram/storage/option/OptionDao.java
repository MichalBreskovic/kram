package kram.storage.option;

import java.util.Map;

import kram.storage.EntityNotFoundException;

public interface OptionDao {
	
	Option getById(Long id) throws EntityNotFoundException;
	Option saveOption(Option option) throws EntityNotFoundException;
	Option deleteOption(Long id) throws EntityNotFoundException;
	Map<Option,Boolean> deleteOptions(Map<Option,Boolean> options) throws EntityNotFoundException;
	Map<Option, Boolean> saveOptions(Map<Option, Boolean> options) throws EntityNotFoundException;
}
