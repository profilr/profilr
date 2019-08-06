package io.github.profilr.web;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.Session;

@Provider
public class UserNotAuthorizedMapper implements ExceptionMapper<UserNotAuthorizedException> {

	@Context
	Session session;
	
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response toResponse(UserNotAuthorizedException exception) {
		return Response.status(401)
					   .entity(new ExceptionMapperViewable(session, uriInfo)
								.getViewable("/notauthorized"))
					   .build();
	}

}
