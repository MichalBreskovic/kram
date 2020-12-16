package kram.storage.zameranie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.EntityNotFoundException;
import kram.storage.subject.Subject;


public class MysqlZameranieDao implements ZameranieDao {
	JdbcTemplate jdbcTemplate;

	private class ZameranieRowMapper implements RowMapper<Zameranie> {
		public Zameranie mapRow(ResultSet rs, int rowNum) throws SQLException {
			long idZameranie = rs.getLong("topic_id");
			long idSubject = rs.getLong("subject_id");
			String title = rs.getString("title");
			return new Zameranie(idZameranie,idSubject, title);
		};

	}

	public MysqlZameranieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Zameranie> getAllForTeacher(long idUser) throws NullPointerException{
		return jdbcTemplate.query(
				"select t.topic_id, t.title, t.subject_id from topic t join question q on (t.topic_id=q.topic_id) where q.user_id like ? group by t.topic_id",
				new ZameranieRowMapper(), idUser);
	}

	@Override
	public List<Zameranie> getAllBySubjectId(long subjectId) throws EntityNotFoundException {

		return jdbcTemplate.query("SELECT topic_id, title, subject_id FROM topic where subject_id = ?", new ZameranieRowMapper(),
				subjectId);

	}
	@Override
	public List<Zameranie> getAll() throws EntityNotFoundException {

		return jdbcTemplate.query("SELECT topic_id, title, subject_id FROM topic", new ZameranieRowMapper());

	}
	@Override
	public Zameranie saveZameranie(Zameranie zameranie) throws EntityNotFoundException, NullPointerException {
		if (zameranie.getIdZameranie() == null) {
			
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("topic");
			insert.usingGeneratedKeyColumns("topic_id");
			insert.usingColumns("subject_id","title");
			
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("subject_id", zameranie.getIdSubject().toString());
			valuesMap.put("title", zameranie.getTitle());
		
			//(Long idUser, String name, String username, String surname, String password, boolean teacher)
			Zameranie newZameranie = new Zameranie(insert.executeAndReturnKey(valuesMap).longValue(), zameranie.getIdSubject(), zameranie.getTitle());
			return newZameranie;
		} else {
			return null;
		}
			/*String sql = "UPDATE user SET name = ?, username = ?, surname=?, password=?, teacher=?  WHERE user_id like ?";
			int now = jdbcTemplate.update(sql,user.getName(), user.getUsername(), user.getSurname(), user.getHeslo(), user.isTeacher(), user.getIdUser());
			if (now==1) {
				return user;
			}else {
				throw new EntityNotFoundException("User with id " + user.getIdUser() + " not found");
			}
			*/

		}
	@Override
	public List<Zameranie> getBySubstring(String sub) throws NullPointerException {
		if (sub == null) {
			return getAll();
		}
		if (sub.isBlank()) {
			return getAll();
		}
		String str = "%" + sub +"%";
		String sql = "SELECT topic_id , title, subject_id FROM topic WHERE title LIKE ?";
		return jdbcTemplate.query(sql, new ZameranieRowMapper(),str);
	}
}
