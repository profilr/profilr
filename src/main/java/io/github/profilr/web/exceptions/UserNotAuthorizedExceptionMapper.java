package io.github.profilr.web.exceptions;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.Session;

@Provider
public class UserNotAuthorizedExceptionMapper extends ViewableExceptionMapper<UserNotAuthorizedException> implements ExceptionMapper<UserNotAuthorizedException> {

	protected UserNotAuthorizedExceptionMapper(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@Override
	public Response toResponse(UserNotAuthorizedException exception) {
		return Response.status(Status.FORBIDDEN)
					   .entity(getViewable("/403"))
					   .build();
	}

}
