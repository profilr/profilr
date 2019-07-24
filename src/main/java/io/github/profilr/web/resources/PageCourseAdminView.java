package io.github.profilr.web.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("courses")
public class PageCourseAdminView extends WebResource {
	
	@Inject
	private EntityManager entityManager;
	
	public PageCourseAdminView(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{course}")
	@Template(name="/courseadminview")
	public Response get(@PathParam("course") int courseId) {
		Course c = entityManager.find(Course.class, courseId);
		
		return Response.ok(getView("course", getCourseView(c))).build();
	}
	
	public Map<String, Object> getCourseView(Course c) {
		Map<String, Object> course = new HashMap<String, Object>();
		List<Map<String, Object>> tests = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sections = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> topics = new ArrayList<Map<String, Object>>();
		
		for (Section s : c.getSections())
			sections.add(getSectionView(s));
		
		for (Test t : c.getTests())
			tests.add(getTestView(t));
		
		for (Topic t : c.getTopics())
			topics.add(getTopicView(t));
		
		course.put("courseId", c.getCourseID());
		course.put("name", c.getName());
		course.put("tests", tests);
		course.put("topics", topics);
		course.put("sections", sections);
		return course;
	}
	
	public Map<String, Object> getSectionView(Section s) {
		Map<String, Object> section = new HashMap<String, Object>();
		
		// TODO Eventually we'll probly add some info about the performance of each section in the course.
		
		section.put("sectionId", s.getSectionID());
		section.put("name", s.getName());
		section.put("joinCode", s.getJoinCode());
		
		return section;
	}
	
	public Map<String, Object> getTestView(Test t) {
		Map<String, Object> test = new HashMap<String, Object>();
		
		// TODO Eventually we'll probly add some info about the overall performance on each test.
		
		test.put("testId", t.getTestID());
		test.put("name", t.getName());
		
		return test;
	}
	
	public Map<String, Object> getTopicView(Topic t) {
		Map<String, Object> topic = new HashMap<String, Object>();
		
		// TODO Eventually we'll probly add some info about the overall performance on each topic.
		
		topic.put("topicId", t.getTopicID());
		topic.put("name", t.getName());
		
		return topic;
	}
	
}
