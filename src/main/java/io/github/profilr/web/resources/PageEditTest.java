package io.github.profilr.web.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
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

import io.github.profilr.domain.Question;
import io.github.profilr.domain.Test;
import io.github.profilr.web.Session;
import io.github.profilr.web.StringCleanseExtensions;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;
import lombok.experimental.ExtensionMethod;

@Path("edit-test/{test-id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ExtensionMethod(StringCleanseExtensions.class)
public class PageEditTest extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	@PathParam("test-id")
	int testID;
	
	public PageEditTest(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Template(name="/edittest")
	@Produces(MediaType.TEXT_HTML)
	public Response get() {
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToEdit(t, session);
		
		return Response.ok(getView("test", t, "topics", t.getCourse().getTopics(), "types", t.getCourse().getQuestionTypes())).build();
	}

	@POST
	@Path("create-question")
	public Response createQuestion(Question question) {
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToEdit(t, session);
		
		question.setTest(t);
		question.cleanse();
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
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToEdit(t, session);
		
		question.setQuestionID(questionID);
		question.setTest(t);
		question.cleanse();
		entityManager.merge(question);
		return Response.ok().build();
	}
	
	@POST
	@Path("delete-question/{question-id}")
	public Response deleteQuestion(@PathParam("question-id") int questionID) {
		Question q = entityManager.find(Question.class, questionID);
		
		ExceptionUtils.check(q, session);
		
		entityManager.remove(q);
		return Response.ok().build();
	}
	
	@POST
	@Path("publish")
	public Response publish() {
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToEdit(t, session);
		
		t.setPublished(true);
		entityManager.merge(t);
		
		return Response.ok().build();
	}
	
	@POST
	@Path("unpublish")
	public Response unpublish() {
		Test t = entityManager.find(Test.class, testID);
		
		ExceptionUtils.checkToEdit(t, session);
		
		t.setPublished(false);
		entityManager.merge(t);
		
		return Response.ok().build();
	}
	
}
