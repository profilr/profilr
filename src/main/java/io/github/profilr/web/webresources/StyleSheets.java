package io.github.profilr.web.webresources;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/styles")
public class StyleSheets extends WebResource {

	public StyleSheets(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		super(request, uriInfo);
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
