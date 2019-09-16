package io.github.profilr.web.exceptions;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

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
	
	private static final Map<Class<? extends Exception>, ExceptionMapper<? extends Exception>> submappers =
		Collections.singletonMap(MysqlDataTruncation.class, new CharacterLimitExceededExceptionMapper());
	
	@Override
	public Response toResponse(Exception exception) {
		Exception e = exception;
		while (e != null)
			if (submappers.containsKey(e.getClass()))
				return getMapper(e).toResponse(e);
			else
				e = (Exception) e.getCause();
		log.error("Returned 500 Internal Server Error because of the following exception", exception);
		return Response.status(Status.INTERNAL_SERVER_ERROR)
					   .entity(new ExceptionMapperViewable(session, uriInfo)
								.getViewable("/500", "e", exception, "date", Instant.now().formatSystem()))
					   .build();
	}

	@SuppressWarnings("unchecked")
	private <T extends Exception> ExceptionMapper<T> getMapper(T e) {
		return (ExceptionMapper<T>) submappers.get(e.getClass());
	}

}
