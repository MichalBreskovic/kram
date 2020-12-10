package kram.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;



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
			return new User(user_id, username, name, surname,  heslo, teacher);
		}
	}


	@Override
	public User saveUser(User user) throws EntityNotFoundException, NullPointerException {
		if (user.getIdUser() == null) {
			
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("user");
			insert.usingGeneratedKeyColumns("user_id");
			insert.usingColumns("name","surname","heslo","teacher", "username");
			
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("name", user.getName());
			valuesMap.put("surname", user.getSurname());
			valuesMap.put("heslo", user.getHeslo());
			valuesMap.put("teacher", Boolean.toString(user.isTeacher()));
			valuesMap.put("username", user.getUsername());
			//(Long idUser, String name, String username, String surname, String heslo, boolean teacher)
			User newPredmet = new User(insert.executeAndReturnKey(valuesMap).longValue(), user.getName(), user.getUsername(), user.getSurname(), user.getHeslo(), user.isTeacher());
			return newPredmet;
		}else {
			String sql = "UPDATE user SET name = ?, username = ?, surname=?, heslo=?, teacher=?,  WHERE user_id = ?";
			int now = jdbcTemplate.update(sql,user.getName(), user.getUsername(), user.getSurname(), user.getHeslo(), user.isTeacher(), user.getIdUser());
			if (now==1) {
				return user;
			}else {
				throw new EntityNotFoundException("User with id "+user.getIdUser()+" not found");
			}
			

		}
		
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



	@Override
	public boolean isTeacher(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
