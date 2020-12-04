package kram.storage;

import java.util.ArrayList;
import java.util.List;

public class Answer {
	private Long idAnswer;
	private Long idoption;
	private Long idQuestion;
	public Answer(Long idAnswer, Long idoption, Long idQuestion) {
		this.idAnswer = idAnswer;
		this.idoption = idoption;
		this.idQuestion = idQuestion;
	}
	
	public Answer( Long idoption, Long idQuestion) {
		this.idoption = idoption;
		this.idQuestion = idQuestion;
	}

	public Long getIdAnswer() {
		return idAnswer;
	}

	public void setIdAnswer(Long idAnswer) {
		this.idAnswer = idAnswer;
	}

	public Long getIdoption() {
		return idoption;
	}

	public void setIdoption(Long idoption) {
		this.idoption = idoption;
	}

	public Long getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(Long idQuestion) {
		this.idQuestion = idQuestion;
	}

	@Override
	public String toString() {
		return "Answer [idAnswer=" + idAnswer + ", idoption=" + idoption + ", idQuestion=" + idQuestion + "]";
	}
	
	
	

}
