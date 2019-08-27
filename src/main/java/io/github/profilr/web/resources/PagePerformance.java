package io.github.profilr.web.resources;

import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.trueCondition;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSeekStep2;
import org.jooq.impl.DSL;

import io.github.profilr.domain.Course;
import io.github.profilr.web.Session;
import io.github.profilr.web.WebResource;
import io.github.profilr.web.exceptions.ExceptionUtils;

@Path("performance")
public class PagePerformance extends WebResource {
	
	@Inject
	EntityManager entityManager;
	
	public PagePerformance(Session session, @Context UriInfo uriInfo) {
		super(session, uriInfo);
	}
	
	@GET
	@Path("{courseID}")
	@Produces(MediaType.TEXT_HTML)
	@Template(name="/performance")
	public Response getPerformance(@PathParam("courseID") int courseID) {
		
		Course c = entityManager.find(Course.class, courseID);
		
		ExceptionUtils.check(c, session);
		
		return Response.ok(getView("course", c)).build();
	}
	
	@GET
	@Path("bytopic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response byTopic(@QueryParam("courseID") int courseID,
							@QueryParam("testID") @DefaultValue("-1") int testID,
							@QueryParam("questionTypeID") @DefaultValue("-1") int questionTypeID,
							@QueryParam("sectionID") @DefaultValue("-1") int sectionID,
							@QueryParam("userID") @DefaultValue("-1") String userID) {
		
		
		return Response.ok(entityManager.unwrap(org.hibernate.Session.class).doReturningWork(connection -> {
			
			DSLContext database = DSL.using(connection, SQLDialect.MYSQL_5_7);

			SelectJoinStep<Record2<String, BigDecimal>> joins = 
				database.select(field("Topics.name", String.class).as("Topic"),
								avg(field("Answers.correct", int.class).div(field("Questions.weight", int.class))).as("Performance"))
						.from(table("Answers"))
						.join(table("Questions"))
							.on(field("Questions.question_id", int.class)
								.eq(field("Answers.question_id", int.class)))
						.join(table("Tests"))
							.on(field("Tests.test_id", int.class)
								.eq(field("Questions.test_id", int.class)))
						.join(table("Topics"))
							.on(field("Topics.topic_id", int.class)
								.eq(field("Questions.topic_id", int.class)));
			
			SelectConditionStep<Record2<String, BigDecimal>> where;
			
			if (sectionID != -1)
				where = joins.join(table("SectionUsers"))
							 	.on(field("SectionUsers.user_id", int.class)
									 .eq(field("Answers.user_id", int.class)))
							 .where(field("SectionUsers.section_id", int.class).eq(sectionID));
			else if (!userID.equals("-1"))
				where = joins.where(field("Answers.user_id", String.class).eq(userID));
			else
				where = joins.where(trueCondition());
			
			if (testID != -1)
				where = where.and(field("Tests.test_id", int.class).eq(testID));
			else
				where = where.and(field("Tests.course_id", int.class).eq(courseID));
			
			if (questionTypeID != -1)
				where = where.and(field("Questions.question_type_id").eq(questionTypeID));
			
			SelectSeekStep2<Record2<String, BigDecimal>, BigDecimal, String> q = where.groupBy(Arrays.asList(field("Questions.topic_id", int.class)))
																   .orderBy(field("Performance", BigDecimal.class).desc(),
																		   	field("Topic", String.class).asc());
			
			return q.fetchMap(q.field(0, String.class), q.field(1, BigDecimal.class));
			
		})).build();
		
	}
	
}
