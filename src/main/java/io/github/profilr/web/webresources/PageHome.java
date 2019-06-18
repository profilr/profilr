package io.github.profilr.web.webresources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.web.NavElement;
import io.github.profilr.web.Session;

@Path("home")
public class PageHome extends WebResource {
	
	public static final String navElementName = "home";
	
	public PageHome(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	public PageHome(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		super(request, uriInfo);
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
