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
import io.github.profilr.domain.Test;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

import static io.github.profilr.domain.Reason.REASONS;

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
	@Template(name="/editresponse") // TODO
	@Produces(MediaType.TEXT_HTML)
	public Response get() throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, testID);
		
		User u = (User) session.get("user");
		
		if (!u.enrolledInCourse(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		return Response.ok(getView("test", t, "reasons", REASONS)).build();
	}
	
	@GET
	@Path("get-response-data")
	public List<Answer> getResponseData() {
		User u = (User) session.get("user");
		Test t = entityManager.find(Test.class, testID);
		
		List<Answer> l = u.getResponsesForTest(t, entityManager);
		
		System.out.println(l);
		
		return l;
	}
	
	@POST
	@Path("create-response")
	public Response createResponse(Answer a) throws UserNotAuthorizedException {
		User u = (User)session.get("user");
		
		Test test = entityManager.find(Test.class, testID);
		
		if (!u.enrolledInCourse(test.getCourse()))
			throw new UserNotAuthorizedException();
		
		a.setUser(u);
		
		entityManager.persist(a);
		
		return Response.ok().build();
	}
	
	@POST
	@Path("edit-response/")
	public Response editResponse(Answer a) throws UserNotAuthorizedException {
		User u = (User)session.get("user");
		
		Test test = entityManager.find(Test.class, testID);
		
		if (!u.enrolledInCourse(test.getCourse()))
			throw new UserNotAuthorizedException();
		
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
	public Response editResponses(List<Answer> l) throws UserNotAuthorizedException {
		User u = (User)session.get("user");
		
		Test test = entityManager.find(Test.class, testID);
		
		if (!u.enrolledInCourse(test.getCourse()))
			throw new UserNotAuthorizedException();
		
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
	public Response deleteResponse(@PathParam("response-id") int responseID) throws UserNotAuthorizedException {
		User u = (User)session.get("user");
		
		Test test = entityManager.find(Test.class, testID);
		
		if (!u.getEnrolledCourses().contains(test.getCourse()))
			throw new UserNotAuthorizedException();
		
		Answer a = entityManager.find(Answer.class, responseID);
		
		entityManager.remove(a);
		
		return Response.ok().build();
	}
	
}
