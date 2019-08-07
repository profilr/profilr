package io.github.profilr.web.resources;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Answer;
import io.github.profilr.domain.Reason;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;

@Path("tests/{test-id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PageEditResponse extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	@PathParam("test-id")
	int testID;
	
	public PageEditResponse(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	@GET
	@Template(name="/editresponse")
	@Produces(MediaType.TEXT_HTML)
	public Response get() {
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToRespond(t, session);
		
		List<Reason> reasons = entityManager.createNamedQuery(Reason.SELECT_ALL_NQ, Reason.class).getResultList();
		
		return Response.ok(getView("test", t, "reasons", reasons)).build();
	}
	
	@GET
	@Path("get-response-data")
	public List<Answer> getResponseData() {
		User u = session.getUser();
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToRespond(t, u);
		
		List<Answer> l = u.getResponsesForTest(t, entityManager);
		
		return l;
	}
	
	@POST
	@Path("create-response")
	public Response createResponse(Answer a) {
		User u = session.getUser();
		
		Test test = entityManager.find(Test.class, testID);

		ExceptionUtils.checkToRespond(test, u);
		
		a.setUser(u);
		
		entityManager.persist(a);
		
		return Response.ok().build();
	}
	
	@POST
	@Path("edit-response")
	public Response editResponse(Answer a) {
		User u = session.getUser();
		
		Test test = entityManager.find(Test.class, testID);

		ExceptionUtils.checkToRespond(test, u);
		
		a.setUser(u);
		
		List<Answer> old = u.getResponsesForQuestion(a.getQuestion(), entityManager);
		if (old.size() > 0) {
			a.setAnswerID(old.get(0).getAnswerID());
			entityManager.merge(a);
		} else {
			entityManager.persist(a);
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("edit-responses/")
	public Response editResponses(List<Answer> l) {
		User u = session.getUser();
		
		Test test = entityManager.find(Test.class, testID);

		ExceptionUtils.checkToRespond(test, u);
		
		for (Answer a : l) {
			a.setUser(u);
			
			List<Answer> old = u.getResponsesForQuestion(a.getQuestion(), entityManager);
			if (old.size() > 0) {
				a.setAnswerID(old.get(0).getAnswerID());
				entityManager.merge(a);
			} else {
				entityManager.persist(a);
			}
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("delete-response/{response-id}")
	public Response deleteResponse(@PathParam("response-id") int responseID) {
		Test test = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToRespond(test, session);
		
		Answer a = entityManager.find(Answer.class, responseID);
		
		entityManager.remove(a);
		
		return Response.ok().build();
	}
	
}
