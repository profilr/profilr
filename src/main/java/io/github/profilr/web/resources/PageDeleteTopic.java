package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Topic;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("delete-topic")
public class PageDeleteTopic extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageDeleteTopic(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{topicId}")
	@Template(name="/deletetopic")
	public Response getDelete(@PathParam("topicId") int topicId) {
		Topic t = entityManager.find(Topic.class, topicId);
		
		return Response.ok(getView("topicId", t.getTopicID(), "topicName", t.getName(), "courseId", t.getCourse().getCourseID())).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response delete(@FormParam("topicId") int topic) {
		Topic t = entityManager.find(Topic.class, topic);
		
		entityManager.remove(t);
		
		return Response.ok().build();
	}
	
}