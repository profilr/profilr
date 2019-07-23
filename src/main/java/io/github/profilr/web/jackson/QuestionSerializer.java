package io.github.profilr.web.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.profilr.domain.Question;

public class QuestionSerializer extends StdSerializer<Question> {

	private static final long serialVersionUID = 1L;

	protected QuestionSerializer() {
		super(Question.class);
	}

	@Override
	public void serialize(Question value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("question_id", value.getQuestionID());
		gen.writeNumberField("test_id", value.getTest().getTestID());
		gen.writeStringField("label", value.getLabel());
		gen.writeStringField("text", value.getText());
		gen.writeNumberField("topic_id", value.getTopic().getTopicID());
		gen.writeNumberField("weight", value.getWeight());
		gen.writeEndObject();
	}

}
