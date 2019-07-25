package io.github.profilr.web.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("edit-test/{test-id}")
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
	
	@GET
	@Template(name="/edittest")
	@Produces(MediaType.TEXT_HTML)
	public Response get() throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, testID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		return Response.ok(getView(t)).build();
	}
	
	public Map<String, Object> getView(Test test) {
		Map<String, Object> v = super.getView();
		Map<String, Object> testView = new HashMap<String, Object>();
		
		List<Map<String, Object>> topicList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> questionList = new ArrayList<Map<String, Object>>();
		
		Course c = test.getCourse();
		
		for (Topic topic : c.getTopics())
			topicList.add(topic.getView());
		
		for (Question question : test.getQuestions())
			questionList.add(question.getView());
		
		testView.put("name", test.getName());
		testView.put("testId", test.getTestID());
		testView.put("questions", questionList);
		v.put("test", testView);
		v.put("topics", topicList);
		return v;
	}
	
	@POST
	@Path("create-question")
	public Response createQuestion(Question question) throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, testID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		question.setTest(t);
		entityManager.persist(question);
		return Response.created(uriInfo.getBaseUriBuilder()
									   .path(PageEditTest.class, "editQuestion")
									   .build(testID, question.getQuestionID()))
					   .entity(question)
					   .build();
	}
	
	@POST
	@Path("edit-question/{question-id}")
	public Response editQuestion(Question question, @PathParam("question-id") int questionID) throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, testID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		question.setQuestionID(questionID);
		question.setTest(t);
		entityManager.merge(question);
		return Response.ok().build();
	}
	
	@POST
	@Path("delete-question/{question-id}")
	public Response deleteQuestion(@PathParam("question-id") int questionID) throws UserNotAuthorizedException {
		Question q = entityManager.find(Question.class, questionID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(q.getTest().getCourse()))
			throw new UserNotAuthorizedException();
		
		entityManager.remove(q);
		return Response.ok().build();
	}
	
}
