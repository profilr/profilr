package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("courses")
public class PageCourseAdminView extends WebResource {
	
	@Inject
	private EntityManager entityManager;
	
	public PageCourseAdminView(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{course}")
	@Template(name="/courseadminview")
	public Response get(@PathParam("course") int courseId) {
		Course c = entityManager.find(Course.class, courseId);
		return Response.ok(getView("course", c)).build();
	}
	
}
