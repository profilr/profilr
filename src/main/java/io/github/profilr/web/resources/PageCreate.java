package io.github.profilr.web.resources;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.QuestionType;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;
import io.github.profilr.web.exceptions.UserNotAuthorizedException;

@Path("create")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class PageCreate extends WebResource {
	
	@Inject
	EntityManager entityManager;

	public PageCreate(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("course")
	@Produces(MediaType.TEXT_HTML)
	@Template(name="/createcourse")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
	@POST
	@Path("course")
	public Response create(@FormParam("courseName") String name) {
		
		if (!session.getUser().canCreateCourse())
			throw new UserNotAuthorizedException();
		
		Course c = new Course();
		c.setName(name);
		c.setAdmins(Arrays.asList(session.getUser()));
		
		ExceptionUtils.checkLength(c);
		
		entityManager.persist(c);

		QuestionType mc = new QuestionType(),
					 fr = new QuestionType();
		mc.setName("Multiple Choice");
		mc.setCourse(c);
		entityManager.persist(mc);
		fr.setName("Free Response");
		fr.setCourse(c);
		entityManager.persist(fr);
		
		return Response.noContent().build();
	}
	
	@POST
	@Path("question-type")
	public Response createQuestionType(@FormParam("questionTypeName") String name, @FormParam("courseId") int course) {
		Course c = entityManager.find(Course.class, course);
		ExceptionUtils.check(c, session);
		
		QuestionType qt = new QuestionType();
		qt.setName(name);
		qt.setCourse(c);

		ExceptionUtils.checkLength(qt);
		
		entityManager.persist(qt);
		
		return Response.noContent().build();
	}
	
	@POST
	@Path("topic")
	public Response createTopic(@FormParam("topicName") String name, @FormParam("courseId") int course) {
		Course c = entityManager.find(Course.class, course);
		
		ExceptionUtils.check(c, session);
		
		Topic t = new Topic();
		t.setName(name);
		t.setCourse(c);
		
		ExceptionUtils.checkLength(t);
		
		entityManager.persist(t);
		
		return Response.noContent().build();
	}
	
	@POST
	@Path("test")
	public Response createTest(@FormParam("testName") String name, @FormParam("courseId") int course) {
		Course c = entityManager.find(Course.class, course);
		
		ExceptionUtils.check(c, session);
		
		Test t = new Test();
		t.setName(name);
		t.setCourse(c);
		t.setQuestions(new TreeSet<Question>());
		
		ExceptionUtils.checkLength(t);
		
		entityManager.persist(t);
		
		return Response.noContent().build();
	}

	@POST
	@Path("section")
	public Response createSection(@FormParam("sectionName") String name, @FormParam("courseId") int course) {
		Course c = entityManager.find(Course.class, course);

		ExceptionUtils.check(c, session);

		Section s = new Section();
		s.setCourse(c);
		s.setName(name);
		
		ExceptionUtils.checkLength(s);

		String joinCode;

		do {
			joinCode = getRandomCode();
		} while (!isValidCode(joinCode));
		
		s.setJoinCode(joinCode);

		entityManager.persist(s);

		return Response.noContent().build();
	}

	private static final String CHARSET = "ABCDEF1234567890";
	private static final int CHAR_COUNT = CHARSET.length();
	private static final int LENGTH = 6;
	private static final long RANDOM_COUNT = Math.round(Math.pow(CHAR_COUNT, LENGTH));
	
	private static final Random random = new Random();
	
	private static String getRandomCode() {
		long r = nextLong(random, 0, RANDOM_COUNT);
		char[] code = new char[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			code[i] = CHARSET.charAt((int) (r % CHAR_COUNT));
			r /= CHAR_COUNT;
		}
		return new String(code); 
	}
	
	// Taken from JavaDocs of Random#longs(long, long)
	private static long nextLong(Random random, long origin, long bound) {
		long r = random.nextLong();
		long n = bound - origin, m = n - 1;
		if ((n & m) == 0L) // power of two
			r = (r & m) + origin;
		else if (n > 0L) { // reject over-represented candidates
			for (long u = r >>> 1; // ensure nonnegative
					u + m - (r = u % n) < 0L; // rejection check
					u = random.nextLong() >>> 1) // retry
				;
			r += origin;
		} else { // range not representable as long
			while (r < origin || r >= bound)
				r = random.nextLong();
		}
		return r;
	}

	private boolean isValidCode(String joinCode) {
		return entityManager.createNamedQuery(Section.SELECT_VIA_JOIN_CODE_NQ, Section.class)
							.setParameter("joinCode", joinCode)
							.getResultList()
							.size() == 0;
	}

}