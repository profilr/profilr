package io.github.profilr.web;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

public class UserNotAuthorizedViewable extends WebResource {

	public UserNotAuthorizedViewable(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	public Viewable getViewable() {
		return new Viewable("/notauthorized.ftl", getView());
	}
	
}
