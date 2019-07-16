package io.github.profilr.web.jackson;

import java.util.function.Function;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonNodeCheckedExtensions {
	
	@SuppressWarnings("unchecked")
	public static <T> T getValueChecked(JsonNode node, JsonParser parser, String key, Class<T> clazz) throws JsonParseException {
		if (clazz.equals(String.class))
			return (T) getValueChecked(node, parser, key, String.class, JsonNode::isTextual, JsonNode::asText);
		if (clazz.equals(int.class) || clazz.equals(Integer.class))
			return (T) getValueChecked(node, parser, key, Integer.class, JsonNode::isInt, JsonNode::asInt);
		if (clazz.equals(JsonNode.class))
			return (T) getValueChecked(node, parser, key, JsonNode.class, JsonNode::isObject, n -> n);
		if (clazz.equals(ArrayNode.class))
			return (T) getValueChecked(node, parser, key, ArrayNode.class, JsonNode::isArray, n -> (ArrayNode) n);
		throw new IllegalArgumentException("Class must be one of String, Integer, JsonNode, or ArrayNode. For all other types please pass custom isCorrectType predicate and mapToType function");
	}
	
	public static <T> T getValueChecked(JsonNode node, JsonParser parser, String key, Class<T> clazz, Predicate<JsonNode> isCorrectType, Function<JsonNode, T> mapToType) throws JsonParseException {
		if (!node.hasNonNull(key))
			throw new JsonParseException(parser, String.format("Property %s must be non-null", key));
		JsonNode valueNode = node.get(key);
		if (!isCorrectType.test(valueNode))
			throw new JsonParseException(parser, String.format("Property %s is of wrong type %s, should be %s", key, valueNode.getNodeType().toString(), clazz.getSimpleName()));
		T t = mapToType.apply(valueNode);
		return t;
	}
	
}
