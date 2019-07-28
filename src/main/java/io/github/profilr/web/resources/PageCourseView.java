package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("courses")
public class PageCourseView extends WebResource {
	
	@Inject
	private EntityManager entityManager;
	
	public PageCourseView(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{course}")
	@Template(name="/courseadminview")
	public Viewable get(@PathParam("course") int courseId) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseId);
		
		User u = (User)session.get("user");
		
		// If the users is an admin of this course then give them the admin view.
		if (u.isCourseAdmin(c))
			return new Viewable("/courseadminview", getView("course", c));
		
		// If they're not an admin we need to find what section they are in.
		Section s = u.getSectionFromCourse(c);
		
		// If the user doesn't have a section with a matching course, the user is not a part of the course.
		if (s == null)
			throw new UserNotAuthorizedException();
		
		return new Viewable("/coursestudentview", getView("course", c, "section", s));
	}
	
}
