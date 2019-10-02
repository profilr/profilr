package io.github.profilr.web.exceptions;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.Session;

@Provider
public class ResourceNotFoundExceptionMapper extends ViewableExceptionMapper<ResourceNotFoundException> implements ExceptionMapper<ResourceNotFoundException> {

	protected ResourceNotFoundExceptionMapper(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@Override
	public Response toResponse(ResourceNotFoundException exception) {
		return Response.status(Status.NOT_FOUND)
					   .entity(getViewable("/404-resource"))
					   .build();
	}

}
