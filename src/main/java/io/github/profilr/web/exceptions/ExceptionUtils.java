package io.github.profilr.web.exceptions;

import io.github.profilr.domain.Answer;
import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.QuestionType;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.TestResponse;
import io.github.profilr.domain.Topic;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {
	
	// Session methods duplicated with this beautiful regex:
	//
	// Find: /\*\*([^*]*)\*/\s*public static void check\((\w*) (\w*), User u\) (throws UserNotAuthorizedException )?\{[^}]*\}
	// Replace: \0\n\n\t/**\1 (from session) */\n\tpublic static void check(\2 \3, Session session) \4{\n\t\tcheckAuthorized(\3, session.getUser());\n\t}


	/** Check if answer is a users */
	public static void check(Answer a, User u) {
		checkNull(a);
		if (a.getUser().getUserID() != u.getUserID())
			throw new UserNotAuthorizedException();
	}

	/** Check if answer is a users  (from session) - generated by regex */
	public static void check(Answer a, Session session) {
		check(a, session.getUser());
	}

	/** Check if user is a course admin */
	public static void check(Course c, User u) {
		checkNull(c);
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();
	}

	/** Check if user is a course admin  (from session) - generated by regex */
	public static void check(Course c, Session session) {
		check(c, session.getUser());
	}

	/** Check if user is the admin of owner course of question */
	public static void check(Question q, User u) {
		checkNull(q);
		if (!u.isCourseAdmin(q.getTest().getCourse()))
			throw new UserNotAuthorizedException();
	}

	/** Check if user is the admin of owner course of question  (from session) - generated by regex */
	public static void check(Question q, Session session) {
		check(q, session.getUser());
	}

	/** Check if user is the admin of owner course of section */
	public static void check(Section s, User u) {
		checkNull(s);
		if (!u.isCourseAdmin(s.getCourse()))
			throw new UserNotAuthorizedException();
	}

	/** Check if user is the admin of owner course of section  (from session) - generated by regex */
	public static void check(Section s, Session session) {
		check(s, session.getUser());
	}

	/** Check if user is the admin of owner course of test */
	public static void checkToEdit(Test t, User u) {
		checkNull(t);
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
	}

	/** Check if user is the admin of owner course of test  (from session) - generated by regex */
	public static void checkToEdit(Test t, Session session) {
		checkToEdit(t, session.getUser());
	}

	/** Check if user is the admin of owner course of QuestionType (from session) - generated by jacob **/
	public static void check(QuestionType qt, Session session) {
		check(qt, session.getUser());
	}
	
	/** Check if user is the admin of owner course of QuestionType **/
	public static void check(QuestionType qt, User u) {
		check(qt.getCourse(), u);
	}
	
	/** Check if user is enrolled in owner course of test AND test is published */
	public static void checkToRespond(Test t, User u) {
		checkNull(t);
		if (!u.enrolledInCourse(t.getCourse()) || !t.isPublished())
			throw new UserNotAuthorizedException();
	}
	
	/** Check if user is enrolled in owner course of test AND test is published  (from session) - generated by regex */
	public static void checkToRespond(Test t, Session session) {
		checkToRespond(t, session.getUser());
	}

	/** Check if user is the admin of owner course of topic */
	public static void check(Topic t, User u) {
		checkNull(t);
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
	}

	/** Check if user is the admin of owner course of topic  (from session) - generated by regex */
	public static void check(Topic t, Session session) {
		check(t, session.getUser());
	}
	
	public static void checkNull(Object o) {
		if (o == null)
			throw new ResourceNotFoundException();
	}
	
	private static final int RESPONSE_TEXT_MAX_LENGTH = 1000;
	public static void checkLength(TestResponse r) {
		if (r.getText().length() > RESPONSE_TEXT_MAX_LENGTH)
			throw new CharacterLimitExceededException(RESPONSE_TEXT_MAX_LENGTH);
	}
	
	private static final int ANSWER_NOTES_MAX_LENGTH = 500;
	public static void checkLength(Answer a) {
		if (a.getNotes().length() > ANSWER_NOTES_MAX_LENGTH)
			throw new CharacterLimitExceededException(ANSWER_NOTES_MAX_LENGTH);
	}
	
	private static final int COURSE_NAME_MAX_LENGTH = 50; 
	public static void checkLength(Course c) {
		if (c.getName().length() > COURSE_NAME_MAX_LENGTH)
			throw new CharacterLimitExceededException(COURSE_NAME_MAX_LENGTH);
	}
	
	private static final int QUESTION_LABEL_MAX_LENGTH = 30;
	public static void checkLength(Question q) {
		if (q.getLabel().length() > QUESTION_LABEL_MAX_LENGTH)
			throw new CharacterLimitExceededException(QUESTION_LABEL_MAX_LENGTH);
	}
	
	private static final int TOPIC_NAME_MAX_LENGTH = 50;
	public static void checkLength(Topic t) {
		if (t.getName().length() > TOPIC_NAME_MAX_LENGTH)
			throw new CharacterLimitExceededException(TOPIC_NAME_MAX_LENGTH);
	}
	
	private static final int TEST_NAME_MAX_LENGTH = 100;
	public static void checkLength(Test t) {
		if (t.getName().length() > TEST_NAME_MAX_LENGTH)
			throw new CharacterLimitExceededException(TEST_NAME_MAX_LENGTH);
	}
	
	private static final int SECTION_NAME_MAX_LENGTH = 50;
	public static void checkLength(Section s) {
		if (s.getName().length() > SECTION_NAME_MAX_LENGTH)
			throw new CharacterLimitExceededException(SECTION_NAME_MAX_LENGTH);
	}
	
	private static final int QUESTION_TYPE_NAME_MAX_LENGTH = 50;
	public static void checkLength(QuestionType t) {
		if (t.getName().length() > QUESTION_TYPE_NAME_MAX_LENGTH)
			throw new CharacterLimitExceededException(QUESTION_TYPE_NAME_MAX_LENGTH);
	}
}
