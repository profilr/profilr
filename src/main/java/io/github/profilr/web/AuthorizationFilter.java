package io.github.profilr.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.webresources.PageSplash;

@Provider
@Authorized
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

	@Context
	ResourceInfo info;
	
	@Context
	HttpServletRequest request;

	public void filter(ContainerRequestContext requestContext) throws IOException {
		Session session = new Session(request.getSession());
		
		if (session.get("token") == null) {
			requestContext.abortWith(Response.seeOther(
					UriBuilder.fromResource(PageSplash.class)
						.queryParam("redirect", requestContext.getUriInfo().getPath())
						.build())
					.build()
			);
		}
	}
	
}
