package kram.storage.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kram.storage.option.Option;
import kram.storage.zameranie.Zameranie;

public class Question {
	
	private Long idQuestion;
	private Long idTopic;
	
	private String title;
//	private List<Option> options = new ArrayList<Option>();
	private Map<Option,Boolean> options = new HashMap<Option,Boolean>();
	
	public Question(String title, Long idTopic) {
		this.title = title;
		this.idTopic = idTopic;
	}
	
	// komentár
	
	public Question(Long idQusetion, String title, Long idTopic) {
		this.idQuestion = idQusetion;
		this.title = title;
		this.idTopic = idTopic;
	}
	
	public Question(Long idQusetion, String title, Long idTopic, Map<Option,Boolean> options) {
		this.idQuestion = idQusetion;
		this.title = title;
		this.idTopic = idTopic;
		this.options = options;
	}
	
	public Long getIdQuestion() {
		return idQuestion;
	}
	
	public void setIdQuestion(Long idGusetion) {
		this.idQuestion = idGusetion;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getIdTopic() {
		return idTopic;
	}
	
	public void setIdTopic(Long idTopic) {
		this.idTopic = idTopic;
	}
	
	public Map<Option, Boolean> getOptions() {
		return options;
	}

	public void setOptions(Map<Option, Boolean> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Question [idGusetion=" + idQuestion + ", title=" + title + ", options=" + options + "]";
	}
	
}
