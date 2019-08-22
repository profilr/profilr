package io.github.profilr.db;

import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import io.github.profilr.domain.Answer;
import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.Reason;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.TestResponse;
import io.github.profilr.domain.Topic;
import io.github.profilr.domain.User;

public class SessionFactoryFactory implements Supplier<SessionFactory> {

	@Override
	public SessionFactory get() {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
				.build();
				//using default hibernate.properties file in classpath
		
		Metadata metadata = new MetadataSources(ssr)
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Section.class)
				.addAnnotatedClass(Test.class)
				.addAnnotatedClass(Topic.class)
				.addAnnotatedClass(Question.class)
				.addAnnotatedClass(Reason.class)
				.addAnnotatedClass(Answer.class)
				.addAnnotatedClass(TestResponse.class)
				.getMetadataBuilder()
				.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
				.build();
		
		return metadata.getSessionFactoryBuilder().build();
	}

}
