package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Test;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

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
	@Template(name="/edittest")
	@Produces(MediaType.TEXT_HTML)
	public Response get() throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, testID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		return Response.ok(getView()).build();
	}
	
}
