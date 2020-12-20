package kram.storage.test;

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
				Long idQuestion = rs.getLong("question_id");
				Long idOption = rs.getLong("option_id");
				System.out.println("quesion_id : " + idQuestion);
				if(idOption == 0) kramTest.addAnswer(questionDao.getById(idQuestion), null);
				else kramTest.addAnswer(questionDao.getById(idQuestion), optionDao.getById(idOption));
				if (idOption!=0 ) {
					kramTest.addAnswer(questionDao.getById(idQuestion), optionDao.getById(idOption));
				}else {
					kramTest.addAnswer(questionDao.getById(idQuestion), null);
				}

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
				Long idQuestion = rs.getLong("question_id");
				Long idOption = rs.getLong("option_id");
				if(idQuestion != 0 && idOption != 0) {
					kramTest.addAnswer(questionDao.getById(idQuestion), optionDao.getById(idOption));
				} else {
					kramTest.addAnswer(questionDao.getById(idQuestion), null);
				}
			}
			return kramTests;
		}
	}
	
	private class TestRowMapper implements RowMapper<KramTest>{
		public KramTest mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long idTest = rs.getLong("test_id");
			long idUser = rs.getLong("user_id"); 
			long idTopic = rs.getLong("topic_id");
			String timeStart = rs.getString("time_start");
			String timeEnd = rs.getString("time_end");
			int hodnotenie = rs.getInt("hodnotenie");
			return new KramTest(idTest, idUser, idTopic, timeStart, timeEnd, hodnotenie);

		}
	}

	private class TestRowMapper2 implements RowMapper<KramTest>{
		public KramTest mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long idTest = rs.getLong("test_id");
			long idUser = rs.getLong("user_id"); 
			return new KramTest(idTest, idUser, 0L, null, null, 0);

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
	public List<KramTest> getAllInfo(long userId) throws EntityNotFoundException {

		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie FROM test AS t WHERE t.user_id = ? and t.topic_id is not null";

		try {
			return jdbcTemplate.query(sql, new TestRowMapper(), userId);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test not found");
		}
	}
	
	@Override
	public KramTest getById(Long id) throws EntityNotFoundException {
		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t LEFT OUTER JOIN answer AS a USING(test_id) WHERE t.test_id = ?";
		try {
			return jdbcTemplate.query(sql, new TestSetExtractor(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test with " + id + " not found");
		}
	}
	
	@Override
	public List<KramTest> getAllBySubjectId(Long id) throws EntityNotFoundException {
		String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id) JOIN topic USING(topic_id) JOIN subject USING(subject_id) WHERE subject_id = ?";
		try {
			return jdbcTemplate.query(sql, new TestRowMapper(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test with " + id + " not found");
		}
	}

	@Override
	public List<KramTest> getAllBySubjectUserId(Long id, Long userId) throws EntityNotFoundException {
		String sql = " SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie FROM test AS t  JOIN topic as top  USING(topic_id) JOIN subject as s USING(subject_id) WHERE top.subject_id = ? and user_id=?";
		try {
			return jdbcTemplate.query(sql, new TestRowMapper(), id, userId);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Test with " + id + " not found");
		}
	}
	
	@Override
    public List<KramTest> getAllByTopicId(Long id) throws EntityNotFoundException {
        String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id) WHERE topic_id = ?";
        try {
            return jdbcTemplate.query(sql, new TestRowMapper(), id);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Test with " + id + " not found");
        }
    }
	
	@Override
    public List<KramTest> getAllByTopicUserId(Long id, Long userId) throws EntityNotFoundException {
        String sql = " SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie from test as t WHERE topic_id = ? and user_id = ?";
        try {
            return jdbcTemplate.query(sql, new TestRowMapper(), id, userId);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Test with " + id + " not found");
        }
    }
	
	@Override
    public List<KramTest> getAllByCourseId(Long id) throws EntityNotFoundException {
        String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie, a.question_id, a.option_id FROM test AS t JOIN answer AS a USING(test_id) WHERE topic_id = ?";
        try {
            return jdbcTemplate.query(sql, new TestRowMapper(), id);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Test with " + id + " not found");
        }
    }
	
	@Override
    public List<KramTest> getAllByCourseTeacherId(Long id, Long idTeacher) throws EntityNotFoundException {
        String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie FROM test AS t JOIN course_test AS ct USING(test_id) JOIN course AS c USING(course_id) WHERE course_id = ? AND c.user_id = ?";
        try {
            return jdbcTemplate.query(sql, new TestRowMapper(), id, idTeacher);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Test with " + id + " not found");
        }
    }
	
	@Override
    public List<KramTest> getAllByCourseTeacherUserId(Long id, Long idTeacher, Long idUser) throws EntityNotFoundException {
        String sql = "SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, t.hodnotenie FROM test AS t JOIN course_test AS ct USING(test_id) JOIN course AS c USING(course_id) WHERE course_id = ? AND c.user_id = ? AND t.user_id = ?";
        try {
            return jdbcTemplate.query(sql, new TestRowMapper(), id, idTeacher, idUser);
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
			if(kramTest.getIdTopic() == 0) valuesMap.put("topic_id", "NULL");
			else valuesMap.put("topic_id", kramTest.getIdTopic().toString());
			valuesMap.put("time_start", kramTest.getStart());
			valuesMap.put("time_end", kramTest.getEnd());
			valuesMap.put("hodnotenie", kramTest.getHodnotenie().toString());
			KramTest newTest = new KramTest(insert.executeAndReturnKey(valuesMap).longValue(), kramTest.getIdUser(),
					kramTest.getIdTopic(), kramTest.getStart(), kramTest.getEnd(), kramTest.getHodnotenie(),
					kramTest.getAnswers());
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
	
	@Override
	public KramTest saveTest2(KramTest kramTest) throws EntityNotFoundException {
		if (kramTest.getIdTest() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("test");
			insert.usingGeneratedKeyColumns("test_id");
			insert.usingColumns("user_id");

			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("user_id", kramTest.getIdUser().toString());

			KramTest newTest = new KramTest(insert.executeAndReturnKey(valuesMap).longValue(), kramTest.getIdUser());
			if (newTest.getAnswers().size() != 0) {
				String sql = insert(newTest);
				if (sql != "")
					jdbcTemplate.update(sql);
			}
			return newTest;
		} else {
			if (kramTest.getAnswers().size() != 0) {
				String sql = "UPDATE test SET user_id = ? WHERE test_id = ?";
				int now = jdbcTemplate.update(sql, kramTest.getIdUser());
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
		for (Map.Entry<Question, Option> entry : kramTest.getAnswers().entries()) {
			System.out.println(kramTest.getAnswers().get(entry.getKey()));
			if (entry.getValue() != null) {
				sqlBuilder.append("(" + kramTest.getIdTest() + "," + entry.getKey().getIdQuestion() + ","
						+ entry.getValue().getIdOption() + "),");
			} else {
				sqlBuilder.append("(" + kramTest.getIdTest() + "," + entry.getKey().getIdQuestion() + ", NULL),");
			}
		}
		String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
		System.out.println(sql);
		return sql;
	}
	
	@Override
	public KramTest saveTestToCourse(KramTest kramTest, long idCourse) throws EntityNotFoundException {
		if (kramTest.getIdTest() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("test");
			insert.usingGeneratedKeyColumns("test_id");
			insert.usingColumns("user_id", "topic_id", "time_start", "time_end", "hodnotenie");

			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("user_id", kramTest.getIdUser().toString());
			if(kramTest.getIdTopic() == 0) valuesMap.put("topic_id", "NULL");
			else valuesMap.put("topic_id", kramTest.getIdTopic().toString());
			valuesMap.put("time_start", kramTest.getStart());
			valuesMap.put("time_end", kramTest.getEnd());
			valuesMap.put("hodnotenie", kramTest.getHodnotenie().toString());
			KramTest newTest = new KramTest(insert.executeAndReturnKey(valuesMap).longValue(), kramTest.getIdUser(),
					kramTest.getIdTopic(), kramTest.getStart(), kramTest.getEnd(), kramTest.getHodnotenie(),
					kramTest.getAnswers());
			if (newTest.getAnswers().size() != 0) {
				String sql = insert(newTest);
				if (sql != "")
					jdbcTemplate.update(sql);
			}
			if (newTest.getEnd() == null) {
				String sql = "INSERT INTO course_test (course_id, test_id) VALUES (?,?)";
				jdbcTemplate.update(sql, idCourse, kramTest.getIdTest());
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

	@Override
	public KramTest deleteTest(Long id) throws EntityNotFoundException {
		KramTest kramTest = getById(id);
		String deleteAnswerSql = "DELETE FROM answer WHERE test_id = ?";
		jdbcTemplate.update(deleteAnswerSql, id);
		String deleteCourseSql = "DELETE FROM course_test WHERE test_id = ?";
		jdbcTemplate.update(deleteCourseSql, id);
		String sql = "DELETE FROM test WHERE test_id = ?";
		int changed = jdbcTemplate.update(sql, id);
		if (changed == 0)
			throw new EntityNotFoundException("Test with id " + id + " not found");
		return kramTest;
	}

}
