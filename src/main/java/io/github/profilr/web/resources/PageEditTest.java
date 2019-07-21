package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.domain.Question;
import io.github.profilr.domain.Test;
import io.github.profilr.web.PreAuth;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("edit-test/{test-id}")
@PreAuth //TODO REMOVE ME ASAP - ONLY FOR TESTING
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PageEditTest extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	@PathParam("test-id")
	int testID;
	
	public PageEditTest(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@POST
	@Path("create-question")
	public Response createQuestion(Question question) {
		question.setTest(entityManager.find(Test.class, testID));
		entityManager.persist(question);
		return Response.created(uriInfo.getBaseUriBuilder()
									   .path(PageEditTest.class, "editQuestion")
									   .build(testID, question.getQuestionID()))
					   .entity(question)
					   .build();
	}
	
	@POST
	@Path("edit-question/{question-id}")
	public Response editQuestion(Question question, @PathParam("question-id") int questionID) {
		question.setQuestionID(questionID);
		entityManager.merge(question);
		return Response.ok(question).build();
	}
	
}