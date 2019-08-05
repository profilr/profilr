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
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("rename")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class PageRename extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PageRename(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("topic/{topicID}")
	@Template(name="/rename")
	public Response getRenameTopic(@PathParam("topicID") int topicID) throws UserNotAuthorizedException {
		Topic t = entityManager.find(Topic.class, topicID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
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
	public Response renameTopic(@PathParam("topicID") int topicID, @FormParam("name") String name) throws UserNotAuthorizedException {
		Topic t = entityManager.find(Topic.class, topicID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		t.setName(name);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("section/{sectionID}")
	@Template(name="/rename")
	public Response getRenameSection(@PathParam("sectionID") int sectionID) throws UserNotAuthorizedException {
		Section s = entityManager.find(Section.class, sectionID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(s.getCourse()))
			throw new UserNotAuthorizedException();
		
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
	public Response renameSection(@PathParam("sectionID") int section, @FormParam("name") String name) throws UserNotAuthorizedException {
		Section s = entityManager.find(Section.class, section);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(s.getCourse()))
			throw new UserNotAuthorizedException();

		s.setName(name);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("test/{testID}")
	@Template(name="/rename")
	public Response getRenameTest(@PathParam("testID") int testId) throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, testId);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
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
	public Response renameTest(@PathParam("testID") int test, @FormParam("name") String name) throws UserNotAuthorizedException {
		Test t = entityManager.find(Test.class, test);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(t.getCourse()))
			throw new UserNotAuthorizedException();
		
		t.setName(name);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("course/{courseID}")
	@Template(name="/rename")
	public Response getRenameCourse(@PathParam("courseID") int courseID) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
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
	public Response renameCourse(@PathParam("courseID") int courseID, @FormParam("name") String name) throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, courseID);
		
		User u = (User) session.get("user");
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();
		
		c.setName(name);
		
		return Response.ok().build();
	}
	
}