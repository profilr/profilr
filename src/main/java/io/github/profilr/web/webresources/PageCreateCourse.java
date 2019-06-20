package io.github.profilr.web.webresources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.web.Session;

@Path("createcourse")
public class PageCreateCourse extends WebResource {
	
	public PageCreateCourse(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	public PageCreateCourse(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		super(request, uriInfo);
	}
	
	@GET
	@Template(name="/createcourse")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
	@POST
	public Response create() {
		// TODO Need to read the course name from the post data and create a course...
		return Response.ok(getView()).build();
	}
	
}
