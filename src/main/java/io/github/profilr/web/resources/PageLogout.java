package io.github.profilr.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

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
		return Response.seeOther(uriInfo.getBaseUriBuilder().path(PageLogout.class).path("/success").build()).build();
	}
	
	@GET
	@Template(name="/loggedout")
	@Path("success")
	@Produces(MediaType.TEXT_HTML)
	public Response loggedOut() {
		
		return Response.ok(super.getView()).build();
	}
	
	@GET
	@Path("test")
	public String test() {
		return "HUH";
	}
	
}
