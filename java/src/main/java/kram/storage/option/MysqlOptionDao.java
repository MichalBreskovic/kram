package kram.storage.option;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.EntityNotFoundException;

public class MysqlOptionDao implements OptionDao {

	JdbcTemplate jdbcTemplate;

	public MysqlOptionDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class OptionRowMapper implements RowMapper<Option>{
		public Option mapRow(ResultSet rs, int rowNum) throws SQLException {
			long option_id = rs.getLong("option_id");
			String title = rs.getString("title");
			return new Option(option_id, title);
		}
	}
	
	@Override
	public Option getById(Long id) throws EntityNotFoundException {
		String sql = "SELECT option_id, title FROM `option` WHERE option_id = " + id;
		try {
			return jdbcTemplate.queryForObject(sql, new OptionRowMapper());
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Option with id " + id + " not found");
		}
	} 
	
	@Override
	public Option saveOption(Option option) throws EntityNotFoundException {
		if (option.getIdOption() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("`option`");
			insert.usingGeneratedKeyColumns("option_id");
			insert.usingColumns("title");
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("title", option.getTitle());
			Option newOption = new Option(insert.executeAndReturnKey(valuesMap).longValue(), option.getTitle());
		return newOption;
		} else {
			String sql = "UPDATE `option` SET title = ? WHERE option_id = ?";
			int now = jdbcTemplate.update(sql, option.getTitle(), option.getIdOption());
			if(now == 1) return option;
			else throw new EntityNotFoundException("Option with id " + option.getIdOption() + " not found");
		}
	}

	@Override
	public Option deleteOption(Long id) throws EntityNotFoundException {
		Option option = getById(id);
		String sql = "DELETE FROM `option` WHERE option_id = " + id;
		jdbcTemplate.update(sql);
		return option;
	}
	
}
