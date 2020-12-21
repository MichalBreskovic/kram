package kram.storage.zameranie;

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
	public Zameranie getById(long idTopic) throws NullPointerException{
		return jdbcTemplate.queryForObject(
				"select t.topic_id, t.title, t.subject_id from topic t where t.topic_id = ?",
				new ZameranieRowMapper(), idTopic);
	}
	
	@Override
	public List<Zameranie> getAllForTeacherBySubjectId(long idUser, long idSubject) throws NullPointerException{
		return jdbcTemplate.query(
				" select t.topic_id, t.title, t.subject_id from topic t join question q on (t.topic_id=q.topic_id) where q.user_id like ? and t.subject_id like ? group by t.topic_id",
				new ZameranieRowMapper(), idUser, idSubject);
	}

	@Override
	public List<Zameranie> getAllBySubjectId(long subjectId) throws EntityNotFoundException, NullPointerException {

		return jdbcTemplate.query("SELECT topic_id, title, subject_id FROM topic where subject_id = ?", new ZameranieRowMapper(),
				subjectId);

	}

	@Override
	public List<Zameranie> getAll() throws EntityNotFoundException {

		return jdbcTemplate.query("SELECT topic_id, title, subject_id FROM topic", new ZameranieRowMapper());

	}
	
//	@Override
//	public List<Zameranie> getAllByTestUserId(long idTest, long idUser) throws EntityNotFoundException {
//		String sql = "SELECT t.topic_id, t.title FROM topic AS t JOIN test USING(topic_id) WHERE test_id = ? AND user_id = ? ORDER BY t.title";
//		try {
//			return jdbcTemplate.query(sql, new ZameranieRowMapper(), idTest, idUser);
//		} catch (DataAccessException e) {
//			throw new EntityNotFoundException("User not found");
//		}
//	}
	
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
	
	@Override
	public List<Zameranie> getBySubstringSubjectId(String sub, long subjectId) throws NullPointerException {
		if (sub == null) {
			return getAll();
		}
		if (sub.isBlank()) {
			return getAll();
		}
		String str = "%" + sub +"%";
		String sql = "SELECT topic_id , title, subject_id FROM topic WHERE title LIKE ? and subject_id like ?";
		return jdbcTemplate.query(sql, new ZameranieRowMapper(),str, subjectId);
	}
	
	@Override
	public Zameranie deleteZameranie(long id) throws EntityNotFoundException {
		String deleteSql = "DELETE FROM topic WHERE topic_id = " + id;
		Zameranie topic = getById(id);
		int changed = jdbcTemplate.update(deleteSql);
		if(changed == 0) {
			throw new EntityNotFoundException("Topic with id " + id + " not found");
		}
		return topic;
	}
}
