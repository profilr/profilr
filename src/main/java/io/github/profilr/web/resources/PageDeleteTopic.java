package io.github.profilr.web.resources;

import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Topic;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("delete-topic")
public class PageDeleteTopic extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageDeleteTopic(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{topicID}")
	@Template(name="/deletegeneric")
	public Response getDelete(@PathParam("topicID") int topicId) throws UserNotAuthorizedException {
		Topic t = entityManager.find(Topic.class, topicId);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"type", "Topic",
				"name", t.getName(),
				"message", "This will delete ALL questions under this topic. You likely don't want to do this.",
				"strong", true,
				"deleteUrl", urlMappings.get("deleteTopicUrl")+"/"+t.getTopicID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#topicsTab"
		)).build();
	}
	
	@POST
	@Path("{topicID}")
	public Response delete(@PathParam("topicID") int topicID) throws UserNotAuthorizedException {
		Topic t = entityManager.find(Topic.class, topicID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		entityManager.remove(t);
		
		return Response.ok().build();
	}
	
}