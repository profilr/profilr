package io.github.profilr.web;

import io.github.profilr.domain.Answer;
import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.QuestionType;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.TestResponse;
import io.github.profilr.domain.Topic;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringCleanseExtensions {

	public static String cleanse(String s) {
		return s.replaceAll("[‘’]", "'")
				.replaceAll("[“”]", "\"")
				.replaceAll("[^\\n\\p{Print}]", "");
	}
	
	public static void cleanse(Answer a) {
		a.setNotes(cleanse(a.getNotes()));
	}
	
	public static void cleanse(Course c) {
		c.setName(cleanse(c.getName()));
	}
	
	public static void cleanse(Question q) {
		q.setLabel(cleanse(q.getLabel()));
	}
	
	public static void cleanse(QuestionType q) {
		q.setName(cleanse(q.getName()));
	}
	
	public static void cleanse(Section s) {
		s.setName(cleanse(s.getName()));
	}
	
	public static void cleanse(Test t) {
		t.setName(cleanse(t.getName()));
	}
	
	public static void cleanse(TestResponse r) {
		r.setText(cleanse(r.getText()));
	}
	
	public static void cleanse(Topic t) {
		t.setName(cleanse(t.getName()));
	}
	
}
