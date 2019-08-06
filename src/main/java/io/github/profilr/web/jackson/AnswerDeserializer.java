package io.github.profilr.web.jackson;

import java.io.IOException;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.github.profilr.domain.Answer;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.Reason;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(JsonNodeCheckedExtensions.class)
public class AnswerDeserializer extends StdDeserializer<Answer> {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;
	
	public AnswerDeserializer(EntityManager entityManager) {
		super(Answer.class);
		this.entityManager = entityManager;
	}

	@Override
	public Answer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = p.readValueAsTree();
		
		System.out.println(node.toString());
		
		Answer a = new Answer();
		a.setCorrect(node.getValueChecked(p, "correct", Boolean.class));
		a.setNotes(node.getValueChecked(p, "notes", String.class));
		a.setReason(node.hasNonNull("reason_id") ?
				entityManager.find(Reason.class, (Object) node.getValueChecked(p, "reason_id", Integer.class)) : null);
		a.setQuestion(entityManager.find(Question.class, (Object) node.getValueChecked(p, "question_id", Integer.class)));
		
		return a;
	}

}
