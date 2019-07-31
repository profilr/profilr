package io.github.profilr.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.github.profilr.web.resources.PageSplash;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
	ResourceInfo info;
	
	@Context
	HttpServletRequest request;
	
	@Inject
	EntityManager entityManager;
	
	public void filter(ContainerRequestContext requestContext) throws IOException {

		if (info.getResourceClass().isAnnotationPresent(PreAuth.class) || info.getResourceMethod().isAnnotationPresent(PreAuth.class))
			return;

		Session session = new Session(request.getSession());
		
		if (session.get("token") == null) {
			requestContext.abortWith(Response.seeOther(
								requestContext.getUriInfo().getBaseUriBuilder()
											  .path(PageSplash.class)
											  .build())
						  .build()
			);
		}
		
	}
	
}
