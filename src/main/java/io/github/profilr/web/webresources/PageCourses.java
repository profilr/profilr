package io.github.profilr.web.webresources;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
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
import io.github.profilr.web.View;

@Path("courses")
public class PageCourses extends WebResource {
	
	public static final String navElementName = "courses";
	
	public PageCourses(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	public PageCourses(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		super(request, uriInfo);
	}
	
	@GET
	@Template(name="/courses")
	public Response get() {
		super.highlightNavElement(super.getNavElement(navElementName));
		return Response.ok(getView()).build();
	}
	
	public NavElement createNavElement() {
		return new NavElement(navElementName, "Courses", super.buildUri(this.getClass()).toString());
	}
	
	public View getView() {
		View v = super.getView();
		
		List<View> enrolledCourses = new ArrayList<View>();
		if (session.containsKey("user"))
			((User)(session.get("user"))).getEnrolledCourses().stream().map(c -> buildCourseView(c)).forEach(c -> enrolledCourses.add(c));

		List<View> administratedCourses = new ArrayList<View>();
		if (session.containsKey("user"))
			((User)(session.get("user"))).getAdministratedCourses().stream().map(c -> buildCourseView(c)).forEach(c -> administratedCourses.add(c));
		
		v.put("enrolledCourses", enrolledCourses);
		v.put("administratedCourses", administratedCourses);
		
		return v;
	}
	
	public View buildCourseView(Course c) {
		View v = new View();
		
		v.put("name", c.getName());
		
		return v;
	}
	
}
