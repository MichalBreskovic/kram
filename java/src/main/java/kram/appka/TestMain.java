package kram.appka;

import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import kram.storage.question.Question;

public class TestMain {
	
	public static void main(String[] args) {
		MultiValuedMap<Question,String> answers = new ArrayListValuedHashMap<Question,String>();
		answers.put(new Question(1L, "Ahoj", 1L, 1L), "Value1");
		answers.put(new Question(1L, "Hello", 1L, 1L), "Value2");
		
		for(Map.Entry<Question,String> a : answers.entries()) {
			System.out.println(a.getValue());
		}
	}

}
