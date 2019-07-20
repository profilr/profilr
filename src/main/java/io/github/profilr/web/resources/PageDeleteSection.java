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

import io.github.profilr.domain.Section;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("delete-section")
public class PageDeleteSection extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageDeleteSection(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{sectionId}")
	@Template(name="/deletesection")
	public Response getDelete(@PathParam("sectionId") int sectionId) {
		Section s = entityManager.find(Section.class, sectionId);
		
		return Response.ok(getView("sectionId", s.getSectionID(), "sectionName", s.getName(), "courseId", s.getCourse().getCourseID())).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("sectionId") int section) {
		Section s = entityManager.find(Section.class, section);
		
		entityManager.remove(s);
		
		return Response.ok().build();
	}
	
}