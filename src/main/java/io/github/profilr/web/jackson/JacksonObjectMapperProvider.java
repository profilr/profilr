package io.github.profilr.web.jackson;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.profilr.domain.Question;
import io.github.profilr.domain.Test;

@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

	@Inject
	EntityManager entityManager;
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule deserializationModule = new SimpleModule();
		if (type.equals(Test.class)) {
			deserializationModule.addDeserializer(Test.class, new TestDeserializer(entityManager));
		} else if (type.equals(Question.class)) {
			deserializationModule.addDeserializer(Question.class, new QuestionDeserializer(entityManager));
		}
		mapper.registerModule(deserializationModule);
		return mapper;
	}

}