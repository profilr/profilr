package io.github.profilr.web.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Answer;
import io.github.profilr.domain.Course;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.TestResponse;
import io.github.profilr.domain.User;
import io.github.profilr.web.DateFormatterExtensions;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;
import lombok.experimental.ExtensionMethod;

@Path("view-responses/{test-id}")
@Produces(MediaType.TEXT_HTML)
@ExtensionMethod(DateFormatterExtensions.class)
public class PageViewResponses extends WebResource {

	@Inject
	private EntityManager entityManager;
	
	@PathParam("test-id")
	private int testID;
	
	public PageViewResponses(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/testresponseview")
	public Response get() {
		Test t = entityManager.find(Test.class, testID);
		Course c = t.getCourse();
		
		ExceptionUtils.checkToEdit(t, session);
		
		// Also I want a list of the submission times.
		Map<String, String> submissionTimes = new HashMap<String, String>();
		
		List<User> enrolled = c.getEnrolledStudents();
		
		for (User u : enrolled)
			t.getResponsesForUser(u, entityManager)
			 .ifPresent(ts -> submissionTimes.put(String.valueOf(u.getUserID()),
					 							  ts.getTsCreated().formatHuman()));
		
		return Response.ok(getView("course", c, "test", t, "submissionTimes", submissionTimes)).build();
	}

	@GET
	@Path("{user-id}")
	@Template(name="/responseview")
	public Response get(@PathParam("user-id") String userID) {
		User admin = session.getUser();
		User u = entityManager.find(User.class, userID);
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToEdit(t, admin);
		
		List<Answer> answers =  u.getAnswersForTest(t, entityManager);
		Map<String, Answer> answerMap = new HashMap<String, Answer>();
		
		for (Answer a : answers)
			answerMap.put(String.valueOf(a.getQuestion().getQuestionID()), a);
		
		TestResponse response = u.getResponsesForTest(t, entityManager).get();
		
		return Response.ok(getView("user", u, "test", t, "answers", answerMap, "response", response)).build();
	}
	
}
