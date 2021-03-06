package io.github.profilr.web.resources;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("enroll")
public class PageEnroll extends WebResource {

	@Inject
	EntityManager entityManager;
	
	public PageEnroll(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/enroll")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("joinCode") String joinCode) {
		List<Section> result = entityManager.createNamedQuery(Section.SELECT_VIA_JOIN_CODE_NQ, Section.class)
											.setParameter("joinCode", joinCode)
											.getResultList();
		
		if (result.size() == 0)
			return Response.status(Status.NOT_FOUND).build();
		
		Section s = result.get(0);
		
		Course co = s.getCourse();
		
		User u = session.getUser();
		
		entityManager.refresh(u);
		
		if (u.getAdministratedCourses().contains(co) || u.getEnrolledCourses().contains(co))
			return Response.status(Status.CONFLICT).build();
		
		u.getSectionsJoined().add(s);
		s.getUsers().add(u);
		
		return Response.noContent().build();
	}
	
}