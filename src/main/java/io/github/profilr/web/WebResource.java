package io.github.profilr.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.web.resources.PageAuthorize;
import io.github.profilr.web.resources.PageCourses;
import io.github.profilr.web.resources.PageCreateCourse;
import io.github.profilr.web.resources.PageDeleteCourse;
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
		System.out.println("Creating nav elements...");
		
		Map<String, NavElement> elements = new HashMap<String, NavElement>();
		
		addNavElement(elements, new PageHome(session, uriInfo).createNavElement());
		addNavElement(elements, new PageProfile(session, uriInfo).createNavElement());
		addNavElement(elements, new PageCourses(session, uriInfo).createNavElement());
		
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
	
	private Map<String, String> cachedURLMappings;
	
	public Map<String, String> createUrlMappings() {
		if (cachedURLMappings == null) {
			System.out.println("Creating URL mappings...");
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("stylesheets", uriInfo.getBaseUriBuilder().path("/styles/").toString());
			params.put("splashUrl", buildUri(PageSplash.class));
			params.put("homeUrl", buildUri(PageHome.class));
			params.put("authUrl", buildUri(PageAuthorize.class));
			params.put("profileUrl", buildUri(PageProfile.class));
			params.put("coursesUrl", buildUri(PageCourses.class));
			params.put("createCourseUrl", buildUri(PageCreateCourse.class));
			params.put("deleteCourseUrl", buildUri(PageDeleteCourse.class));
			
			cachedURLMappings = params;
		}
		return cachedURLMappings;
	}
	
}
