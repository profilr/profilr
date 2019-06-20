package io.github.profilr.web.webresources;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.web.NavElement;
import io.github.profilr.web.Session;
import io.github.profilr.web.View;

public abstract class WebResource extends javax.ws.rs.core.Application {
	
	@Context
	protected ServletContext context;
	
	protected Session session;
	protected UriInfo uriInfo;
	
	public WebResource(Session session, @Context UriInfo uriInfo) {
		this.session = session;
		this.uriInfo = uriInfo;
	}
	
	public WebResource(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		this(new Session(request), uriInfo);
	}
	
	public Session getSession() { return this.session; }
	
	public UriInfo getUriInfo() { return this.uriInfo; }
	
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
			
			params.put("splashUrl", buildUri(PageSplash.class));
			params.put("homeUrl", buildUri(PageHome.class));
			params.put("authUrl", buildUri(PageAuthorize.class));
			params.put("profileUrl", buildUri(PageProfile.class));
			params.put("createCourseUrl", buildUri(PageCreateCourse.class));
			
			cachedURLMappings = params;
		}
		return cachedURLMappings;
	}
	
}
