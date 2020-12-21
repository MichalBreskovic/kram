package kram.storage.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.EntityNotFoundException;
import kram.storage.SHA256;

public class MysqlUserDao implements UserDao {
	
	JdbcTemplate jdbcTemplate;
	
	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	private class UserRowMapper implements RowMapper<User>{
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			long user_id = rs.getLong("user_id");
			String email = rs.getString("email");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String heslo = rs.getString("password");
			String username = rs.getString("username");
			boolean teacher = rs.getBoolean("teacher");
			return new User(user_id, name, username, surname,  heslo, teacher, email);
		}
	}
	
	private class MiniUserRowMapper implements RowMapper<User>{
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			long user_id = rs.getLong("user_id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			return new User(user_id, name, surname);
		}
	}
	
	@Override
    public List<User> getAll() throws EntityNotFoundException {
        String sql = "SELECT user_id, name, surname, password, teacher, username, email FROM user";
        try {
            return jdbcTemplate.query(sql, new UserRowMapper());
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("User not found");
        }
    }

	@Override
	public List<User> getAllAcceptedInCourse(Long idCourse) throws EntityNotFoundException {
		String sql = "SELECT u.user_id, u.name, u.surname FROM course AS c JOIN course_user AS cu USING(course_id) JOIN user AS u ON(cu.user_id = u.user_id) WHERE c.course_id = ? and accepted = 1";
		try {
			return jdbcTemplate.query(sql, new MiniUserRowMapper(), idCourse);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User not found");
		}
	}
	
	@Override
	public List<User> getAllWaitingInCourse(Long idCourse) throws EntityNotFoundException {
		String sql = "SELECT u.user_id, u.name, u.surname FROM course AS c JOIN course_user AS cu USING(course_id) JOIN user AS u ON(cu.user_id = u.user_id) WHERE c.course_id = ? and accepted = 0";
		try {
			return jdbcTemplate.query(sql, new MiniUserRowMapper(), idCourse);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User not found");
		}
	}
	
	@Override
	public User getByUsername(String username) throws EntityNotFoundException {
		String sql = "SELECT user_id, name, surname, password, teacher, username, email FROM `user` WHERE username LIKE ?";
		try {
			return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User not found");
		}
	}
	
	@Override
	public User getById(Long id) throws EntityNotFoundException {
		String sql = "SELECT user_id, name, surname, password, teacher, username, email FROM `user` WHERE user_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new UserRowMapper());
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User not found");
		}
	}
	
	@Override
	public boolean checkUsername(String username) throws EntityNotFoundException {
		String sql = "SELECT username FROM user WHERE username LIKE ?";
		try {
			return jdbcTemplate.queryForObject(sql, new RowMapper<Boolean>() {
	
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					String username = rs.getString("username");
					if(username != null) return false;
					return true;
				}
			
			}, username); 
		} catch(EmptyResultDataAccessException e) {
			return true;
		}
	}
	
	@Override
	public User login(String meno, String password) throws EntityNotFoundException {
		String sql = "SELECT user_id, name, surname, password, teacher, username, email FROM user WHERE username LIKE ?";
		password = SHA256.getHashWithouSalt(password);
		try {
			User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), meno);
			String userPassword = user.getHeslo();
			String passFront = (String) userPassword.subSequence(0, 20);
			String passEnd = (String) userPassword.subSequence(56, userPassword.length());
			userPassword = passFront + passEnd;
			if(password.equals(userPassword)) return user;
			else {
				throw new EntityNotFoundException("Wrong password for " + meno);
			}
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User '" + meno + "' not found");
		}
	}
	
	@Override
	public User saveUser(User user) throws EntityNotFoundException, NullPointerException {
		if (user.getIdUser() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("user");
			insert.usingGeneratedKeyColumns("user_id");
			insert.usingColumns("name","surname","password","teacher", "username", "email");
			
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("email", user.getEmail());
			valuesMap.put("name", user.getName());
			valuesMap.put("surname", user.getSurname());
			valuesMap.put("password", user.getHeslo());
			valuesMap.put("teacher", Boolean.toString(user.isTeacher()));
			valuesMap.put("username", user.getUsername());
			//(Long idUser, String name, String username, String surname, String password, boolean teacher)
			User newUser = new User(insert.executeAndReturnKey(valuesMap).longValue(), user.getName(), user.getUsername(), user.getSurname(), user.getHeslo(), user.isTeacher(), user.getEmail());
			return newUser;
		} else {
			String sql = "UPDATE user SET name = ?, username = ?, surname=?, password=?, teacher=?, email=?  WHERE user_id like ?";
			int now = jdbcTemplate.update(sql,user.getName(), user.getUsername(), user.getSurname(), user.getHeslo(), user.isTeacher(), user.getEmail(), user.getIdUser());
			if (now == 1) return user;
			else throw new EntityNotFoundException("User with id " + user.getIdUser() + " not found");
		}
	}

	@Override
	public void deleteUser(Long id) throws EntityNotFoundException {
		String deleteSql = "DELETE FROM user WHERE user_id = " + id;
//		User user = getById(id);
		int changed = jdbcTemplate.update(deleteSql);
		if(changed == 0) throw new EntityNotFoundException("User with id " + id + " not found");
//		return user;
	}

}
