package kram.storage;

import java.util.ArrayList;
import java.util.List;

public class Zameranie {
	private Long idZameranie;
	private String title;
	private List<Question> questions = new ArrayList<Question>();
	
	//constructor all parametres
	public Zameranie(Long idZameranie, String title, List<Question> questions) {
		this.idZameranie = idZameranie;
		this.title = title;
		this.questions = questions;
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
		return "Zameranie [idZameranie=" + idZameranie + ", title=" + title + ", questions=" + questions + "]";
	}
	
}
