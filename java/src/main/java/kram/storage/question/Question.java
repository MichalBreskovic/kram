package kram.storage.question;

import java.util.HashMap;
import java.util.Map;

import kram.storage.option.Option;

public class Question {
	
	private Long idQuestion;
	private Long idTopic;
	private Long idUser;
	
	private String title;
	private Map<Option,Boolean> options = new HashMap<Option,Boolean>();
	
	public Question(String title, Long idTopic, Long idUser) {
		this.title = title;
		this.idTopic = idTopic;
		this.idUser = idUser;
	}
	
	public Question(String title, Long idTopic, Long idUser, Map<Option,Boolean> options) {
		this.title = title;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.options = options;
	}
	
	public Question(Long idQusetion, String title, Long idTopic, Long idUser) {
		this.idQuestion = idQusetion;
		this.title = title;
		this.idTopic = idTopic;
		this.idUser = idUser;
	}
	
	public Question(Long idQusetion, String title, Long idTopic, Long idUser, Map<Option,Boolean> options) {
		this.idQuestion = idQusetion;
		this.title = title;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.options = options;
	}
	
	@Override
	public int hashCode()
	{
	    return idQuestion.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
<<<<<<< HEAD
		if(o != null) return idQuestion.hashCode() == o.hashCode();
	    return false;
=======
		if (o != null) {
			return idQuestion.hashCode() == o.hashCode();	
		}else {
			return false;
		}
	    
>>>>>>> branch 'master' of https://github.com/MichalBreskovic/kram.git
	}
	
	public void addOption(Option option, boolean correct) {
		this.options.put(option, correct);
	}
	
	public void addOption(Long idOption, String title, boolean correct) {
		this.options.put(new Option(idOption, title), correct);
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

	public void deleteLastOption(Long id) {
		Option o = null;
		for (Map.Entry<Option, Boolean> entry : options.entrySet()) {
			o = entry.getKey();
	    } 
		options.remove(o);
	}
	
	public Option getOption(Long id) {
		for (Map.Entry<Option, Boolean> entry : options.entrySet()) {
			if(id == entry.getKey().getIdOption()) return entry.getKey();
	    } 
		return null;
	}
	
	public void setOptions(Map<Option, Boolean> options) {
		this.options = options;
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
