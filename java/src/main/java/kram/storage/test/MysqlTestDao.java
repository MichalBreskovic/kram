package kram.storage.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.DaoFactory;
import kram.storage.EntityNotFoundException;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;

public class MysqlTestDao implements TestDao {

	JdbcTemplate jdbcTemplate;

	public MysqlTestDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class TestSetExtractor implements ResultSetExtractor<KramTest> {
		@SuppressWarnings("unused")
		@Override
		public KramTest extractData(ResultSet rs) throws SQLException, DataAccessException {
			KramTest kramTest = null;
			while (rs.next()) {
				Long idTest = rs.getLong("test_id");
				if (kramTest == null || kramTest.getIdTest() != idTest) {
					long idUser = rs.getLong("user_id");
					long idTopic = rs.getLong("topic_id");
					String timeStart = rs.getString("time_start");
					String timeEnd = rs.getString("time_end");
					int hodnotenie = rs.getInt("hodnotenie");
					kramTest = new KramTest(idTest, idUser, idTopic, timeStart, timeEnd, hodnotenie);
				}
				OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();
				QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
				Long idQuestion = rs.getLong("option_id");
				Long idOption = rs.getLong("option_id");
				kramTest.addAnswer(questionDao.getById(idQuestion), optionDao.getById(idOption));
			}
			return kramTest;
		}
	}

