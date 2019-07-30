package io.github.profilr.web.resources;

import java.util.Map;

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
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("delete-section")
public class PageDeleteSection extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageDeleteSection(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{sectionID}")
	@Template(name="/deletegeneric")
	public Response getDelete(@PathParam("sectionID") int sectionID) throws UserNotAuthorizedException {
		Section s = entityManager.find(Section.class, sectionID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(s.getCourse()))
			throw new UserNotAuthorizedException();
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"type", "Section",
				"name", s.getName(),
				"message", "This will remove all "+s.getUsers().size()+" students enrolled in this section.",
				"strong", true,
				"deleteUrl", urlMappings.get("deleteSectionUrl")+"/"+s.getSectionID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+s.getCourse().getCourseID()+"#sectionsTab"
		)).build();
	}
	
	@POST
	@Path("{sectionID}")
	public Response delete(@PathParam("sectionID") int section) throws UserNotAuthorizedException {
		Section s = entityManager.find(Section.class, section);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(s.getCourse()))
			throw new UserNotAuthorizedException();
		
		entityManager.remove(s);
		
		return Response.ok().build();
	}
	
}