package io.github.profilr.web.resources;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.web.NavElement;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("home")
public class PageHome extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public static final String navElementName = "home";
	
	public PageHome(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/home")
	public Response get() {
		super.highlightNavElement(super.getNavElement(navElementName));
		
		entityManager.refresh(session.getUser());
		
		Set<Course> enrolledCourses = session.getUser()
											 .getEnrolledCourses()
											 .stream()
											 .filter(c -> c.getAdmins().size() > 0)
											 .collect(Collectors.toCollection(TreeSet::new));

		Set<Course> administratedCourses = session.getUser().getAdministratedCourses();
		
		return Response.ok(getView("enrolledCourses", enrolledCourses,
								   "administratedCourses", administratedCourses,
								   "canCreate", session.getUser().canCreateCourse())).build();
	}
	
	public NavElement createNavElement() {
		return new NavElement(navElementName, "Home", super.buildUri(this.getClass()).toString());
	}
	
}
