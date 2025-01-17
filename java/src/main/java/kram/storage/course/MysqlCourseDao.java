package kram.storage.course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kram.storage.EntityNotFoundException;


public class MysqlCourseDao implements CourseDao {

	JdbcTemplate jdbcTemplate;
	
	public MysqlCourseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private class CourseRowMapper implements RowMapper<Course>{
		public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
			long course_id = rs.getLong("course_id");
			long user_id = rs.getLong("user_id");
			String name = rs.getString("name");
			return new Course(course_id, user_id, name);
		}
	}
	private class CourseSetExtractor implements ResultSetExtractor<Course>{
		@SuppressWarnings("unused")
		@Override
		public Course extractData(ResultSet rs) throws SQLException, DataAccessException {
			Course course = null;
			while(rs.next()) {
				Long idCourse = rs.getLong("course_id");
				if(course == null || course.getIdCourse() != idCourse) {
					Long userId = rs.getLong("teacher");
					String name = rs.getString("name");
					course = new Course(idCourse, userId, name);
				}
				Long idStudent = rs.getLong("student");
				if(idStudent != null) course.addStudent(idStudent);
				Long idTest = rs.getLong("test_id");
				if(idTest != null) course.addTest(idTest);
			}
			return course;
		}
	}
	
	private class MultipleCourseSetExtractor implements ResultSetExtractor<List<Course>>{
		@SuppressWarnings("unused")
		@Override
		public List<Course> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<Course> courses = new ArrayList<Course>();
			Course course = null;
			while(rs.next()) {
				Long idCourse = rs.getLong("course_id");
				if(course == null || course.getIdCourse() != idCourse) {
					Long userId = rs.getLong("teacher");
					String name = rs.getString("name");
					course = new Course(idCourse, userId, name);
					courses.add(course);
				}
				Long idStudent = rs.getLong("student");
				if(idStudent != null) course.addStudent(idStudent);
				Long idTest = rs.getLong("test_id");
				if(idTest != null) course.addTest(idTest);
			}
			return courses;
		}
	}
	
	@Override
	public List<Course> getAll() throws EntityNotFoundException {
		String sql = "SELECT c.course_id, c.user_id AS teacher, c.name, cu.user_id AS student, ct.test_id FROM course AS c LEFT OUTER JOIN course_user AS cu USING(course_id) LEFT OUTER JOIN course_test AS ct USING(course_id)";
		try {
			return jdbcTemplate.query(sql, new MultipleCourseSetExtractor());
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Course not found");
		}
	}
	
