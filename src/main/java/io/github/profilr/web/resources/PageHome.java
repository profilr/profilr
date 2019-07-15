package io.github.profilr.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.web.NavElement;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("home")
public class PageHome extends WebResource {
	
	public static final String navElementName = "home";
	
	public PageHome(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/home")
	public Response get() {
		super.highlightNavElement(super.getNavElement(navElementName));
		return Response.ok(getView()).build();
	}
	
	public NavElement createNavElement() {
		return new NavElement(navElementName, "Home", super.buildUri(this.getClass()).toString());
	}
	
}
