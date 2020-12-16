package kram.storage.test;

import java.util.HashMap;
import java.util.Map;

import kram.storage.option.Option;
import kram.storage.question.Question;

public class KramTest {
	private Long idTest;
	private Long idUser;
	private Long idTopic;
	private String start;
	private String end;
	private Integer hodnotenie;
	
	private Map<Question,Option> answers = new HashMap<Question,Option>();
	
	public KramTest(Long idUser, Long idTopic, String start, String end, int hodnotenie) {
		this.start = start;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.end = end;
		this.hodnotenie = hodnotenie;
	}
	
	public KramTest(Long idTest, Long idUser, Long idTopic, String start, String end, int hodnotenie) {
		this.idTest = idTest;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
	}
	
	public KramTest(Long idUser, Long idTopic, String start, String end, int hodnotenie, Map<Question,Option> answers) {
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
		this.answers = answers;
	}
	
	public KramTest(Long idTest, Long idUser, Long idTopic, String start, String end, int hodnotenie, Map<Question,Option> answers) {
		this.idTest = idTest;
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
	
	public Integer getHodnotenie() {
		return hodnotenie;
	}

	public void setHodnotenie(Integer hodnotenie) {
		this.hodnotenie = hodnotenie;
	}
	
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(Long idTopic) {
		this.idTopic = idTopic;
	}

	@Override
	public String toString() {
		return "KramTest [idTest=" + idTest + ", answers=" + answers + ", start=" + start + ", end=" + end + ", hodnotenie="
				+ hodnotenie + "]";
	}
	
	
	
	
}