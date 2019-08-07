package io.github.profilr.web.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.profilr.domain.Answer;

public class AnswerSerializer extends StdSerializer<Answer> {

	private static final long serialVersionUID = 1L;

	protected AnswerSerializer() {
		super(Answer.class);
	}

	@Override
	public void serialize(Answer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("answer_id", value.getAnswerID());
		gen.writeNumberField("question_id", value.getQuestion().getQuestionID());
		gen.writeStringField("user_id", value.getUser().getUserID());
		gen.writeStringField("notes", value.getNotes());
		if (value.getReason() == null)
			gen.writeNullField("reason_id");
		else
			gen.writeNumberField("reason_id", value.getReason().getReasonID());
		gen.writeNumberField("correct", value.getCorrect());
		gen.writeEndObject();
	}

}
