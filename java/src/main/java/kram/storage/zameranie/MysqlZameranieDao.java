package kram.storage.zameranie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kram.storage.EntityNotFoundException;

public class MysqlZameranieDao implements ZameranieDao {
	JdbcTemplate jdbcTemplate;
	
	private class ZameranieRowMapper implements RowMapper<Zameranie>{
		public Zameranie mapRow(ResultSet rs, int rowNum) throws SQLException {
			long idZameranie = rs.getLong("topic_id");
//			long idSubject = rs.getLong("subject_id");
			String title = rs.getString("title");
			return new Zameranie(idZameranie,title);
		};
		
	}
	
	public MysqlZameranieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Zameranie> getAllBySubjectId(long subjectId) throws EntityNotFoundException {
		return jdbcTemplate.query("SELECT topic_id, title FROM topic where subject_id = ?", new ZameranieRowMapper(), subjectId);
	
		
	}
}
