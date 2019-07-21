package io.github.profilr.web.jackson;

import java.io.IOException;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.github.profilr.domain.Question;
import io.github.profilr.domain.Topic;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(JsonNodeCheckedExtensions.class)
public class QuestionDeserializer extends StdDeserializer<Question> {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;
	
	public QuestionDeserializer(EntityManager entityManager) {
		super(Question.class);
		this.entityManager = entityManager;
	}

	@Override
	public Question deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = p.readValueAsTree();
		Question q = new Question();
		if (node.has("question_id")) {
			q.setQuestionID(node.getValueChecked(p, "question_id", Integer.class));
		}
		q.setLabel(node.getValueChecked(p, "label", String.class));
		q.setText(node.getValueChecked(p, "text", String.class));
		q.setWeight(node.getValueChecked(p, "weight", Integer.class));
		q.setTopic(entityManager.find(Topic.class, (Object) node.getValueChecked(p, "topic_id", Integer.class)));
		return q;
	}

}
