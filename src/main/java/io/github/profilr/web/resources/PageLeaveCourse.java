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
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("leave-course")
public class PageLeaveCourse extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageLeaveCourse(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{courseId}")
	@Template(name="/leavecourse")
	public Response getDelete(@PathParam("courseId") int courseId) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseId);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		return Response.ok(getView("course", c)).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response delete(@FormParam("courseId") int courseId) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseId);
		
		User u = (User) session.get("user");
		
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		entityManager.refresh(u);
		
		c.getAdmins().remove(u);
		
		return Response.ok().build();
	}
	
}