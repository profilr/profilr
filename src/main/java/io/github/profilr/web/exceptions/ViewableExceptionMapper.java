package io.github.profilr.web.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.jersey.server.mvc.Viewable;

import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

public abstract class ViewableExceptionMapper<T extends Throwable> extends WebResource implements ExceptionMapper<T> {

	protected ViewableExceptionMapper(Session session, UriInfo uriInfo) {
		super(session, uriInfo);
	}

	@Override
	public abstract Response toResponse(T exception);
	
	protected Viewable getViewable(String template, Object... args) {
		return new Viewable(template, getView(args));
	}
	
}
