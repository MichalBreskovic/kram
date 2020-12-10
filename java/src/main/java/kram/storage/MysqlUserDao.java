package kram.storage;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlUserDao implements UserDao {
	
	JdbcTemplate jdbcTemplate;
	
	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
		// TODO Auto-generated constructor stub
	}
	
	private class UserRowMapper implements RowMapper<User>{
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			long user_id = rs.getLong("user_id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String heslo = rs.getString("heslo");
			String username = rs.getString("username");
			boolean teacher = rs.getBoolean("teacher");
			return new User(user_id, name, surname,  heslo, teacher);
		}
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTeacher(User user) {
		
		return false;
	}

	@Override
	public User login(String meno, String heslo) throws EntityNotFoundException {
		String sql = "SELECT user_id, name, surname, heslo, teacher, username FROM user where username like ? and heslo like ?";
		try {
			return jdbcTemplate.queryForObject(sql,
					new UserRowMapper(), meno, heslo);
			
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("User not found");
		}
	}

}
