package io.github.profilr.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@Path("/")
public class WebResource extends javax.ws.rs.core.Application {
	
	@GET
	public Response root() {
		return Response.seeOther(UriBuilder.fromMethod(WebResource.class, "home").build()).build();
	}
	
	@GET
	@Template(name="/home")
	@Path("home")
	public Response home() {
		return Response.ok(view()).build();
	}
	
	@GET
	@Template(name="/logintest")
	@Path("login")
	public Response login() {
		return Response.ok(view()).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("authorize")
	public Response authorize(@QueryParam("token") String tokenString) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport.Builder().build(), new JacksonFactory()).build();
		
		try {
			GoogleIdToken token = verifier.verify(tokenString);
			
			session.put("token", token);
			
			// TODO: Here's where we should get a user object using the subject of the token as a primary key and put it into the session.
			
			return Response.seeOther(UriBuilder.fromMethod(WebResource.class, "profile").build()).build();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return Response.status(504).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(504).build();
		}
	}
	
	@GET
	@Authorized
	@Template(name="/profile")
	@Path("profile")
	public Response profile() {
		GoogleIdToken token = (GoogleIdToken) session.get("token");
		String name = (String) token.getPayload().get("given_name");
		return Response.ok(view("name", name)).build();
	}
	
	@GET
	@Template(name="/404")
	@Path("{all:.*}")
	public Response notFound(@PathParam("all") String path) {
		return Response.status(Status.NOT_FOUND).entity(view()).build();
	}
	
	private static Object[] cachedUrlParams;
	
	private Session session;
	private UriInfo uriInfo;
	
	public WebResource(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		this.session = new Session(request);
		this.uriInfo = uriInfo;
	}
	
	private WebpageEntity view(Object... args) {
		return new WebpageEntity(session, getUrlParams(), args);
	}
	
	private Object[] getUrlParams() {
		if (cachedUrlParams == null) {
			cachedUrlParams = new Object[] {
				"homeURL", buildUri("home"),
				"cssURL", buildUriSpecial("res/style.css"),
				"loginURL", buildUri("login")
			};
		}
		
		return cachedUrlParams;
	}
	
	private String buildUri(String method) {
		return uriInfo.getBaseUriBuilder().path(WebResource.class, method).build().toString();
	}
	
	private String buildUriSpecial(String path) {
		return uriInfo.getBaseUriBuilder().path(path).build().toString();
	}
	
}
