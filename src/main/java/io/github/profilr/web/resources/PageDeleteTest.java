package io.github.profilr.web.resources;

import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Test;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("delete-test")
public class PageDeleteTest extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageDeleteTest(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{testID}")
	@Template(name="/deletegeneric")
	public Response getDelete(@PathParam("testID") int testId) throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, testId);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"type", "Test",
				"name", t.getName(),
				"message", "All questions and student responses will be deleted as well",
				"deleteUrl", urlMappings.get("deleteTestUrl")+"/"+t.getTestID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#testsTab"
		)).build();
	}
	
	@POST
	@Path("{testID}")
	public Response delete(@PathParam("testID") int test) throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, test);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		entityManager.remove(t);
		
		return Response.ok().build();
	}
	
}