package kram.storage.subject;

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

public class MysqlSubjectDao implements SubjectDao {

	JdbcTemplate jdbcTemplate;

	public MysqlSubjectDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class SubjectRowMapper implements RowMapper<Subject> {
		public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
			long idSubject = rs.getLong("subject_id");
			String title = rs.getString("title");
			String acronym = rs.getString("short");
			return new Subject(idSubject, title, acronym);
		};

	}

	@Override
	public List<Subject> getAll()  throws EntityNotFoundException {
		String sql = "SELECT subject_id, title, short FROM subject";
		try {
			return jdbcTemplate.query(sql, new SubjectRowMapper());
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User not found");
		}
	}
	
	@Override
	public Subject getById(Long id) throws EntityNotFoundException {
		String sql = "SELECT subject_id, title, short FROM subject WHERE subject_id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new SubjectRowMapper(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User not found");
		}
	}

	@Override
	public List<Subject> getAllForTeacher(long idUser) throws EntityNotFoundException{
		String sql = "SELECT s.subject_id, s.title, s.short FROM subject s JOIN (SELECT t.subject_id FROM topic t JOIN question q ON (t.topic_id=q.topic_id) WHERE q.user_id LIKE ? GROUP BY t.topic_id) z WHERE z.subject_id LIKE s.subject_id";
		try {
			return jdbcTemplate.query(sql, new SubjectRowMapper(), idUser);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User with id " + idUser + " not found");
		}
	}

	@Override
	public List<Subject> getBySubstring(String sub) throws EntityNotFoundException {
		if (sub == null) {
			return getAll();
		}
		if (sub.isBlank()) {
			return getAll();
		}
		String str = "%" + sub + "%";
		String sql = "SELECT subject_id, title, short FROM subject WHERE title LIKE ? or short LIKE ?";
		try {
			return jdbcTemplate.query(sql, new SubjectRowMapper(), str, str);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Subject with substring " + sub + " not found");
		}
	}
	
	@Override
	public Subject saveSubject(Subject subject) throws EntityNotFoundException {
		if (subject.getIdSubject() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("subject");
			insert.usingGeneratedKeyColumns("subject_id");
			insert.usingColumns("title","short");
			
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("title", subject.getTitle());
			valuesMap.put("short", subject.getAcronym());
			Subject newSubject = new Subject(insert.executeAndReturnKey(valuesMap).longValue(), subject.getTitle(), subject.getAcronym());
			return newSubject;
		} else {
			String sql = "UPDATE subject SET title = ?, short = ? WHERE subject_id = 1;";
			int now = jdbcTemplate.update(sql, subject.getTitle(), subject.getAcronym(), subject.getIdSubject());
			if (now != 1) return subject;
			else throw new EntityNotFoundException("Subject with id " + subject.getIdSubject() + " not found");
		}
	}
	
	@Override
	public Subject deleteSubject(long id) throws EntityNotFoundException {
		String deleteSql = "DELETE FROM subject WHERE subject_id = " + id;
		Subject subject = getById(id);
		int changed = jdbcTemplate.update(deleteSql);
		if(changed == 0) {
			throw new EntityNotFoundException("Question with id " + id + " not found");
		}
		return subject;
	}

}
