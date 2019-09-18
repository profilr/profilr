package io.github.profilr.web.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CharacterLimitExceededExceptionMapper implements ExceptionMapper<CharacterLimitExceededException> {
	
	@Override
	public Response toResponse(CharacterLimitExceededException exception) {
		return Response.status(Status.REQUEST_ENTITY_TOO_LARGE)
					   .build();
	}

}
