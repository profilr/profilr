package io.github.profilr.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.web.resources.PageAuthorize;
import io.github.profilr.web.resources.PageCourseView;
import io.github.profilr.web.resources.PageCreate;
import io.github.profilr.web.resources.PageDelete;
import io.github.profilr.web.resources.PageEnroll;
import io.github.profilr.web.resources.PageHome;
import io.github.profilr.web.resources.PageInvite;
import io.github.profilr.web.resources.PageKick;
import io.github.profilr.web.resources.PageLeaveCourse;
import io.github.profilr.web.resources.PagePerformance;
import io.github.profilr.web.resources.PageProfile;
import io.github.profilr.web.resources.PageRename;
import io.github.profilr.web.resources.PageSplash;
import io.github.profilr.web.resources.PageUnenroll;

public abstract class WebResource {
	
	private static final boolean DEBUG_NO_CACHE_URL_MAPPINGS = true; //TODO make false in production
	
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
		
		if (!session.containsKey("urlMappings") || DEBUG_NO_CACHE_URL_MAPPINGS)
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
	
	public String buildUri(Class<?> resource, String path) {
		return uriInfo.getBaseUriBuilder().path(resource).path(path).build().toString();
	}
	
	public String buildUri(String path) {
		return uriInfo.getBaseUriBuilder().path(path).build().toString();
	}
	
	private Map<String, String> cachedURLMappings;
	
	public Map<String, String> createUrlMappings() {
		if (cachedURLMappings == null || DEBUG_NO_CACHE_URL_MAPPINGS) {
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("stylesheets", buildUri("/styles"));
			params.put("images", buildUri("/images"));
			params.put("favicon", buildUri("/favicon.ico"));
			params.put("splashUrl", buildUri(PageSplash.class));
			params.put("homeUrl", buildUri(PageHome.class));
			params.put("authUrl", buildUri(PageAuthorize.class));
			params.put("profileUrl", buildUri(PageProfile.class));
			
			params.put("courseViewUrl", buildUri(PageCourseView.class));
			
			params.put("createCourseUrl", buildUri(PageCreate.class, "course"));
			params.put("renameCourseUrl", buildUri(PageRename.class, "course"));
			params.put("deleteCourseUrl", buildUri(PageLeaveCourse.class));
			
			params.put("createSectionUrl", buildUri(PageCreate.class, "section"));
			params.put("renameSectionUrl", buildUri(PageRename.class, "section"));
			params.put("deleteSectionUrl", buildUri(PageDelete.class, "section"));
			
			params.put("createTopicUrl", buildUri(PageCreate.class, "topic"));
			params.put("renameTopicUrl", buildUri(PageRename.class, "topic"));
			params.put("deleteTopicUrl", buildUri(PageDelete.class, "topic"));
			
			params.put("createTestUrl", buildUri(PageCreate.class, "test"));
			params.put("renameTestUrl", buildUri(PageRename.class, "test"));
			params.put("deleteTestUrl", buildUri(PageDelete.class, "test"));
			
			params.put("enrollUrl", buildUri(PageEnroll.class));
			params.put("unenrollUrl", buildUri(PageUnenroll.class));
			
			params.put("editTestUrl", buildUri("/edit-test"));
			params.put("editResponseUrl", buildUri("/tests"));
			
			params.put("updateResponseUrl", buildUri("/edit-response"));
			
			params.put("inviteAdminUrl", buildUri(PageInvite.class));
			params.put("kickUrl", buildUri(PageKick.class));
			
			params.put("performanceUrl", buildUri(PagePerformance.class));
			
			cachedURLMappings = params;
		}
		return cachedURLMappings;
	}
	
}
