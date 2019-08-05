package io.github.profilr.web.resources;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("invite")
public class PageInvite extends WebResource {

	@Inject
	EntityManager entityManager;
	
	public PageInvite(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response invite(@FormParam("email") String email, @FormParam("courseID") int courseID) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseID);
		
		if (!((User) session.get("user")).isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		org.hibernate.Session session = entityManager.unwrap(org.hibernate.Session.class);
		
		@SuppressWarnings("deprecation")
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("emailAddress", email));
		
		@SuppressWarnings("unchecked")
		List<User> result = crit.list();
		
		if (result.size() == 0)
			return Response.status(Status.NOT_FOUND).build();
		
		User u = result.get(0);
		
		if (u.getAdministratedCourses().contains(c) || u.getEnrolledCourses().contains(c))
			return Response.status(Status.BAD_REQUEST).build();
		
		c.getAdmins().add(u);
		
		return Response.noContent().build();
	}
	
}
