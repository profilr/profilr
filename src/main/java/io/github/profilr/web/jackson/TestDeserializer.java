package io.github.profilr.web.jackson;

import java.io.IOException;

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
		this.entityManager = entityManager;
	}

	@Override
	public Test deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = p.readValueAsTree();
		Test test = new Test();
		test.setName(node.getValueChecked(p, "name", String.class));
		test.setCourse(entityManager.find(Course.class, (Object) node.getValueChecked(p, "course_id", Integer.class)));
		ArrayNode questions = node.getValueChecked(p, "questions", ArrayNode.class);
		for (JsonNode qNode : questions) {
			if (!qNode.isObject())
				throw new JsonParseException(p, String.format("Element of array questions is of wrong type %s, should be Object", qNode.getNodeType().toString()));
			Question q = new Question();
			q.setLabel(qNode.getValueChecked(p, "label", String.class));
			q.setText(qNode.getValueChecked(p, "text", String.class));
			q.setTopic(entityManager.find(Topic.class, (Object) qNode.getValueChecked(p, "topic_id", Integer.class)));
			q.setTest(test);
		}
		return test;
	}
	
}
