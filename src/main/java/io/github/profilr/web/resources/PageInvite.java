package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

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

		User u;
		try {
			u = entityManager.createNamedQuery(User.SELECT_BY_EMAIL_ADDRESS_NQ, User.class)
							 .setParameter("emailAddress", email)
							 .getSingleResult();
		} catch (NoResultException e) {
			return Response.status(Status.NOT_FOUND).build();
		}

		if (u.getAdministratedCourses().contains(c) || u.getEnrolledCourses().contains(c))
			return Response.status(Status.BAD_REQUEST).build();
		
		c.getAdmins().add(u);
		
		return Response.noContent().build();
	}
	
}
