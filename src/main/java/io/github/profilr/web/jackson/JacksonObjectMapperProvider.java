package io.github.profilr.web.jackson;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.profilr.domain.Answer;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.Test;

@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

	@Inject
	EntityManager entityManager;
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule serializationModule = new SimpleModule();
		if (type.equals(Test.class)) {
			serializationModule.addDeserializer(Test.class, new TestDeserializer(entityManager));
		} else if (type.equals(Question.class)) {
			serializationModule.addDeserializer(Question.class, new QuestionDeserializer(entityManager));
			serializationModule.addSerializer(Question.class, new QuestionSerializer());
		} else if (type.equals(Answer.class)) {
			serializationModule.addDeserializer(Answer.class, new AnswerDeserializer(entityManager));
			serializationModule.addSerializer(Answer.class, new AnswerSerializer());
		}
		mapper.registerModule(serializationModule);
		return mapper;
	}

}