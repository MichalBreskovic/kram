package kram.storage.subject;

import java.util.ArrayList;
import java.util.List;

import kram.storage.zameranie.Zameranie;

//import prezencka.storage.Student;

public class Subject {
	private Long idSubject;
	private String title;
	private String acronym;
	private List<Zameranie> zamerania = new ArrayList<Zameranie>(); 
	//constructor all parameters
	public Subject(Long idSubject, String title, String acronym, List<Zameranie> zamerania) {
		this.idSubject = idSubject;
		this.title = title;
		this.acronym = acronym;
		this.zamerania = zamerania;
	}
	public Subject(Long idSubject, String title, String acronym) {
		this.idSubject = idSubject;
		this.title = title;
		this.acronym = acronym;
	}
	
	//constructor without ID
	public Subject(String title, String acronym, List<Zameranie> zamerania) {
		this.title = title;
		this.acronym = acronym;
		this.zamerania = zamerania;
	}
	
	//constructor only title for new subject
	public Subject(String title) {
		this.title = title;
	}
	
	//constructor only title and acronym for new subject
	public Subject(String title, String acronym) {

		this.title = title;
		this.acronym = acronym;
	}





	public Long getIdSubject() {
		return idSubject;
		}
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	@Override
	public String toString() {
		return  acronym +" : "+ title;
	}
	

}
