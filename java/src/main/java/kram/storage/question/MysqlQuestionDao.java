package kram.storage.question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.EntityNotFoundException;
import kram.storage.option.Option;

public class MysqlQuestionDao implements QuestionDao{
	
	JdbcTemplate jdbcTemplate;
	
	public MysqlQuestionDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private class QuestionSetExtractor implements ResultSetExtractor<Question>{
		@SuppressWarnings("unused")
		@Override
		public Question extractData(ResultSet rs) throws SQLException, DataAccessException {
			Question question = null;
			while(rs.next()) {
				Long idQuestion = rs.getLong("question_id");
//				System.out.println("id: " + idQuestion);
				if(question == null) {
					String questionTitle = rs.getString("question_title");
					Long topicId = rs.getLong("topic_id");
					Long userId = rs.getLong("user_id");
					question = new Question(idQuestion, questionTitle, topicId, userId);
				}
				Long idOption = rs.getLong("option_id");
				if(idOption == null) {
					continue;
				}
				String title = rs.getString("option_title");
				Boolean correct = rs.getBoolean("correct");
				question.addOption(idOption, title, correct);
			}
			return question;
		}
	}

	private class MultipleQuestionSetExtractor implements ResultSetExtractor<List<Question>>{
		@SuppressWarnings("unused")
		@Override
		public List<Question> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<Question> questions = new ArrayList<Question>();
			Question question = null;
			while(rs.next()) {
				Long idQuestion = rs.getLong("question_id");
				if(question == null || !question.getIdQuestion().equals(idQuestion)) {
					String questionTitle = rs.getString("question_title");
					Long topicId = rs.getLong("topic_id");
					Long userId = rs.getLong("user_id");
					question = new Question(idQuestion, questionTitle, topicId, userId);
					questions.add(question);
				}
				Long idOption = rs.getLong("option_id");
				if(idOption == null) {
					continue;
				}
				String title = rs.getString("option_title");
				Boolean correct = rs.getBoolean("correct");
				question.addOption(idOption, title, correct);
			}
			return questions;
		}
	}
	
	@Override
	public List<Question> getAll() {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) ORDER BY q.question_id";
		try {
			return jdbcTemplate.query(sql, new MultipleQuestionSetExtractor());
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Question not found");
		}
	}
	
