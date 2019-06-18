package io.github.profilr.web.webresources;

import java.net.URI;
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
	
	public View getView() {
		if (!session.containsKey("navElements"))
			session.put("navElements", createNavElements());
		
		if (!session.containsKey("urlMappings"))
			session.put("urlMappings", createUrlMappings());
		
		View v = new View(session);
		v.put("navElements", session.get("navElements"));
		v.put("urlMappings", session.get("urlMappings"));
		
		return v;
	}
	
	public URI buildUri(Class<?> resource) {
		return uriInfo.getBaseUriBuilder().path(resource).build();
	}

	public Map<String, NavElement> createNavElements() {
		System.out.println("Creating nav elements...");
		
		Map<String, NavElement> elements = new HashMap<String, NavElement>();
		
		addNavElement(elements, new PageHome(session, uriInfo).createNavElement());
		addNavElement(elements, new PageProfile(session, uriInfo).createNavElement());
		
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
	
	public Map<String, String> createUrlMappings() {
		System.out.println("Creating URL mappings...");
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("splashUrl", uriInfo.getBaseUriBuilder().path(PageSplash.class).build().toString());
		params.put("homeUrl", uriInfo.getBaseUriBuilder().path(PageHome.class).build().toString());
		params.put("authUrl", uriInfo.getBaseUriBuilder().path(PageAuthorize.class).build().toString());
		params.put("profileUrl", uriInfo.getBaseUriBuilder().path(PageProfile.class).build().toString());
		
		return params;
	}
	
}
