package kram.storage;

import java.util.ArrayList;
import java.util.List;

public class Test {
	private Long idTest;
	private List<Answer> answers = new ArrayList<Answer>();
	private String start;
	private String end;
	private int hodnotenie;
	public Long getIdTest() {
		return idTest;
	}
	public void setIdTest(Long idTest) {
		this.idTest = idTest;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
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
	public Test(Long idTest, List<Answer> answers, String start, String end, int hodnotenie) {
		this.idTest = idTest;
		this.answers = answers;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
	}
	
	public Test(List<Answer> answers, String start, String end, int hodnotenie) {
		this.answers = answers;
		this.start = start;
		this.end = end;
		this.hodnotenie = hodnotenie;
	}
	@Override
	public String toString() {
		return "Test [idTest=" + idTest + ", answers=" + answers + ", start=" + start + ", end=" + end + ", hodnotenie="
				+ hodnotenie + "]";
	}
	
	
	
	
}
