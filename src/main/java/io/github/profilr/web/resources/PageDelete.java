package io.github.profilr.web.resources;

import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.QuestionType;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;

@Path("delete")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class PageDelete extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageDelete(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("question-type/{questionTypeID}")
	@Template(name="/delete")
	public Response getDeleteQuestionType(@PathParam("questionTypeID") int questionTypeID) {
		QuestionType t = entityManager.find(QuestionType.class, questionTypeID);
		
		ExceptionUtils.check(t, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"type", "Question Type",
				"name", t.getName(),
				"message", "This will delete ALL questions under this question type. You likely don't want to do this.",
				"strong", true,
				"deleteUrl", urlMappings.get("deleteQuestionTypeUrl")+"/"+t.getQuestionTypeID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#questionTypesTab"
		)).build();
	}
	
	@POST
	@Path("question-type/{questionTypeID}")
	public Response deleteQuestionType(@PathParam("questionTypeID") int questionTypeID) {
		QuestionType t = entityManager.find(QuestionType.class, questionTypeID);
		
		ExceptionUtils.check(t, session);
		
		entityManager.remove(t);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("topic/{topicID}")
	@Template(name="/delete")
	public Response getDeleteTopic(@PathParam("topicID") int topicID) {
		Topic t = entityManager.find(Topic.class, topicID);
		
		ExceptionUtils.check(t, session);
		
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
	@Path("topic/{topicID}")
	public Response deleteTopic(@PathParam("topicID") int topicID) {
		Topic t = entityManager.find(Topic.class, topicID);
		
		ExceptionUtils.check(t, session);
		
		entityManager.remove(t);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("section/{sectionID}")
	@Template(name="/delete")
	public Response getDeleteSection(@PathParam("sectionID") int sectionID) {
		Section s = entityManager.find(Section.class, sectionID);
		
		ExceptionUtils.check(s, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"type", "Section",
				"name", s.getName(),
				"message", "This will remove all "+s.getUsers().size()+" students enrolled in this section.",
				"strong", true,
				"deleteUrl", urlMappings.get("deleteSectionUrl")+"/"+s.getSectionID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+s.getCourse().getCourseID()+"#sectionsTab"
		)).build();
	}
	
	@POST
	@Path("section/{sectionID}")
	public Response deleteSection(@PathParam("sectionID") int section) {
		Section s = entityManager.find(Section.class, section);
		
		ExceptionUtils.check(s, session);
		
		entityManager.remove(s);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("test/{testID}")
	@Template(name="/delete")
	public Response getDeleteTest(@PathParam("testID") int testId) {
		Test t = entityManager.find(Test.class, testId);
		
		ExceptionUtils.checkToEdit(t, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"type", "Test",
				"name", t.getName(),
				"message", "All questions and student responses will be deleted as well",
				"deleteUrl", urlMappings.get("deleteTestUrl")+"/"+t.getTestID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#testsTab"
		)).build();
	}
	
	@POST
	@Path("test/{testID}")
	public Response deleteTest(@PathParam("testID") int test) {
		Test t = entityManager.find(Test.class, test);
		
		ExceptionUtils.checkToEdit(t, session);
		
		entityManager.remove(t);
		
		return Response.ok().build();
	}
	
}