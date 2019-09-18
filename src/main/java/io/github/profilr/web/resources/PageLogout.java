package io.github.profilr.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.web.PreAuth;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@PreAuth
@Path("logout")
public class PageLogout extends WebResource {

	public PageLogout(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response logout() {
		session.clear();
		return Response.seeOther(uriInfo.getBaseUriBuilder().path(PageSplash.class).build()).build();
	}
	
}
