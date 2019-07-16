package io.github.profilr.web.jackson;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(JsonNodeCheckedExtensions.class)
public class TestDeserializer extends StdDeserializer<Test> {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;
	
	public TestDeserializer(EntityManager entityManager) {
		super(Test.class);
		Objects.requireNonNull(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public Test deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Objects.requireNonNull(entityManager);
		JsonNode testNode = parser.readValueAsTree();
		Test test = new Test();
		test.setName(testNode.getValueChecked(parser, "name", String.class));
		test.setCourse(entityManager.find(Course.class, (Object) testNode.getValueChecked(parser, "course_id", Integer.class)));
		ArrayNode questions = testNode.getValueChecked(parser, "questions", ArrayNode.class);
		for (JsonNode questionNode : questions) {
			if (!questionNode.isObject())
				throw new JsonParseException(parser, String.format("Element of array questions is of wrong type %s, should be Object", questionNode.getNodeType().toString()));
			Question question = new Question();
			question.setLabel(questionNode.getValueChecked(parser, "label", String.class));
			question.setText(questionNode.getValueChecked(parser, "text", String.class));
			question.setTopic(entityManager.find(Topic.class, (Object) questionNode.getValueChecked(parser, "topic_id", Integer.class)));
			question.setTest(test);
		}
		return test;
	}
	
}
