package kram.storage.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserFxModel {
	private long user_id;
	
	StringProperty email = new SimpleStringProperty();
	StringProperty username = new SimpleStringProperty();
	StringProperty surname = new SimpleStringProperty();
	StringProperty name = new SimpleStringProperty();
	StringProperty heslo = new SimpleStringProperty();
	StringProperty heslo2 = new SimpleStringProperty();
	private boolean teacher;
	
	

	public UserFxModel() {

	}
	public UserFxModel(StringProperty username, StringProperty surname, StringProperty name, StringProperty heslo,
			StringProperty heslo2, boolean teacher, StringProperty email) {
		this.username = username;
		this.surname = surname;
		this.name = name;
		this.heslo = heslo;
		this.heslo2 = heslo2;
		this.teacher = teacher;
		this.email = email;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username.get();
	}
	public StringProperty getUsernameProperty() {
		return username;
	}
	public void setUsername(StringProperty username) {
		this.username = username;
	}
	public String getSurname() {
		return surname.get();
	}
	public StringProperty getSurnameProperty() {
		return surname;
	}
	public void setSurname(StringProperty surname) {
		this.surname = surname;
	}
	public String getHeslo2() {
		return heslo2.get();
	}
	public StringProperty getHeslo2Property() {
		return heslo2;
	}
	public void setHeslo2(StringProperty heslo2) {
		this.heslo2 = heslo2;
	}
	public boolean isTeacher() {
		return teacher;
	}
	public void setTeacher(boolean teacher) {
		this.teacher = teacher;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	public void setHeslo(StringProperty heslo) {
		this.heslo = heslo;
	}
	
	public String getName() {
		return name.get();
	}
	public StringProperty getNameProperty() {
		return name;
	}
	public String getHeslo() {
		return heslo.get();
	}
	
	public StringProperty getHesloProperty() {
		return heslo;
	}
	
	public StringProperty getEmailProperty() {
		return email;
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public void setEmail(StringProperty email) {
		this.email = email;
	}

}