	private class MultipleTestSetExtractor implements ResultSetExtractor<List<KramTest>> {
		@SuppressWarnings("unused")
		@Override
		public List<KramTest> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<KramTest> kramTests = new ArrayList<KramTest>();
			KramTest kramTest = null;
			while (rs.next()) {
				Long idTest = rs.getLong("test_id");
				if (kramTest == null || kramTest.getIdTest() != idTest) {
					long idUser = rs.getLong("user_id");
					long idTopic = rs.getLong("topic_id");
					String timeStart = rs.getString("time_start");
					String timeEnd = rs.getString("time_end");
					int hodnotenie = rs.getInt("hodnotenie");
					kramTest = new KramTest(idTest, idUser, idTopic, timeStart, timeEnd, hodnotenie);
					kramTests.add(kramTest);
				}
				OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();
				QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
				Long idQuestion = rs.getLong("option_id");
				Long idOption = rs.getLong("option_id");
				kramTest.addAnswer(questionDao.getById(idQuestion), optionDao.getById(idOption));
			}
			return kramTests;
		}
	}
	
	private class InfoTestSetExtractor implements ResultSetExtractor<List<KramTest>> {
		@SuppressWarnings("unused")
		@Override
		public List<KramTest> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<KramTest> kramTests = new ArrayList<KramTest>();
			KramTest kramTest = null;
			while (rs.next()) {
				Long idTest = rs.getLong("test_id");
				long idUser = rs.getLong("user_id");
				long idTopic = rs.getLong("topic_id");
				String timeStart = rs.getString("time_start");
				String timeEnd = rs.getString("time_end");
				int hodnotenie = rs.getInt("hodnotenie");
				kramTest = new KramTest(idTest, idUser, idTopic, timeStart, timeEnd, hodnotenie);
				kramTests.add(kramTest);
			}
			return kramTests;
		}
	}

	@Override
	public List<KramTest> getAll() throws EntityNotFoundException {
		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id)";
		try {
			return jdbcTemplate.query(sql, new MultipleTestSetExtractor());
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test not found");
		}
	}
	
	@Override
	public List<KramTest> getAllInfo() throws EntityNotFoundException {
		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie FROM test AS t JOIN answer AS a USING(test_id)";
		try {
			return jdbcTemplate.query(sql, new InfoTestSetExtractor());
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test not found");
		}
	}

	@Override
	public KramTest getById(Long id) throws EntityNotFoundException {
		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id) WHERE t.test_id = ?";
		try {
			return jdbcTemplate.query(sql, new TestSetExtractor(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test with " + id + " not found");
		}
	}

	@Override
	public KramTest getByUserId(Long id) throws EntityNotFoundException {
		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id) WHERE user_id = ?";
		try {
			return jdbcTemplate.query(sql, new TestSetExtractor(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test with " + id + " not found");
		}
	}


	
	@Override
	public KramTest getBySubjectId(Long id) throws EntityNotFoundException {
		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id) JOIN topic USING(topic_id) JOIN subject USING(subject_id) WHERE subject_id = ?";
		try {
			return jdbcTemplate.query(sql, new TestSetExtractor(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test with " + id + " not found");
		}
	}
	
	
	@Override
    public KramTest getByTopicId(Long id) throws EntityNotFoundException {
        String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id) WHERE topic_id = ?";
        try {
            return jdbcTemplate.query(sql, new TestSetExtractor(), id);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Test with " + id + " not found");
        }
    }
	
	@Override
	public KramTest saveTest(KramTest kramTest) throws EntityNotFoundException {
		if (kramTest.getIdTest() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("test");
			insert.usingGeneratedKeyColumns("test_id");
			insert.usingColumns("user_id", "topic_id", "time_start", "time_end", "hodnotenie");

			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("user_id", kramTest.getIdUser().toString());
			valuesMap.put("topic_id", kramTest.getIdTopic().toString());
			valuesMap.put("time_start", kramTest.getStart());
			valuesMap.put("time_end", kramTest.getEnd());
			valuesMap.put("hodnotenie", kramTest.getHodnotenie().toString());
			KramTest newTest = new KramTest(insert.executeAndReturnKey(valuesMap).longValue(), kramTest.getIdUser(),
					kramTest.getIdTopic(), kramTest.getStart(), kramTest.getEnd(), kramTest.getHodnotenie(),
					kramTest.getAnswers());
			System.out.println(newTest.getIdTest());
			if (newTest.getAnswers().size() != 0) {
				String sql = insert(newTest);
				if (sql != "")
					jdbcTemplate.update(sql);
			}
			return newTest;
		} else {
			if (kramTest.getAnswers().size() != 0) {
				String sql = "UPDATE test SET user_id = ?, topic_id = ?, time_start = ?, time_end = ?, hodnotenie = ? WHERE test_id = ?";
				int now = jdbcTemplate.update(sql, kramTest.getIdUser(), kramTest.getIdTopic(), kramTest.getStart(),
						kramTest.getEnd(), kramTest.getHodnotenie());
				if (now != 1)
					throw new EntityNotFoundException("Test with id " + kramTest.getIdTest() + " not found");
				String deleteSql = "DELETE FROM answer WHERE test_id = ?";
				jdbcTemplate.update(deleteSql, kramTest.getIdTest());
				jdbcTemplate.update(insert(kramTest));
			}
			return kramTest;
		}
	}

	private String insert(KramTest kramTest) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO answer (test_id, question_id, option_id) VALUES ");
		boolean stop = true;
		for (Map.Entry<Question, Option> entry : kramTest.getAnswers().entries()) {
			System.out.println(kramTest.getAnswers().get(entry.getKey()));
			if (entry.getValue() != null) {
				stop = false;
				sqlBuilder.append("(" + kramTest.getIdTest() + "," + entry.getKey().getIdQuestion() + ","
						+ entry.getValue().getIdOption() + "),");
			}
		}
		if (stop)
			return "";
		String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
		System.out.println(sql);
		return sql;
	}

	@Override
	public KramTest deleteTest(Long id) throws EntityNotFoundException {
		String deleteSql = "DELETE FROM answer WHERE test_id = ?";
		KramTest kramTest = getById(id);
		jdbcTemplate.update(deleteSql, id);
		String sql = "DELETE FROM test WHERE test_id = ?";
		int changed = jdbcTemplate.update(sql, id);
		if (changed == 0)
			throw new EntityNotFoundException("Test with id " + id + " not found");
		return kramTest;
	}

}
