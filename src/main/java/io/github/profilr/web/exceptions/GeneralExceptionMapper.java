package io.github.profilr.web.exceptions;

import java.time.Instant;

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
public class GeneralExceptionMapper extends ViewableExceptionMapper<Throwable> implements ExceptionMapper<Throwable> {

	protected GeneralExceptionMapper(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@Override
	public Response toResponse(Throwable t) {
		log.error("Returned 500 Internal Server Error because of the following exception", t);
		return Response.status(Status.INTERNAL_SERVER_ERROR)
					   .entity(getViewable("/500", "e", t, "date", Instant.now().formatSystem()))
					   .build();
	}

}
