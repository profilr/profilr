package io.github.profilr.web.resources;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.User;
import io.github.profilr.web.DateFormatterExtensions;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;
import lombok.experimental.ExtensionMethod;

@Path("view-responses/{test-id}")
@Produces(MediaType.TEXT_HTML)
@ExtensionMethod(DateFormatterExtensions.class)
public class PageViewResponses extends WebResource {

	@Inject
	private EntityManager entityManager;
	
	@PathParam("test-id")
	private int testID;
	
	public PageViewResponses(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/testresponseview")
	public Response get() {
		Test t = entityManager.find(Test.class, testID);
		Course c = t.getCourse();
		
		ExceptionUtils.checkToEdit(t, session);
		
		// Also I want a list of the submission times.
		Map<String, String> submissionTimes = new HashMap<String, String>();
		
		List<User> enrolled = c.getEnrolledStudents();
		
		for (User u : enrolled)
			t.getResponsesForUser(u, entityManager)
			 .ifPresent(ts -> submissionTimes.put(String.valueOf(u.getUserID()),
					 							  ts.getTsCreated().formatHuman()));
		
		return Response.ok(getView("course", c, "test", t, "submissionTimes", submissionTimes)).build();
	}
	
}
