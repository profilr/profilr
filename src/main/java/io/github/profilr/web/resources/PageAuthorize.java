package io.github.profilr.web.resources;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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
import io.github.profilr.web.PreAuth;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;

@PreAuth
@Path("authorize")
@Produces(MediaType.TEXT_HTML)
public class PageAuthorize extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	private static GoogleIdTokenVerifier verifier;

	public PageAuthorize(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	public Response get(@QueryParam("token") String tokenString) throws GeneralSecurityException, IOException {
		
		GoogleIdToken token;
		try {
			if (tokenString == null)
				throw new NullPointerException("Empty token");
			token = getVerifier().verify(tokenString);
			if (token == null)
				throw new NullPointerException("Invalid token");
		} catch (NullPointerException | IllegalArgumentException e) {
			// If the user does not provide a JWT token in the query string, we throw a NullPointerError intentionally, and handle it here
			// If tokenString is not a valid JWT token, verify(tokenString) will throw a IllegalArgumentException, and we will handle it here
			// If the provided tokenString is not verified, we will throw a NullPointerException intentionally, and handle it here
			// If any of the above occurs, we will redirect the user back to the splash page
			return Response.seeOther(uriInfo.getBaseUriBuilder()
											.path(PageSplash.class)
											.build())
						   .build();
		}
		String name = (String) token.getPayload().get("given_name");
		String userID = (String) token.getPayload().getSubject();
		
		session.put("token", token);
		session.put("username", name);
		
		// This bit is used to set the navbar entry for the profile page to display as the user's name.
		// The navbar should have been created by this point.... unless somebody is super weird and navigates straight to the authorize endpoint when they first access the app.
		
		User u = entityManager.find(User.class, userID);
		
		if (u == null) {
			u = new User();
			u.setUserID(userID);
			u.setEmailAddress(token.getPayload().getEmail());
			u.setGivenName(name);
			u.setFamilyName((String) token.getPayload().get("family_name"));
			
			entityManager.persist(u);
		}
		
		session.put("user", u);

		return Response.seeOther(Optional.ofNullable((URI) session.remove("redirect"))
										 .orElseGet(() -> uriInfo.getBaseUriBuilder()
												 				 .path(PageHome.class)
												 				 .build()))
					   .build();
	}
	
	private GoogleIdTokenVerifier getVerifier() {
		if (verifier == null)
			verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport.Builder().build(),
														 new JacksonFactory()).build();
		return verifier;
	}
	
}
