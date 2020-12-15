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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.EntityNotFoundException;
import kram.storage.option.Option;

public class MysqlQuestionDao implements QuestionDao{
	
	JdbcTemplate jdbcTemplate;
	
	public MysqlQuestionDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	private class QuestionSetExtractor implements ResultSetExtractor<List<Question>>{
		@Override
		public List<Question> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<Question> questions = new ArrayList<Question>();
			Question question = null;
			while(rs.next()) {
				Long id = rs.getLong("question_id");
				if(question == null || question.getIdQuestion() != id) {
					String questionTitle = rs.getString("question_title");
					Long 
				}
			}
			return questions;
		}
	}
	
	@Override
	public List<Question> getAll() {
		String sql = "SELECT q.question_id, q.title AS question_title, q.topic_id, o.option_id, o.title AS option_title, qo.correct FROM question AS q LEFT OUTER JOIN question_option AS qo USING(question_id) LEFT OUTER JOIN `option` AS o USING(option_id) ORDER BY q.question_id";
		return null;
	}
	
//	@Override
//	public Options getOptions(Long id) {
//		String sql = "SELECT question_id, title, topic_id FROM question WHERE question_id = " + id;
//		try {
//			return jdbcTemplate.queryForObject(sql, new QuestionRowMapper());
//			
//		} catch (DataAccessException e) {
//			throw new EntityNotFoundException("Question not found");
//		}
//	}
	
	@Override
	public Question getQuestion(Long id) {
		String sql = "SELECT question_id, title, topic_id FROM question WHERE question_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new QuestionRowMapper());
			
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Question not found");
		}
	}
	
	@Override
	public Question saveQuestion(Question question) throws EntityNotFoundException, NullPointerException {
		
		if (question.getIdQuestion() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("question");
			insert.usingGeneratedKeyColumns("question_id");
			insert.usingColumns("title","topic_id");
			
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("title", question.getTitle());
			valuesMap.put("topic_id", question.getIdTopic().toString());
			Question newQuestion = new Question(insert.executeAndReturnKey(valuesMap).longValue(), question.getTitle(), question.getIdTopic());
		return newQuestion;
		} else {
			String sql = "UPDATE question SET title = ?, topic_id = ? WHERE question_id = ?";
			int now = jdbcTemplate.update(sql, question.getTitle(), question.getIdTopic(), question.getIdQuestion());
			if ( now == 1) return question;
			else {
				throw new EntityNotFoundException("Question with id " + question.getIdQuestion() + " not found");
			}
		}
	}
	
	@Override
	public Question deleteQuestion(Long id) throws EntityNotFoundException {
		Question question = getQuestion(id);
		String sql = "DELETE FROM question WHERE question_id = " + id;
		jdbcTemplate.update(sql);
		return question;
	}
	
}
