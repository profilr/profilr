package io.github.profilr.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.web.resources.PageAuthorize;
import io.github.profilr.web.resources.PageCourseAdminView;
import io.github.profilr.web.resources.PageCreateCourse;
import io.github.profilr.web.resources.PageCreateSection;
import io.github.profilr.web.resources.PageCreateTest;
import io.github.profilr.web.resources.PageCreateTopic;
import io.github.profilr.web.resources.PageDeleteCourse;
import io.github.profilr.web.resources.PageDeleteSection;
import io.github.profilr.web.resources.PageDeleteTest;
import io.github.profilr.web.resources.PageDeleteTopic;
import io.github.profilr.web.resources.PageHome;
import io.github.profilr.web.resources.PageProfile;
import io.github.profilr.web.resources.PageSplash;

public abstract class WebResource {
	
	protected Session session;
	protected UriInfo uriInfo;
	
	protected WebResource(Session session, @Context UriInfo uriInfo) {
		this.session = session;
		this.uriInfo = uriInfo;
	}
	
	public void highlightNavElement(NavElement navElement) {
		if (navElement == null)
			return;
		
		if (session.containsKey("highlightedNav")) {
			NavElement highlightedElement = (NavElement) (session.get("highlightedNav"));
			highlightedElement.remove("highlighted");
		}
		
		session.put("highlightedNav", navElement);
		navElement.put("highlighted", Boolean.TRUE);
	}
	
	public View getView(Object... args) {
		if (!session.containsKey("navElements"))
			session.put("navElements", createNavElements());
		
		if (!session.containsKey("urlMappings"))
			session.put("urlMappings", createUrlMappings());
		
		View v = new View(session);
		v.put("navElements", session.get("navElements"));
		v.put("urlMappings", session.get("urlMappings"));
		
		for (int i = 0; i+1 < args.length; i+=2)
			v.put((String) args[i], args[i+1]);
		
		return v;
	}
	
	public Map<String, NavElement> createNavElements() {
		Map<String, NavElement> elements = new HashMap<String, NavElement>();
		
		addNavElement(elements, new PageHome(session, uriInfo).createNavElement());
		addNavElement(elements, new PageProfile(session, uriInfo).createNavElement());
		addNavElement(elements, new PageHome(session, uriInfo).createNavElement());
		
		return elements;
	}
	
	public void addNavElement(Map<String, NavElement> elements, NavElement e) {
		elements.put(e.getName(), e);
	}
	
	public NavElement getNavElement(String element) {
		@SuppressWarnings("unchecked")
		Map<String, NavElement> elements = (Map<String, NavElement>) session.get("navElements");
		
		if (elements == null)
			return null;
		
		return elements.get(element);
	}
	
	public String buildUri(Class<?> resource) {
		return uriInfo.getBaseUriBuilder().path(resource).build().toString();
	}
	
	public String buildUri(String path) {
		return uriInfo.getBaseUriBuilder().path(path).build().toString();
	}
	
	private Map<String, String> cachedURLMappings;
	
	public Map<String, String> createUrlMappings() {
		if (cachedURLMappings == null) {
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("stylesheets", buildUri("/styles"));
			params.put("images", buildUri("/images"));
			params.put("favicon", buildUri("/favicon.ico"));
			params.put("splashUrl", buildUri(PageSplash.class));
			params.put("homeUrl", buildUri(PageHome.class));
			params.put("authUrl", buildUri(PageAuthorize.class));
			params.put("profileUrl", buildUri(PageProfile.class));
			
			params.put("courseAdminViewUrl", buildUri(PageCourseAdminView.class));
			
			params.put("createCourseUrl", buildUri(PageCreateCourse.class));
			params.put("deleteCourseUrl", buildUri(PageDeleteCourse.class));
			
			params.put("createSectionUrl", buildUri(PageCreateSection.class));
			params.put("deleteSectionUrl", buildUri(PageDeleteSection.class));
			
			params.put("createTopicUrl", buildUri(PageCreateTopic.class));
			params.put("deleteTopicUrl", buildUri(PageDeleteTopic.class));
			
			params.put("createTestUrl", buildUri(PageCreateTest.class));
			params.put("deleteTestUrl", buildUri(PageDeleteTest.class));
			
			cachedURLMappings = params;
		}
		return cachedURLMappings;
	}
	
}
