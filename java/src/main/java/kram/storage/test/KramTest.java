package kram.storage.test;

import java.util.List;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.question.Question;
import kram.storage.zameranie.ZameranieDao;

public class KramTest {
	private Long idTest;
	private Long idUser;
	private Long idTopic;
	private String start;
	private String end;
	private Integer hodnotenie;

	private MultiValuedMap<Question,Option> answers = new ArrayListValuedHashMap<Question,Option>();
	
	
	public KramTest(Long idUser, Long idTopic, String start) {
		this.start = start;
		this.idTopic = idTopic;
		this.idUser = idUser;
	}
	public KramTest(Long idTest, Long idTopic) {
		this.idTest=idTest;
		this.idUser = idUser;
	}
	
	public KramTest(Long idUser) {
		this.idUser = idUser;

	}

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
	
	public KramTest(Long idUser, Long idTopic, String start, String end, int hodnotenie, MultiValuedMap<Question,Option> answers) {
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
		this.answers = answers;
	}
	
	public KramTest(Long idTest, Long idUser, Long idTopic, String start, String end, int hodnotenie, MultiValuedMap<Question,Option> answers) {
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
	
	public MultiValuedMap<Question,Option> setQuestions(List<Question> questions) {
		for (Question question : questions) {
			this.answers.put(question, null);
		}
		return answers;
	}
	
	public MultiValuedMap<Question,Option> getAnswers() {
		return answers;
	}
	
	public void setAnswers(MultiValuedMap<Question,Option> answers) {
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
		if(idTopic == 0) {
			return "Points from test :" + hodnotenie+ "%" +" starttime "+ start.substring(5) + " end: " + end.substring(5) ;
		} else {
			return DaoFactory.INSTATNCE.getZameranieDao().getById(idTopic).toString().toUpperCase()+" you got " + hodnotenie+ "%"  ;
		}
	}
	
	
	
	
	
}
