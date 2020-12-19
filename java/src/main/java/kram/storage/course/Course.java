package kram.storage.course;

import java.util.HashSet;
import java.util.Set;

public class Course {
	
	private Long idCourse;
	private Long idUser;
	private String name;
	
	private Set<Long> students = new HashSet<Long>();
	private Set<Long> tests = new HashSet<Long>();
	
	public Course(Long idUser, String name) {
		this.name = name;
		this.idUser = idUser;
	}
	
	public Course(Long idCourse, Long idUser, String name) {
		this.idCourse = idCourse;
		this.idUser = idUser;
		this.name = name;
	}
	
	public void addStudent(Long user) {
		students.add(user);
	}
	
	public void removeStudent(Long user) {
		students.remove(user);
	}
	
	public void addTest(Long test) {
		tests.add(test);
	}
	
	public void removeTest(Long test) {
		tests.remove(test);
	}

	public Long getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(Long idCourse) {
		this.idCourse = idCourse;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Long> getStudents() {
		return students;
	}

	public void setStudents(Set<Long> students) {
		this.students = students;
	}

	public Set<Long> getTests() {
		return tests;
	}

	public void setTests(Set<Long> tests) {
		this.tests = tests;
	}

	@Override
	public String toString() {
		return name;
	}
	
	

}
