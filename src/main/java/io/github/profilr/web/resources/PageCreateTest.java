package io.github.profilr.web.resources;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.User;
import io.github.profilr.web.PreAuth;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("create-test")
@PreAuth
public class PageCreateTest extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageCreateTest(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/createtest")
	public Response get() {
		return Response.ok(getView()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("testName") String name, @FormParam("courseId") int course) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, course);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		Test t = new Test();
		t.setName(name);
		t.setCourse(c);
		t.setQuestions(new ArrayList<Question>());
		
		entityManager.persist(t);
		
		return Response.noContent().build();
	}
	
}
