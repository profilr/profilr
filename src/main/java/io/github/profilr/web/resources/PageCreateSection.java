package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Section;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("create-section")
public class PageCreateSection extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageCreateSection(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("sectionName") String name, @FormParam("courseId") int course) {
		System.out.println("Create section: " + name + " on course " + course);
		
		Course c = entityManager.find(Course.class, course);
		
		Section s = new Section();
		s.setCourse(c);
		s.setName(name);
		
		entityManager.persist(s);
		
		return Response.ok().build();
	}
	
}