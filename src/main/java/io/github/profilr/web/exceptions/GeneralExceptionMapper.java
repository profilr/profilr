package io.github.profilr.web.exceptions;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.DateFormatterExtensions;
import io.github.profilr.web.Session;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
@ExtensionMethod(DateFormatterExtensions.class)
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	@Context
	Session session;
	
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response toResponse(Exception exception) {
		log.error("Returned 500 Internal Server Error because of the following exception", exception);
		return Response.status(Status.INTERNAL_SERVER_ERROR)
					   .entity(new ExceptionMapperViewable(session, uriInfo)
								.getViewable("/500", "e", exception, "date", Instant.now().formatSystem()))
					   .build();
	}

}
