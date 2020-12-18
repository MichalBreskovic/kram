package kram.appka;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import kram.storage.Mail;
import kram.storage.option.Option;
import kram.storage.question.Question;

public class TestMain {
	
	public static void main(String[] args) {
		MultiValuedMap<String,String> answers = new ArrayListValuedHashMap<String,String>();
		answers.put("Key", "Value1");
		answers.put("Key", "Value2");
		
		for(String s : answers.keySet()) {
			System.out.println(s);
		}
	}

}
