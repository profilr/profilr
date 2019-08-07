package io.github.profilr.web.exceptions;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.Session;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFoundException> {

	@Context
	Session session;
	
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response toResponse(ResourceNotFoundException exception) {
		return Response.status(Status.NOT_FOUND)
					   .entity(new ExceptionMapperViewable(session, uriInfo)
								.getViewable("/404-resource"))
					   .build();
	}

}
