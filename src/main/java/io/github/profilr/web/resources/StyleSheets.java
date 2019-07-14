package io.github.profilr.web.resources;

import java.io.File;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("/styles")
public class StyleSheets extends WebResource {

	public StyleSheets(Session session, @Context UriInfo uriInfo, @Context ServletContext context) {
		super(session, uriInfo, context);
	}

	@GET
	@Path("/{style}")
	@Produces("text/css")
	public Response getStyle(@PathParam("style") String style) {
		File f = new File(context.getRealPath("styles/" + style));
		
		if (!f.exists()) {
			throw new WebApplicationException(404);
		}
		
		return Response.ok(f).build();
	}
	
}
