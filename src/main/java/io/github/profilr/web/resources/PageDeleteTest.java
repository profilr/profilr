package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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

import io.github.profilr.domain.Test;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("delete-test")
public class PageDeleteTest extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageDeleteTest(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{testId}")
	@Template(name="/deletetest")
	public Response getDelete(@PathParam("testId") int testId) {
		Test t = entityManager.find(Test.class, testId);
		
		return Response.ok(getView("testId", t.getTestID(), "testName", t.getName(), "courseId", t.getCourse().getCourseID())).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response delete(@FormParam("testId") int test) {
		Test t = entityManager.find(Test.class, test);
		
		entityManager.remove(t);
		
		return Response.ok().build();
	}
	
}