package kram.storage.question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.EntityNotFoundException;



public class MysqlQuestionDao implements QuestionDao{
	
	JdbcTemplate jdbcTemplate;
	
	public MysqlQuestionDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	private class QuestionRowMapper implements RowMapper<Question>{
		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			long question_id = rs.getLong("question_id");
			String title = rs.getString("title");
			long topic_id = rs.getLong("topic_id");
			long user_id = rs.getLong("user_id");
			return new Question(question_id, title, topic_id,user_id);
		}
	}
	
	@Override
	public Question getQuestion(Long id) {
		String sql = "SELECT question_id, title, topic_id, user_id FROM question WHERE question_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new QuestionRowMapper());
			
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Question not found");
		}
	}
	
	@Override
	public List<Question> getAllByUserId(Long id) {
		String sql = "SELECT question_id, title, topic_id, user_id FROM question WHERE user_id = " + id;
		return jdbcTemplate.query(sql, new QuestionRowMapper());


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