//	@Override
//	public List<Course> getAllRowMapper() throws EntityNotFoundException {
//		String sql = "select course_id, user_id, name from course ";
//		try {
//			return jdbcTemplate.query(sql, new CourseRowMapper());
//		} catch (DataAccessException e) {
//			throw new EntityNotFoundException("Course not found");
//		}
//	}
	
	@Override
	public List<Course> getAllRowMapperWithoutUser(Long id) throws EntityNotFoundException {
		String sql = "SELECT course_id, user_id, name FROM course WHERE course_id NOT IN (SELECT course_id FROM course AS c LEFT OUTER JOIN course_user AS cu USING(course_id) WHERE cu.user_id = ?)";
		try {
			return jdbcTemplate.query(sql, new CourseRowMapper(),id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Course not found");
		}
	}
	
	
//	@Override
//	public List<Course> getBySubstring(String string) throws EntityNotFoundException {
//		try {
//			
//			String str = "%" + string +"%";
//			String sql = "SELECT course_id , user_id, name FROM course WHERE name LIKE ?";
//			if (string == null || string.trim().isEmpty()) {
//				return getAllRowMapper();
//			} else {
//				return jdbcTemplate.query(sql, new CourseRowMapper(),str);
//			}
//		
//		} catch (DataAccessException e) {
//			throw new EntityNotFoundException("Course not found");
//		}
//	}
	
	@Override
	public List<Course> getBySubstringWithoutU(String string, Long id) throws EntityNotFoundException {
		try {
			
			String str = "%" + string +"%";
			String sql = "SELECT course_id, user_id, name FROM course WHERE course_id NOT IN (SELECT course_id FROM course AS c LEFT OUTER JOIN course_user AS cu USING(course_id) WHERE cu.user_id = ?) AND name LIKE ?";
			if (string == null || string.trim().isEmpty()) {
				return getAllRowMapperWithoutUser(id);
			}else {
				return jdbcTemplate.query(sql, new CourseRowMapper(),id,str);
			}
		
			

		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Course not found");
		}
	
	}

	@Override
	public Course getById(Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		String sql = "SELECT c.course_id, c.user_id AS teacher, c.name, cu.user_id AS student, ct.test_id FROM course AS c LEFT OUTER JOIN course_user AS cu USING(course_id) LEFT OUTER JOIN course_test AS ct USING(course_id) WHERE c.course_id = ?";
		try {
			return jdbcTemplate.query(sql, new CourseSetExtractor(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Course with " + id + " not found");
		}
	}

	@Override
	public List<Course> getAllByTeacherId(Long id) throws EntityNotFoundException {
		String sql = "SELECT c.course_id, c.user_id AS teacher, c.name, cu.user_id AS student, ct.test_id FROM course AS c LEFT OUTER JOIN course_user AS cu USING(course_id) LEFT OUTER JOIN course_test AS ct USING(course_id) WHERE c.user_id = ?";
		try {
			return jdbcTemplate.query(sql, new MultipleCourseSetExtractor(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Course for teacher with id " + id + " not found");
		}
	}
	
	@Override
	public void addToCourse(Long idCourse, Long idUser) throws EntityNotFoundException {
		String sql = "INSERT INTO course_user (course_id, user_id, accepted) VALUES (?,?,0)";

		int changed = jdbcTemplate.update(sql, idCourse, idUser);
		if(changed == 0) throw new EntityNotFoundException("Course with id " + idCourse + " or student with id " + idUser + " not found");
	}
	
	@Override
	public List<Course> getAllByStudentId(Long id) throws EntityNotFoundException {
		String sql = "SELECT c.course_id, c.user_id AS teacher, c.name, cu.user_id AS student, ct.test_id FROM course AS c LEFT OUTER JOIN course_user AS cu USING(course_id) LEFT OUTER JOIN course_test AS ct USING(course_id) WHERE cu.user_id = ?";
		try {
			return jdbcTemplate.query(sql, new MultipleCourseSetExtractor(), id);
		} catch (DataAccessException e) {
			throw new EntityNotFoundException("Course for student with id " + id + " not found");
		}
	}
	
	@Override
	public Course saveCourse(Course course) throws EntityNotFoundException {
		System.out.println("robim");
		if (course.getIdCourse() == null) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
			insert.withTableName("course");
			insert.usingGeneratedKeyColumns("course_id");
			insert.usingColumns("user_id","name");
			
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("user_id", course.getIdUser().toString());
			valuesMap.put("name", course.getName());
			Course newCourse = new Course(insert.executeAndReturnKey(valuesMap).longValue(), course.getIdUser(), course.getName());
			if(newCourse.getStudents().size() != 0) {
				System.out.println("omyl");
				jdbcTemplate.update(insertStudents(newCourse));
			}
			if(newCourse.getTests().size() != 0) {
				System.out.println("omyl2");
				jdbcTemplate.update(insertTests(newCourse));
			}
			return newCourse;
		} else {
			String sql = "UPDATE course SET user_id = ?, name = ? WHERE course_id = ?";
			int now = jdbcTemplate.update(sql, course.getIdUser(), course.getName(), course.getIdCourse());
			if (now != 1) throw new EntityNotFoundException("Course with id " + course.getIdCourse() + " not found"); 
			if(course.getStudents().size() != 0) {
				String deleteSql = "DELETE FROM course_user WHERE course_id = ?";
				jdbcTemplate.update(deleteSql, course.getIdCourse());
				jdbcTemplate.update(insertStudents(course));
			}
			if(course.getTests().size() != 0) {
				String deleteSql = "DELETE FROM course_test WHERE course_id = ?";
				jdbcTemplate.update(deleteSql, course.getIdCourse());
				jdbcTemplate.update(insertTests(course));
			}
			return course;
		}
	}

	private String insertStudents(Course course) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO course_user (course_id, user_id, accepted) VALUES ");
		for (Long id : course.getStudents()) {
			sqlBuilder.append("("+ course.getIdCourse() + "," + id + "," + 1 + "),");
	    }
		String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
		return sql;
	}
	
	private String insertTests(Course course) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO course_test (course_id, test_id) VALUES ");
		for (Long id : course.getTests()) {
			sqlBuilder.append("("+ course.getIdCourse() + "," + id + "),");
	    }
		String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
		return sql;
	}
	
	public String acceptDismissStudent( int bool, Long idStudent, Long idCourse) {
		try {
			if (bool==1) {
				String sql ="Update course_user set accepted = ? where user_id=? and course_id=? ";
				jdbcTemplate.update(sql,bool,idStudent,idCourse );
				return sql;
			}else {
				String sql ="DELETE FROM course_user WHERE user_id=? and course_id=? ";
				jdbcTemplate.update(sql,idStudent,idCourse );
				return sql;
			}
			
			
		} catch (Exception e) {
			throw new EntityNotFoundException("f�uk :'C");
		}
	}
	
	@Override
	public void deleteCourse(Long id) throws EntityNotFoundException {
		String deleteSql = "DELETE FROM course WHERE course_id = ?";
//		Course course = getById(id);
		int changed = jdbcTemplate.update(deleteSql, id);
		if(changed == 0) throw new EntityNotFoundException("Course with id " + id + " not found");
	}

}
