package kram.storage.question;

import java.util.List;

import kram.storage.EntityNotFoundException;
import kram.storage.option.Option;

public interface QuestionDao {

	Question getQuestion(Long id);
	Question saveQuestion(Question question) throws EntityNotFoundException,NullPointerException;
	Question deleteQuestion(Long id) throws EntityNotFoundException;
	List<Question> getAllByUserId(Long id);
}
