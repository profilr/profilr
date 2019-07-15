package io.github.profilr.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("/")
public class PageSplash extends WebResource {

	public PageSplash(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	@GET
	@Template(name="/splash")
	public Response get() {
		return Response.ok(super.getView()).build();
	}

}
