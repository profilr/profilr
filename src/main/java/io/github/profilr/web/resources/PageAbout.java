package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.web.PreAuth;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.WebappVersionExtensions;
import lombok.experimental.ExtensionMethod;

@Path("about")
@PreAuth
@ExtensionMethod(WebappVersionExtensions.class)
public class PageAbout extends WebResource {

	@Inject
	ServletContext context;
	
	public PageAbout(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	@GET
	@Template(name="/about")
	public Response get() {
		return Response.ok(getView("version", context.getWebappVersion())).build();
	}
	
}
