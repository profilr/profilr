package io.github.profilr.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;
import org.json.JSONObject;

import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("createcourse")
public class PageCreateCourse extends WebResource {
	
	public PageCreateCourse(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/createcourse")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
	@POST
	public Response create(String data) {
		JSONObject json = new JSONObject(data);
		
		
		
		return Response.ok().build();
	}
	
}
