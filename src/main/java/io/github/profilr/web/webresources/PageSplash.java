package io.github.profilr.web.webresources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

@Path("/")
public class PageSplash extends WebResource {

	public PageSplash(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		super(request, uriInfo);
	}

	@GET
	@Template(name="/splash")
	public Response get() {
		return Response.ok(super.getView()).build();
	}

}
