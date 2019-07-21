package io.github.profilr.web.resources;

import java.util.ArrayList;

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
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("create-course")
public class PageCreateCourse extends WebResource {

	@Inject
	EntityManager entityManager;
	
	public PageCreateCourse(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/createcourse")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("courseName") String name) {
		
		Course c = new Course();
		c.setName(name);
		c.setAdmins(new ArrayList<User>());
		c.getAdmins().add((User) session.get("user"));
		
		entityManager.persist(c);
		
		return Response.noContent().build();
	}
	
}