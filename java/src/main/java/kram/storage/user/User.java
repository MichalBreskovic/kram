package kram.storage.user;

import java.util.ArrayList;
import java.util.List;

import kram.storage.question.Question;
import kram.storage.test.KramTest;

public class User {
	private Long idUser;
	private String email;
	private String name;
	private String username;
	private String surname;
	private String heslo;
	private boolean teacher;
	
	public User() {
	}
	
	
	

	public int hashCode() {
		return idUser.hashCode();
	}



	public boolean equals(Object obj) {
		if(obj != null) return idUser.hashCode() ==  obj.hashCode();
	    return false;
	}



	public User(Long idUser, String name, String surname) {
		this.idUser = idUser;
		this.name = name;
		this.surname = surname;
	}

	public User(Long idUser, String name, String surname, String heslo, boolean teacher) {
		this.idUser = idUser;
		this.name = name;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;
	}
	
	public User(String name, String surname, String heslo, boolean teacher) {
		this.name = name;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;
	}
	
	public User(String name, String surname, String heslo) {
		this.name = name;
		this.surname = surname;
		this.heslo = heslo;
	}
	
	public User(String name, String username, String surname, String heslo, boolean teacher, String email) {
		this.name = name;
		this.username = username;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;
		this.email = email;
		
	}
	public User(Long idUser, String name, String username, String surname, String heslo, boolean teacher, String email) {
		this.idUser = idUser;
		this.name = name;
		this.username = username;
		this.surname = surname;
		this.heslo = heslo;
		this.teacher = teacher;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
	}

}
