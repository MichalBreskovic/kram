package kram.storage.question;

import java.util.List;

import kram.storage.EntityNotFoundException;

public interface QuestionDao {

	List<Question> getAll() throws EntityNotFoundException;
	Question saveQuestion(Question question) throws EntityNotFoundException,NullPointerException;
	Question deleteQuestion(Long id) throws EntityNotFoundException;
	List<Question> getAllByUserId(Long id) throws EntityNotFoundException;
	Question getById(Long id) throws EntityNotFoundException;
	List<Question> getByTopicUserId(Long id, Long idUser) throws EntityNotFoundException, NullPointerException;
	List<Question> getBySubjectUserId(Long id, Long idUser) throws EntityNotFoundException, NullPointerException;
	List<Question> generateTestQuestions(int pocet, long idZamerania)
			throws EntityNotFoundException, NullPointerException;
}
