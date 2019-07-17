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

import io.github.profilr.domain.Course;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("deletecourse")
public class PageDeleteCourse extends WebResource {

	@Inject
	EntityManager entityManager;
	
	public PageDeleteCourse(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("/course/{courseId}")
	@Template(name="/deletecourse")
	public Response getDelete(@PathParam("courseId") int courseId) {
		Course c = entityManager.find(Course.class, courseId);
		return Response.ok(getView("courseName", c.getName(), "courseId", c.getCourseID())).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response delete(@FormParam("courseId") int courseId) {
		Course c = entityManager.find(Course.class, courseId);
		
		entityManager.remove(c);
		
		return Response.ok().build();
	}
	
}
