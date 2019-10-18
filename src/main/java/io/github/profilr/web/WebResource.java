package io.github.profilr.web;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import io.github.profilr.web.resources.PageAbout;
import io.github.profilr.web.resources.PageAuthorize;
import io.github.profilr.web.resources.PageCourseView;
import io.github.profilr.web.resources.PageCreate;
import io.github.profilr.web.resources.PageDelete;
import io.github.profilr.web.resources.PageEnroll;
import io.github.profilr.web.resources.PageHome;
import io.github.profilr.web.resources.PageInvite;
import io.github.profilr.web.resources.PageKick;
import io.github.profilr.web.resources.PageLeaveCourse;
import io.github.profilr.web.resources.PageLogout;
import io.github.profilr.web.resources.PagePerformance;
import io.github.profilr.web.resources.PageProfile;
import io.github.profilr.web.resources.PageRename;
import io.github.profilr.web.resources.PageSplash;
import io.github.profilr.web.resources.PageUnenroll;

public abstract class WebResource {
	
	public static final boolean DEBUG_MODE_ENABLED = true; //TODO make false in production
	
	protected Session session;
	protected UriInfo uriInfo;
	
	protected WebResource(Session session, @Context UriInfo uriInfo) {
		this.session = session;
		this.uriInfo = uriInfo;
	}
	
	public View getView(Object... args) {
		// We regenerate urlMappings on the fly in debug mode
		// so that when using JRebel, we can add routes dynamically
		View v = new View(session);
		v.put("urlMappings", getUrlMappings());
		
		for (int i = 0; i+1 < args.length; i+=2)
			v.put((String) args[i], args[i+1]);
		
		return v;
	}
	
	private String buildUri(Class<?> resource) {
		return uriInfo.getBaseUriBuilder().path(resource).build().toString();
	}
	
	private String buildUri(Class<?> resource, String path) {
		return uriInfo.getBaseUriBuilder().path(resource).path(path).build().toString();
	}
	
	private String buildUri(String path) {
		return uriInfo.getBaseUriBuilder().path(path).build().toString();
	}
	
	
	/**
	 * This Map caches URL mappings between requests.
	 * Because users may access the site through different Base URIs, we store one set of URL mappings per base URI.
	 * When a user accesses the website, we look up a URL mapping based on the base URI of the request.
	 * <ul>
	 *		<li>During production, the base URI should be {@code https://profilr.org/} for all benign requests</li>
	 *		<li>During development, the base URI should be {@code http://profilr.tk:8080/} for all benign requests</li>
	 *		<li>If a malicious actor attempts to poison the cache by creating a request with a bad base URI, he only affects
	 *     		requests made to that base URI, which shouldn't matter anyway</li>
	 *		<li>Sometimes, Eclipse tries to be helpful and hits {@code http://localhost:8080/} in order to check if the server
	 *			has successfully started. This will not affect any future requests, as they should all be to {@code profilr.tk:8080}.</li>
	 * </ul>
	 */
	private static Map<URI, Map<String, String>> cachedURLMappings = new HashMap<>();
	
	private Map<String, String> getUrlMappings() {
		if (!cachedURLMappings.containsKey(uriInfo.getBaseUri()) || DEBUG_MODE_ENABLED) {
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("stylesheets",			buildUri("/styles"));
			params.put("images",				buildUri("/images"));
			params.put("favicon",				buildUri("/favicon.ico"));
			params.put("splashUrl",				buildUri(PageSplash.class));
			params.put("aboutUrl",				buildUri(PageAbout.class));
			params.put("homeUrl",				buildUri(PageHome.class));
			params.put("authUrl",				buildUri(PageAuthorize.class));
			params.put("profileUrl",			buildUri(PageProfile.class));
			params.put("logoutUrl",				buildUri(PageLogout.class));
			
			params.put("courseViewUrl", 		buildUri(PageCourseView.class));
			
			params.put("createCourseUrl",		buildUri(PageCreate.class, "course"));
			params.put("renameCourseUrl",		buildUri(PageRename.class, "course"));
			params.put("deleteCourseUrl",		buildUri(PageLeaveCourse.class));
			
			params.put("createSectionUrl",		buildUri(PageCreate.class, "section"));
			params.put("renameSectionUrl",		buildUri(PageRename.class, "section"));
			params.put("deleteSectionUrl",		buildUri(PageDelete.class, "section"));
			
			params.put("createTopicUrl",		buildUri(PageCreate.class, "topic"));
			params.put("renameTopicUrl",		buildUri(PageRename.class, "topic"));
			params.put("deleteTopicUrl",		buildUri(PageDelete.class, "topic"));
			
			params.put("createQuestionTypeUrl",	buildUri(PageCreate.class, "question-type"));
			params.put("renameQuestionTypeUrl",	buildUri(PageRename.class, "question-type"));
			params.put("deleteQuestionTypeUrl",	buildUri(PageDelete.class, "question-type"));
			
			params.put("createTestUrl",			buildUri(PageCreate.class, "test"));
			params.put("renameTestUrl",			buildUri(PageRename.class, "test"));
			params.put("deleteTestUrl",			buildUri(PageDelete.class, "test"));
			
			params.put("enrollUrl",				buildUri(PageEnroll.class));
			params.put("unenrollUrl",			buildUri(PageUnenroll.class));
			
			params.put("editTestUrl",			buildUri("/edit-test"));
			params.put("editResponseUrl",		buildUri("/tests"));
			params.put("viewResponsesUrl",		buildUri("/view-responses"));
			
			params.put("inviteAdminUrl",		buildUri(PageInvite.class));
			params.put("kickUrl",				buildUri(PageKick.class));
			
			params.put("performanceUrl",		buildUri(PagePerformance.class));
			
			cachedURLMappings.put(uriInfo.getBaseUri(), Collections.unmodifiableMap(params));
		}
		return cachedURLMappings.get(uriInfo.getBaseUri());
	}
	
}
