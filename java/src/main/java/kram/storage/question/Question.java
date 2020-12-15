package kram.storage.question;

import java.util.ArrayList;
import java.util.List;

import kram.storage.option.Option;

public class Question {
	
	private Long idQuestion;
	private Long idTopic;
	private Long idUser;
	
	private String title;
	private List<Option> options = new ArrayList<Option>();
	
	public Question(String title, Long idTopic) {
		this.title = title;
		this.idTopic = idTopic;
	}
	
	// komentár
	public Question(Long idQusetion, String title, Long idTopic,Long idUser) {
		this.idQuestion = idQusetion;
		this.title = title;
		this.idTopic = idTopic;
		this.idUser=idUser;
	}
	
	public Question(Long idQusetion, String title, Long idTopic) {
		this.idQuestion = idQusetion;
		this.title = title;
		this.idTopic = idTopic;
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
	
	public List<Option> getOptions() {
		return options;
	}
	
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	public Long getIdTopic() {
		return idTopic;
	}
	
	public void setIdTopic(Long idTopic) {
		this.idTopic = idTopic;
	}
	
	
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	@Override
	public String toString() {
		return title;
	}
	
}
