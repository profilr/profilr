package io.github.profilr.web.resources;

import java.util.Random;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.User;
import io.github.profilr.web.Session;
import io.github.profilr.web.UserNotAuthorizedException;
import io.github.profilr.web.WebResource;

@Path("create-section")
public class PageCreateSection extends WebResource {
	
	@Inject
	EntityManager entityManager;

	public PageCreateSection(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("sectionName") String name, @FormParam("courseId") int course)
			throws UserNotAuthorizedException {
		Course c = entityManager.find(Course.class, course);

		User u = (User) session.get("user");
		if (!u.isCourseAdmin(c))
			throw new UserNotAuthorizedException();

		Section s = new Section();
		s.setCourse(c);
		s.setName(name);

		String joinCode;

		do {
			joinCode = getRandomCode();
		} while (!isValidCode(joinCode));
		
		s.setJoinCode(joinCode);

		entityManager.persist(s);

		return Response.noContent().build();
	}

	private static final String CHARSET = "ABCDEF1234567890";
	private static final int CHAR_COUNT = CHARSET.length();
	private static final int LENGTH = 6;
	private static final long RANDOM_COUNT = Math.round(Math.pow(CHAR_COUNT, LENGTH));
	
	private static final Random random = new Random();
	
	private static String getRandomCode() {
		long r = nextLong(random, 0, RANDOM_COUNT);
		char[] code = new char[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			code[i] = CHARSET.charAt((int) (r % CHAR_COUNT));
			r /= CHAR_COUNT;
		}
		return new String(code); 
	}
	
	// Taken from JavaDocs of Random#longs(long, long)
	private static long nextLong(Random random, long origin, long bound) {
		long r = random.nextLong();
		long n = bound - origin, m = n - 1;
		if ((n & m) == 0L) // power of two
			r = (r & m) + origin;
		else if (n > 0L) { // reject over-represented candidates
			for (long u = r >>> 1; // ensure nonnegative
					u + m - (r = u % n) < 0L; // rejection check
					u = random.nextLong() >>> 1) // retry
				;
			r += origin;
		} else { // range not representable as long
			while (r < origin || r >= bound)
				r = random.nextLong();
		}
		return r;
	}

	private boolean isValidCode(String joinCode) {
		org.hibernate.Session session = entityManager.unwrap(org.hibernate.Session.class);
		@SuppressWarnings("deprecation")
		Criteria c = session.createCriteria(Section.class);
		c.add(Restrictions.eq("joinCode", joinCode));
		return c.list().size() == 0;
	}

}