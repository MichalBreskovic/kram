package kram.storage.zameranie;

import java.util.ArrayList;
import java.util.List;

import kram.storage.Question;

public class Zameranie {
	private Long idZameranie;
	private Long idSubject;
	private String title;
	private List<Question> questions = new ArrayList<Question>();
	
	//constructor all parametres
	public Zameranie(Long idZameranie, String title, List<Question> questions) {
		this.idZameranie = idZameranie;
		this.title = title;
		this.questions = questions;
	}
	public Zameranie(Long idZameranie, String title) {
		this.idZameranie = idZameranie;
		this.title = title;
	}
	public Zameranie(Long idZameranie,Long idSubject, String title) {
		this.idZameranie = idZameranie;
		this.idSubject = idSubject;
		this.title = title;
	}
	//construcor for new zameranie no ID
	public Zameranie(String title) {
		this.title = title;
	
	}
	public Long getIdZameranie() {
		return idZameranie;
	}
	public void setIdZameranie(Long idZameranie) {
		this.idZameranie = idZameranie;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	@Override
	public String toString() {
		return title;
	}
	public Long getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	
	
}
