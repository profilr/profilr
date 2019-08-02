package io.github.profilr.web.resources;

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
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("tests/{test-id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PageEditResponse extends WebResource {

	private static final Reason[] reasons = {
											new Reason(1, "Significant Digits"),
											new Reason(2, "No +C"),
											new Reason(3, "Didn't Study"),
											new Reason(4, "Didn't Know"),
											new Reason(5, "oof.")
											};
	
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
		
		//System.out.println(u.getEnrolledCourses());
		//System.out.println(t.getCourse());
		
		//if (!u.getEnrolledCourses().contains(t.getCourse()))
			//throw new UserNotAuthorizedException();
		
		return Response.ok(getView("test", t, "reasons", reasons)).build();
	}
	
	@POST
	@Path("create-response")
	public Response createResponse(Answer a) throws UserNotAuthorizedException {
		User u = (User)session.get("user");
		
		Test test = entityManager.find(Test.class, testID);
		
		if (!u.getEnrolledCourses().contains(test.getCourse()))
			throw new UserNotAuthorizedException();
		
		a.setUser(u);
		
		entityManager.persist(a);
		
		return Response.ok().build();
	}
	
	@POST
	@Path("edit-response/{response-id}")
	public Response editResponse(Answer a) throws UserNotAuthorizedException {
		User u = (User)session.get("user");
		
		Test test = entityManager.find(Test.class, testID);
		
		if (!u.getEnrolledCourses().contains(test.getCourse()))
			throw new UserNotAuthorizedException();
		
		a.setUser(u);
		entityManager.merge(a);
		
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
