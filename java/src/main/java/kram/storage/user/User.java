package kram.storage.user;

import java.util.ArrayList;
import java.util.List;

import kram.storage.Question;
import kram.storage.test.Test;

public class User {
	private Long idUser;
	private String name;
	private String username;
	private String surname;
	private String heslo;
	private boolean teacher;
	private List<Test> testy = new ArrayList<Test>();
	private List<Question> questions = new ArrayList<Question>();
	
	
	
	public User() {
	}

	//constructor all parameters
	public User(Long idUser, String name, String surname, String heslo, boolean teacher) {
		this.idUser = idUser;
		this.name = name;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;
	}
	
	//constructor without id
	public User(String name, String surname, String heslo, boolean teacher) {
		this.name = name;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;
	}
	
	
	
	//constructor withou teacher, idk, maybe delete later
	public User(String name, String surname, String heslo) {
		this.name = name;
		this.surname = surname;
		this.heslo = heslo;
	}
	



	

	public User( String name, String username, String surname, String heslo, boolean teacher) {
		this.name = name;
		this.username = username;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;

	}
	public User(Long idUser, String name, String username, String surname, String heslo, boolean teacher) {
		this.idUser = idUser;
		this.name = name;
		this.username = username;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getHeslo() {
		return heslo;
	}
	public void setHeslo(String heslo) {
		this.heslo = heslo;
	}
	public boolean isTeacher() {
		return teacher;
	}
	public void setTeacher(boolean teacher) {
		this.teacher = teacher;
	}
	
	public List<Test> getTesty() {
		return testy;
	}

	public void setTesty(List<Test> testy) {
		this.testy = testy;
	}
	

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", surname=" + surname + ", heslo=" + heslo + ", teacher="
				+ teacher + ", testy=" + testy + ", questions=" + questions + "]";
	}

	
	
	
}
