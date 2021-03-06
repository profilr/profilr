package io.github.profilr.web.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.TestResponse;
import io.github.profilr.domain.User;
import io.github.profilr.web.DateFormatterExtensions;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;
import io.github.profilr.web.exceptions.UserNotAuthorizedException;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("courses")
@ExtensionMethod(DateFormatterExtensions.class)
public class PageCourseView extends WebResource {
	
	@Inject
	private EntityManager entityManager;
	
	public PageCourseView(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{course}")
	public Viewable get(@PathParam("course") int courseId) {
		Course c = entityManager.find(Course.class, courseId);
		
		// Ensure that course exists
		ExceptionUtils.checkNull(c);
		
		// Get current user to determine type of view
		User u = session.getUser();
		
		// If the users is an admin of this course then give them the admin view.
		if (u.isCourseAdmin(c))
			return new Viewable("/courseadminview", getView("course", c, "userID", u.getUserID()));
		// userID is used to determine which user to send to the kick page and which user to send to the leave page
		
		// If they're not an admin we need to find what section they are in.
		Section s = u.getSectionFromCourse(c);
		
		// Also I want a list of the submission times.
		Map<String, String> submissionTimes = new HashMap<String, String>();
		
		for (Test t : c.getTests()) {
			Optional<TestResponse> r = u.getResponsesForTest(t, entityManager);

			// Got a random null pointer error. Might as well leave this to prevent anything sketchy in the future.
			if (r == null) {
				log.warn("u.getResponsesForTest returned a null optional ???");
				continue;
			}
			
			if (r.isPresent())
				submissionTimes.put(String.valueOf(t.getTestID()), r.get().getTsCreated().formatHuman());
		}
		
		// If the user doesn't have a section with a matching course, the user is not a part of the course.
		if (s == null)
			throw new UserNotAuthorizedException();
		
		return new Viewable("/coursestudentview", getView("course", c, "section", s, "submissionTimes", submissionTimes));
	}
	
}
