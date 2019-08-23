package io.github.profilr.web.resources;

import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.QuestionType;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;

@Path("rename")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class PageRename extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageRename(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("question-type/{questionTypeID}")
	@Template(name="/rename")
	public Response getRenameQuestionType(@PathParam("questionTypeID") int questionTypeID) {
		QuestionType qt = entityManager.find(QuestionType.class, questionTypeID);
		
		ExceptionUtils.check(qt, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"name", qt.getName(),
				"renameUrl", urlMappings.get("renameQuestionTypeUrl")+"/"+qt.getQuestionTypeID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+qt.getCourse().getCourseID()+"#questionTypesTab"
		)).build();
	}
	
	@POST
	@Path("question-type/{questionTypeID}")
	public Response renameQuestionType(@PathParam("questionTypeID") int questionTypeID, @FormParam("name") String name) {
		QuestionType qt = entityManager.find(QuestionType.class, questionTypeID);
		
		ExceptionUtils.check(qt, session);
		
		qt.setName(name);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("topic/{topicID}")
	@Template(name="/rename")
	public Response getRenameTopic(@PathParam("topicID") int topicID) {
		Topic t = entityManager.find(Topic.class, topicID);
		
		ExceptionUtils.check(t, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"name", t.getName(),
				"renameUrl", urlMappings.get("renameTopicUrl")+"/"+t.getTopicID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#topicsTab"
		)).build();
	}
	
	@POST
	@Path("topic/{topicID}")
	public Response renameTopic(@PathParam("topicID") int topicID, @FormParam("name") String name) {
		Topic t = entityManager.find(Topic.class, topicID);
		
		ExceptionUtils.check(t, session);
		
		t.setName(name);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("section/{sectionID}")
	@Template(name="/rename")
	public Response getRenameSection(@PathParam("sectionID") int sectionID) {
		Section s = entityManager.find(Section.class, sectionID);
		
		ExceptionUtils.check(s, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"name", s.getName(),
				"renameUrl", urlMappings.get("renameSectionUrl")+"/"+s.getSectionID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+s.getCourse().getCourseID()+"#sectionsTab"
		)).build();
	}
	
	@POST
	@Path("section/{sectionID}")
	public Response renameSection(@PathParam("sectionID") int section, @FormParam("name") String name) {
		Section s = entityManager.find(Section.class, section);
		
		ExceptionUtils.check(s, session);

		s.setName(name);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("test/{testID}")
	@Template(name="/rename")
	public Response getRenameTest(@PathParam("testID") int testId) {
		Test t = entityManager.find(Test.class, testId);
		
		ExceptionUtils.checkToEdit(t, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"name", t.getName(),
				"renameUrl", urlMappings.get("renameTestUrl")+"/"+t.getTestID(),
				"redirect", urlMappings.get("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#testsTab"
		)).build();
	}
	
	@POST
	@Path("test/{testID}")
	public Response renameTest(@PathParam("testID") int test, @FormParam("name") String name) {
		Test t = entityManager.find(Test.class, test);
		
		ExceptionUtils.checkToEdit(t, session);
		
		t.setName(name);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("course/{courseID}")
	@Template(name="/rename")
	public Response getRenameCourse(@PathParam("courseID") int courseID) {
		Course c = entityManager.find(Course.class, courseID);
		
		ExceptionUtils.check(c, session);
		
		@SuppressWarnings("unchecked")
		Map<String, String> urlMappings = (Map<String, String>) session.get("urlMappings");
		
		return Response.ok(getView(
				"name", c.getName(),
				"renameUrl", urlMappings.get("renameCourseUrl")+"/"+c.getCourseID(),
				"redirect", urlMappings.get("homeUrl")
		)).build();
	}
	
	@POST
	@Path("course/{courseID}")
	public Response renameCourse(@PathParam("courseID") int courseID, @FormParam("name") String name) {
		Course c = entityManager.find(Course.class, courseID);
		
		ExceptionUtils.check(c, session);
		
		c.setName(name);
		
		return Response.ok().build();
	}
	
}