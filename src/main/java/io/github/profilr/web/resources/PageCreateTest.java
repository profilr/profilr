package io.github.profilr.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Test;
import io.github.profilr.web.PreAuth;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("createtest")
@PreAuth
public class PageCreateTest extends WebResource {
	
	public PageCreateTest(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/createtest")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Test test, @PathParam("courseid") int course_id) {
		System.out.println(test);
		// TODO Need to read the test
		return Response.ok(getView()).build();
	}
	
}
