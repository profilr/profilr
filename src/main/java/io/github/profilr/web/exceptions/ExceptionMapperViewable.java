package io.github.profilr.web.exceptions;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

public class ExceptionMapperViewable extends WebResource {

	public ExceptionMapperViewable(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	public Viewable getViewable(String template, Object... args) {
		return new Viewable(template, getView(args));
	}
	
}
