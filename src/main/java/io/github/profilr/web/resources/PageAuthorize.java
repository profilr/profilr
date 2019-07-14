package io.github.profilr.web.resources;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import io.github.profilr.domain.User;
import io.github.profilr.web.NavElement;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@Path("/authorize")
public class PageAuthorize extends WebResource {
	
	@Inject
	EntityManager entityManager;

	public PageAuthorize(Session session, @Context UriInfo uriInfo, @Context ServletContext context) {
		super(session, uriInfo, context);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response get(@QueryParam("token") String tokenString) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport.Builder().build(), new JacksonFactory()).build();
		
		try {
			GoogleIdToken token = verifier.verify(tokenString);
			String name = (String)token.getPayload().get("given_name");
			String userID = (String)token.getPayload().getSubject();
			
			session.put("token", token);
			session.put("username", name);
			
			// This bit is used to set the navbar entry for the profile page to display as the user's name.
			// The navbar should have been created by this point.... unless somebody is super weird and navigates straight to the authorize endpoint when they first access the app.
			NavElement e = super.getNavElement(PageProfile.navElementName);
			if (e != null)
				e.setDisplayName(name);
			
			User u = entityManager.find(User.class, userID);
			
			if (u == null) {
				u = new User();
				u.setUserID(userID);
				u.setEmailAddress(token.getPayload().getEmail());
				u.setGivenName(name);
				u.setFamilyName((String)token.getPayload().get("family_name"));
				
				entityManager.persist(u);
			}
			
			session.put("user", u);
			
			return Response.seeOther(uriInfo.getBaseUriBuilder().path(PageHome.class).build()).build();
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
			return Response.status(504).build();
		}
	}

}
