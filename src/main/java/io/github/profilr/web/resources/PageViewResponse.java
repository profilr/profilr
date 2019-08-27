package io.github.profilr.web.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import io.github.profilr.domain.Test;
import io.github.profilr.domain.TestResponse;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;

@Produces(MediaType.TEXT_HTML)
@Path("view-responses/{test-id}/{user-id}")
public class PageViewResponse extends WebResource {

	@Context
	EntityManager entityManager;
	
	@PathParam("test-id")
	int testID;
	
	@PathParam("user-id")
	String userID;
	
	protected PageViewResponse(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	@GET
	@Template(name="/responseview")
	public Response get() {
		User admin = session.getUser();
		User u = entityManager.find(User.class, userID);
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToEdit(t, admin);
		
		List<Answer> answers =  u.getAnswersForTest(t, entityManager);
		Map<String, Answer> answerMap = new HashMap<String, Answer>();
		
		for (Answer a : answers)
			answerMap.put(String.valueOf(a.getQuestion().getQuestionID()), a);
		
		TestResponse response = u.getResponsesForTest(t, entityManager).get(0);
		
		return Response.ok(getView("user", u, "test", t, "answers", answerMap, "response", response)).build();
	}
	
}
