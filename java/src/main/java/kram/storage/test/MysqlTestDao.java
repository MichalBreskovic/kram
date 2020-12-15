package kram.storage.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import kram.storage.EntityNotFoundException;
import kram.storage.option.Option;
import kram.storage.question.Question;

public class MysqlTestDao implements TestDao {
	
	
	private class MultipleQuestionSetExtractor implements ResultSetExtractor<List<Test>>{
		@SuppressWarnings("unused")
		@Override
		public List<Test> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<Test> tests = new ArrayList<Test>();
			Test test = null;
			while(rs.next()) {
				Long idTest = rs.getLong("test_id");
				if(test == null || test.getIdTest() != idTest) {
					long idUser = rs.getLong("user_id");
					long idTopic = rs.getLong("topic_id");
					String timeStart = rs.getString("time_start");
					String timeEnd = rs.getString("time_end");
					int hodnotenie = rs.getInt("hodnotenie");
					test = new Test(idTest, idUser, idTopic, timeStart, timeEnd, hodnotenie);
					tests.add(test);
				}
				Long idOption = rs.getLong("option_id");
				if(idOption == null) {
					continue;
				}
				String title = rs.getString("option_id");
				Boolean correct = rs.getBoolean("correct");
//				test.addAnswer(idOption, title, correct);
			}
			return tests;
		}
	}
	

	private class TestRowMapper implements RowMapper<Test>{
		@Override
		public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
			long test_id = rs.getLong("test_id");
			long user_id = rs.getLong("user_id");
			long topic_id = rs.getLong("topic_id");
			String time_start = rs.getString("time_start");
			String time_end = rs.getString("time_end");
			int hodnotenie = rs.getInt("hodnotenie");
			return new Test(test_id, user_id, topic_id, time_start, time_end, hodnotenie);
		}
	}
	
	
	@Override
	public Test getAll() throws EntityNotFoundException {
//		SELECT t.test_id, t.user_id, t.topic_id, t.time_start, t.time_end, a.question_id, a.option_id FROM test t JOIN answer AS a USING(test_id);
		return null;
	}

	@Override
	public Test getById(Long id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Test saveTest(Test test) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test deleteUser(Long id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
