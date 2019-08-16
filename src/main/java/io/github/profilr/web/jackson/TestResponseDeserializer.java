package io.github.profilr.web.jackson;

import java.io.IOException;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.github.profilr.domain.Test;
import io.github.profilr.domain.TestResponse;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(JsonNodeCheckedExtensions.class)
public class TestResponseDeserializer extends StdDeserializer<TestResponse> {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	
	protected TestResponseDeserializer(EntityManager em) {
		super(TestResponse.class);
		this.em = em;
	}
	
	@Override
	public TestResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		TestResponse r = new TestResponse();
		
		JsonNode node = p.readValueAsTree();
		
		r.setText(node.getValueChecked(p, "text", String.class));
		
		if (node.has("test"))
			r.setTest(em.find(Test.class, (Object) node.getValueChecked(p, "test", Integer.class)));
		
		return r;
	}

}
