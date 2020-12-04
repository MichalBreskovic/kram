package kram.storage;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private Long idGusetion;
	private String title;
	private List<Option> options = new ArrayList<Option>();
	public Question(Long idGusetion, String title, List<Option> options) {
		this.idGusetion = idGusetion;
		this.title = title;
		this.options = options;
	}
	public Question(String title, List<Option> options) {
		this.title = title;
		this.options = options;
	}
	public Long getIdGusetion() {
		return idGusetion;
	}
	public void setIdGusetion(Long idGusetion) {
		this.idGusetion = idGusetion;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	@Override
	public String toString() {
		return "Question [idGusetion=" + idGusetion + ", title=" + title + ", options=" + options + "]";
	}
	
	
}
