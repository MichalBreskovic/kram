package kram.storage.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.question.Question;

public class KramTest {
	private Long idTest;
	private Long idUser;
	private Long idTopic;
	private String name;
	private String start;
	private String end;
	private Integer hodnotenie;

	private MultiValuedMap<Question,Option> answers = new ArrayListValuedHashMap<Question,Option>();
	
	public KramTest(Long idUser) {
		this.idUser = idUser;
	}
	
	public KramTest(Long idTest, Long idUser) {
		this.idTest = idTest;
		this.idUser = idUser;
	}
	
	public KramTest(Long idUser, Long idTopic, String start) {
		this.start = start;
		this.idTopic = idTopic;
		this.idUser = idUser;
	}

	public KramTest(Long idUser, Long idTopic, String start, String end, int hodnotenie) {
		this.start = start;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.end = end;
		this.hodnotenie = hodnotenie;
	}
	
	public KramTest(Long idTest, Long idUser, Long idTopic, String start, String end, int hodnotenie, String name) {
		this.idTest = idTest;
		this.start = start;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.end = end;
		this.hodnotenie = hodnotenie;
		this.name = name;
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
	
	public KramTest(Long idTest, Long idUser, Long idTopic, String start, String end, int hodnotenie, MultiValuedMap<Question,Option> answers, String name) {
		this.idTest = idTest;
		this.idTopic = idTopic;
		this.idUser = idUser;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
		this.answers = answers;
		this.name = name;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
	    Date firstDate = null;
	    Date secondDate = null;
		try {
			firstDate = sdf.parse(start);
		    secondDate = sdf.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.SECONDS);
		
		String output = "";
		if(idTopic != 0) output += "Topic: " + DaoFactory.INSTATNCE.getZameranieDao().getById(idTopic).toString().toUpperCase() + " "; 
		if(name != null) output += "Name: " + name + " ";
		output += "Grade: " + hodnotenie + "% ";
		if(start != null) output += " Start: " + start.substring(5) + " ";
		if(end != null) output += " End: " + end.substring(5) + " ";
		if(diff != 0) output += " Duration: " + diff + " seconds "; 
		return output;
	}
	
	
	
	
	
}
