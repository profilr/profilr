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
import io.github.profilr.domain.Section;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("kick")
public class PageKick extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageKick(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{course-id}/{user-id}")
	@Template(name="/kick")
	public Response getKick(@PathParam("course-id") int courseID, @PathParam("user-id") String userID) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseID);
		
		if (!((User) session.get("user")).isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		User u = entityManager.find(User.class, userID);
		
		if (u.isCourseAdmin(c))
			return Response.ok(getView("course", c, "user", u, "admin", true)).build();
		else
			return Response.ok(getView("course", c, "user", u, "admin", false)).build();
	}
	
	@POST
	@Path("{course-id}/{user-id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response kick(@PathParam("course-id") int courseID, @PathParam("user-id") String userID) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseID);
		
		if (!((User) session.get("user")).isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		User u = entityManager.find(User.class, userID);
		
		if (((User) session.get("user")).getUserID().equals(u.getUserID()))
			return Response.status(400).build();

		if (u.isCourseAdmin(c))
			c.getAdmins().remove(u);
		else {
			Section s = u.getSectionFromCourse(c);
			if (s != null)
				s.getUsers().remove(u);
			else
				return Response.status(404).build();
		}
		
		return Response.ok().build();
	}
	
}