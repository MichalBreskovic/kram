package kram.storage.subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kram.storage.EntityNotFoundException;



public class MysqlSubjectDao implements SubjectDao {
	
	JdbcTemplate jdbcTemplate;
	
	private class SubjectRowMapper implements RowMapper<Subject>{
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
			long idSubject = rs.getLong("predmet_id");
			String title = rs.getString("title");
			String acronym = rs.getString("short");
			return new Subject(idSubject,title,acronym);
		};
		
	}
	
	public MysqlSubjectDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Subject saveSubject(Subject subject) throws EntityNotFoundException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getAll() {
		return jdbcTemplate.query("SELECT predmet_id, title, short FROM predmet", new SubjectRowMapper());
	}

	@Override
	public Subject delete(long id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
