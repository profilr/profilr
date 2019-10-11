package io.github.profilr.web.exceptions;

import java.time.Instant;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.DateFormatterExtensions;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebappVersionExtensions;
import io.sentry.Sentry;
import io.sentry.event.UserBuilder;
import io.sentry.event.interfaces.HttpInterface;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
@ExtensionMethod({DateFormatterExtensions.class, WebappVersionExtensions.class})
public class GeneralExceptionMapper extends ViewableExceptionMapper<Throwable> implements ExceptionMapper<Throwable> {

	@Context
	HttpServletRequest request;
	
	@Context
	ServletContext context;
	
	protected GeneralExceptionMapper(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@Override
	public Response toResponse(Throwable t) {
		log.error("Returned 500 Internal Server Error because of the following exception", t);
		sentryRecordException(t);
		return Response.status(Status.INTERNAL_SERVER_ERROR)
					   .entity(getViewable("/500", "e", t, "date", Instant.now().formatSystem()))
					   .build();
	}

	private void sentryRecordException(Throwable t) {
		try {
			Sentry.getContext().setUser(new UserBuilder().setUsername(session.getUser().getFullName())
														 .setEmail(session.getUser().getEmailAddress())
														 .setId(session.getUser().getUserID())
														 .build());
			Sentry.getContext().setHttp(new HttpInterface(request));
			Sentry.getContext().addTag("version", context.getWebappVersion());
			Sentry.getContext().addExtra("timestamp", Instant.now().formatSystem());
			for (Entry<String, Object> attribute : session.entrySet())
				Sentry.getContext().addExtra("session."+attribute.getKey(), attribute.getValue().toString());
			Sentry.capture(t);
			Sentry.clearContext();
		} catch (NoClassDefFoundError e) {
			// For whatever reason, when running locally Eclipse can't find Sentry's classes,
			// despite them being added through Maven and the app compiling successfully against it.
			// When this NoClassDefFoundError occurs, we ignore it because it means that we're likely
			// running on a local machine, where we don't want to report errors to Sentry.
			log.warn("Sentry class ({}) not found", e.getMessage());
		}
	}

}
