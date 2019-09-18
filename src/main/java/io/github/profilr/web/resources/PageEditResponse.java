package io.github.profilr.web.resources;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
import io.github.profilr.domain.TestResponse;
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
	@Path("get-answer-data")
	public List<Answer> getAnswerData() {
		User u = session.getUser();
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToRespond(t, u);
		
		List<Answer> l = u.getAnswersForTest(t, entityManager);
		
		return l;
	}
	
	@GET
	@Path("get-response-data")
	public TestResponse getResponseData() {
		User u = session.getUser();
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToRespond(t, u);
		
		return u.getResponsesForTest(t, entityManager).orElse(null);
	}
	
	@POST
	@Path("update-answer")
	public Response editResponse(Answer a) {
		ExceptionUtils.checkLength(a);

		User u = session.getUser();
		Test test = entityManager.find(Test.class, testID);
		ExceptionUtils.checkToRespond(test, u);
		a.setUser(u);
		
		Optional<Answer> old = u.getAnswersForQuestion(a.getQuestion(), entityManager);
		
		if (old.isPresent()) {
			a.setAnswerID(old.get().getAnswerID());
			entityManager.merge(a);
		} else {
			entityManager.persist(a);
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("update-answers/")
	public Response editResponses(List<Answer> l) {
		User u = session.getUser();
		
		Test test = entityManager.find(Test.class, testID);

		ExceptionUtils.checkToRespond(test, u);
		
		for (Answer a : l) {
			ExceptionUtils.checkLength(a);
			a.setUser(u);
			
			Optional<Answer> old = u.getAnswersForQuestion(a.getQuestion(), entityManager);
			if (old.isPresent()) {
				a.setAnswerID(old.get().getAnswerID());
				entityManager.merge(a);
			} else {
				entityManager.persist(a);
			}
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Path("update-response/")
	public Response updateResponse(TestResponse r) {
		ExceptionUtils.checkLength(r);
		
		User u = session.getUser();
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToRespond(t, u);

		r.setUser(u);
		r.setTest(t);
		
		Instant now = Instant.now();
		Optional<TestResponse> old = u.getResponsesForTest(t, entityManager);

		if (old.isPresent()) {
			r.setResponseID(old.get().getResponseID());
			r.setTsCreated(old.get().getTsCreated());
			r.setTsUpdated(now);
			entityManager.merge(r);
		} else {
			r.setTsCreated(now);
			r.setTsUpdated(now);
			entityManager.persist(r);
		}
		
		return null;
	}
	
	@POST
	@Path("delete-answer/{answer-id}")
	public Response deleteResponse(@PathParam("answer-id") int answerID) {
		Test test = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToRespond(test, session);
		
		Answer a = entityManager.find(Answer.class, answerID);
		
		entityManager.remove(a);
		
		return Response.ok().build();
	}
	
}
