package io.github.profilr.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("profile")
public class PageProfile extends WebResource {
	
	public static final String navElementName = "profile";
	
	public PageProfile(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/profile")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
}
