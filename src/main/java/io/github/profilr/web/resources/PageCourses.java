package io.github.profilr.web.resources;

import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.User;
import io.github.profilr.web.NavElement;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("courses")
public class PageCourses extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public static final String navElementName = "courses";
	
	public PageCourses(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/courses")
	public Response get() {
		super.highlightNavElement(super.getNavElement(navElementName));
		
		entityManager.refresh(session.get("user"));
		
		Set<Course> enrolledCourses = ((User) session.get("user")).getEnrolledCourses();

		Set<Course> administratedCourses = ((User) session.get("user")).getAdministratedCourses();
		
		return Response.ok(getView("enrolledCourses", enrolledCourses, "administratedCourses", administratedCourses, "canCreate", ((User)session.get("user")).isCourseAdminApproved())).build();
	}
	
	public NavElement createNavElement() {
		return new NavElement(navElementName, "Courses", super.buildUri(this.getClass()).toString());
	}
	
}
