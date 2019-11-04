package io.github.profilr.web.resources;

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
import io.github.profilr.web.StringCleanseExtensions;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;
import lombok.experimental.ExtensionMethod;

@Path("rename")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@ExtensionMethod(StringCleanseExtensions.class)
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
		
		return Response.ok(getView(
				"name", qt.getName(),
				"renameUrl", getUrlMapping("renameQuestionTypeUrl")+"/"+qt.getQuestionTypeID(),
				"redirect", getUrlMapping("courseViewUrl")+"/"+qt.getCourse().getCourseID()+"#questionTypesTab"
		)).build();
	}
	
	@POST
	@Path("question-type/{questionTypeID}")
	public Response renameQuestionType(@PathParam("questionTypeID") int questionTypeID, @FormParam("name") String name) {
		QuestionType qt = entityManager.find(QuestionType.class, questionTypeID);
		
		ExceptionUtils.check(qt, session);
		
		qt.setName(name);
		qt.cleanse();
		
		ExceptionUtils.checkLength(qt);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("topic/{topicID}")
	@Template(name="/rename")
	public Response getRenameTopic(@PathParam("topicID") int topicID) {
		Topic t = entityManager.find(Topic.class, topicID);
		
		ExceptionUtils.check(t, session);
		
		return Response.ok(getView(
				"name", t.getName(),
				"renameUrl", getUrlMapping("renameTopicUrl")+"/"+t.getTopicID(),
				"redirect", getUrlMapping("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#topicsTab"
		)).build();
	}
	
	@POST
	@Path("topic/{topicID}")
	public Response renameTopic(@PathParam("topicID") int topicID, @FormParam("name") String name) {
		Topic t = entityManager.find(Topic.class, topicID);
		
		ExceptionUtils.check(t, session);
		
		t.setName(name);
		t.cleanse();
		
		ExceptionUtils.checkLength(t);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("section/{sectionID}")
	@Template(name="/rename")
	public Response getRenameSection(@PathParam("sectionID") int sectionID) {
		Section s = entityManager.find(Section.class, sectionID);
		
		ExceptionUtils.check(s, session);
		
		return Response.ok(getView(
				"name", s.getName(),
				"renameUrl", getUrlMapping("renameSectionUrl")+"/"+s.getSectionID(),
				"redirect", getUrlMapping("courseViewUrl")+"/"+s.getCourse().getCourseID()+"#sectionsTab"
		)).build();
	}
	
	@POST
	@Path("section/{sectionID}")
	public Response renameSection(@PathParam("sectionID") int section, @FormParam("name") String name) {
		Section s = entityManager.find(Section.class, section);
		
		ExceptionUtils.check(s, session);

		s.setName(name);
		s.cleanse();
		
		ExceptionUtils.checkLength(s);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("test/{testID}")
	@Template(name="/rename")
	public Response getRenameTest(@PathParam("testID") int testId) {
		Test t = entityManager.find(Test.class, testId);
		
		ExceptionUtils.checkToEdit(t, session);
		
		return Response.ok(getView(
				"name", t.getName(),
				"renameUrl", getUrlMapping("renameTestUrl")+"/"+t.getTestID(),
				"redirect", getUrlMapping("courseViewUrl")+"/"+t.getCourse().getCourseID()+"#testsTab"
		)).build();
	}
	
	@POST
	@Path("test/{testID}")
	public Response renameTest(@PathParam("testID") int test, @FormParam("name") String name) {
		Test t = entityManager.find(Test.class, test);
		
		ExceptionUtils.checkToEdit(t, session);
		
		t.setName(name);
		
		/*
		 * For whatever reason, Eclipse compiles `test.cleanse()` correctly using
		 * Lombok's extension method system, but Maven fails to do so successfully.
		 * Oddly enough, `topic.cleanse()`, `course.cleanse()`, etc all compile fine
		 * in both Eclipse and Maven. As a workaround, I am using the static method here.
		 * The same workaround is used at PageCreate#createTest()
		 */
		StringCleanseExtensions.cleanse(t);
		
		ExceptionUtils.checkLength(t);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("course/{courseID}")
	@Template(name="/rename")
	public Response getRenameCourse(@PathParam("courseID") int courseID) {
		Course c = entityManager.find(Course.class, courseID);
		
		ExceptionUtils.check(c, session);
		
		return Response.ok(getView(
				"name", c.getName(),
				"renameUrl", getUrlMapping("renameCourseUrl")+"/"+c.getCourseID(),
				"redirect", getUrlMapping("homeUrl")
		)).build();
	}
	
	@POST
	@Path("course/{courseID}")
	public Response renameCourse(@PathParam("courseID") int courseID, @FormParam("name") String name) {
		Course c = entityManager.find(Course.class, courseID);
		
		ExceptionUtils.check(c, session);
		
		c.setName(name);
		c.cleanse();
		
		ExceptionUtils.checkLength(c);
		
		return Response.ok().build();
	}
	
}