//	@Override
//	public List<Question> getAllById(List<Integer> optionsIds) {
//		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) ORDER BY q.question_id";
//		try {
//			return jdbcTemplate.query(sql, new MultipleQuestionSetExtractor());
//		} catch (DataAccessException e) {
//			throw new EntityNotFoundException("Question not found");
//		}
//	}
	
	@Override
	public List<Question> getAllByUserId(Long id) throws EntityNotFoundException {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) WHERE q.user_id = ? ORDER BY q.question_id";
		try {
			List<Question> questions = jdbcTemplate.query(sql, new MultipleQuestionSetExtractor(), id);
			return questions;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new EntityNotFoundException("Question for user " + id + " not found");
		}
	}
	
	@Override
	public Question getById(Long id) throws EntityNotFoundException {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) WHERE q.question_id = ?";
		try {
			return jdbcTemplate.query(sql, new QuestionSetExtractor(), id);	
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Question with id " + id + " not found");
		}
	}
	
	@Override
	public List<Question> getByTopicUserId(Long id, Long idUser) throws EntityNotFoundException, NullPointerException  {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) WHERE q.user_id = ? and q.topic_id = ? ";
		try {
			return jdbcTemplate.query(sql, new MultipleQuestionSetExtractor(), idUser, id);	
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Questions for topic " + id + " for user " + idUser + " not found");
		}
	}
	
	@Override
	public List<Question> getByTopicId(Long id) throws EntityNotFoundException, NullPointerException  {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) WHERE  q.topic_id = ? ";
		try {
			return jdbcTemplate.query(sql, new MultipleQuestionSetExtractor(),  id);	
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Questions are there not lol");
		}
	}
	
	
	@Override
	public List<Question> generateTestQuestions(int pocet, long idZamerania) throws EntityNotFoundException, NullPointerException  {
		String sql = "  select qe.question_id, qe.title AS question_title, qe.topic_id, qe.user_id, o.option_id, o.title AS option_title, qo.correct from \r\n" + 
				"  (SELECT q.question_id, q.title, q.topic_id, q.user_id from question q where q.topic_id like ? order by rand() LIMIT 0,?) as qe\r\n" + 
				"   LEFT OUTER JOIN question_option AS qo USING(question_id) \r\n" + 
				"   LEFT OUTER JOIN `option` AS o USING(option_id)";
		try {
			return jdbcTemplate.query(sql, new MultipleQuestionSetExtractor(), idZamerania, pocet);	
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("There are zero questions");
		}
	}
	
	@Override
	public List<Question> getBySubjectId(Long id) throws EntityNotFoundException, NullPointerException {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) JOIN (SELECT t.topic_id FROM topic t where t.subject_id = ?) as tab USING(topic_id)";
		try {
			return jdbcTemplate.query(sql, new MultipleQuestionSetExtractor(), id);	
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Haha totot Question with id " + id + " not found");
		}
	}
	
	@Override
	public List<Question> getBySubjectUserId(Long id,Long idUser) throws EntityNotFoundException, NullPointerException {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, q.user_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) JOIN (SELECT t.topic_id FROM topic t where t.subject_id = ?) as tab USING(topic_id) WHERE q.user_id = ? ";
		try {
			return jdbcTemplate.query(sql, new MultipleQuestionSetExtractor(), id, idUser);	
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Haha totot Question with id " + id + " not found");
		}
	}
	
	@Override
	public Question saveQuestion(Question question) throws EntityNotFoundException {
		if (question.getIdQuestion() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("question");
			insert.usingGeneratedKeyColumns("question_id");
			insert.usingColumns("title","topic_id","user_id");
			
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("title", question.getTitle());
			valuesMap.put("topic_id", question.getIdTopic().toString());
			valuesMap.put("user_id", question.getIdUser().toString());
			Question newQuestion = new Question(insert.executeAndReturnKey(valuesMap).longValue(), question.getTitle(), question.getIdTopic(), question.getIdUser(), question.getOptions());
			if(newQuestion.getOptions().size() != 0) {
				jdbcTemplate.update(insert(newQuestion));
			}
			return newQuestion;
		} else {
			String sql = "UPDATE question SET title = ?, topic_id = ? WHERE question_id = ?";
			int now = jdbcTemplate.update(sql, question.getTitle(), question.getIdTopic(), question.getIdQuestion());
			if (now != 1) throw new EntityNotFoundException("Question with id " + question.getIdQuestion() + " not found"); 
			if(question.getOptions().size() != 0) {
				String deleteSql = "DELETE FROM question_option WHERE question_id = ?";
				jdbcTemplate.update(deleteSql, question.getIdQuestion());
				jdbcTemplate.update(insert(question));
			}
			return question;
		}
	}
	
	private String insert(Question question) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO question_option (question_id, option_id, correct) VALUES ");
		for (Map.Entry<Option, Boolean> entry : question.getOptions().entrySet()) {
			sqlBuilder.append("("+ question.getIdQuestion() + "," + entry.getKey().getIdOption() + "," + (entry.getValue() ? 1 : 0) + "),");
	    }
		String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
		return sql;
	}
	
	public Question deleteQuestion(Long id) throws EntityNotFoundException {
		String deleteSql = "DELETE FROM question_option WHERE question_id = " + id;
		Question question = getById(id);
		jdbcTemplate.update(deleteSql);
		String sql = "DELETE FROM question WHERE question_id = " + id;
		int changed = jdbcTemplate.update(sql);
		if(changed == 0) throw new EntityNotFoundException("Question with id " + id + " not found");
		return question;
	}

	
	
}
