package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Topic;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("create-topic")
public class PageCreateTopic extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageCreateTopic(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("topicName") String name, @FormParam("courseId") int course) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, course);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		Topic t = new Topic();
		t.setName(name);
		t.setCourse(c);
		
		entityManager.persist(t);
		
		return Response.ok().build();
	}
	
}