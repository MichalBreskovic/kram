package kram.storage.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kram.storage.option.Option;
import kram.storage.question.Question;

public class Test {
	private Long idTest;
	private Long idUser;
	private Long idTopic;
	private String start;
	private String end;
	private int hodnotenie;
	
	private Map<Question,Option> answers = new HashMap<Question,Option>();
	
	public Test(Long idUser, Long idTopic, String start, String end, int hodnotenie) {
		this.start = start;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.end = end;
		this.hodnotenie = hodnotenie;
	}
	
	public Test(Long idTest, Long idUser, Long idTopic, String start, String end, int hodnotenie) {
		this.idTest = idTest;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
	}
	
	public Test(Long idUser, Long idTopic, String start, String end, int hodnotenie, Map<Question,Option> answers) {
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
		this.answers = answers;
	}
	
	public Test(Long idTest, Long idUser, Long idTopic, String start, String end, int hodnotenie, Map<Question,Option> answers) {
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
		this.answers = answers;
	}
	
	public void addAnswer(Question question, Option option) {
		this.answers.put(question, option);
	}
	
	public Long getIdTest() {
		return idTest;
	}
	
	public void setIdTest(Long idTest) {
		this.idTest = idTest;
	}
	
	public Map<Question,Option> getAnswers() {
		return answers;
	}
	
	public void setAnswers(Map<Question,Option> answers) {
		this.answers = answers;
	}
	
	public String getStart() {
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	
	public int getHodnotenie() {
		return hodnotenie;
	}

	public void setHodnotenie(int hodnotenie) {
		this.hodnotenie = hodnotenie;
	}

	@Override
	public String toString() {
		return "Test [idTest=" + idTest + ", answers=" + answers + ", start=" + start + ", end=" + end + ", hodnotenie="
				+ hodnotenie + "]";
	}
	
	
	
	
}
