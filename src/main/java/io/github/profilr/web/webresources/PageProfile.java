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

@Path("profile")
public class PageProfile extends WebResource {
	
	public static final String navElementName = "profile";
	
	public PageProfile(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	public PageProfile(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		super(request, uriInfo);
	}
	
	@GET
	@Template(name="/profile")
	public Response get() {
		if (session.containsKey("username"))
			super.getNavElement(navElementName).put("displayName", session.get("username"));
		
		super.highlightNavElement(super.getNavElement(navElementName));
		return Response.ok(getView()).build();
	}
	
	public NavElement createNavElement() {
		NavElement element =  new NavElement(navElementName, "Profile", super.buildUri(this.getClass()).toString());
		element.put("align", "right");
		
		return element;
	}
	
}
