package tech.jhipster.lite.common.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import tech.jhipster.lite.error.domain.GeneratorException;

import java.io.IOException;

public class JsonUtils {

  private static ObjectMapper objectMapper = null;
  private static PrettyPrinter customPrettyPrinter = null;

  private JsonUtils() {
    // Cannot be instantiated
  }

  public static ObjectMapper getObjectMapper() {
    if (objectMapper != null) {
      return objectMapper;
    }
    objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    customPrettyPrinter = new CustomPrettyPrinter();
    return objectMapper;
  }

  public static String addValueInArray(String fieldName, String value, String jsonContent) {
    ObjectNode rootNode;
    ObjectMapper objectMapper = getObjectMapper();
    try {
      rootNode = objectMapper.readValue(jsonContent, ObjectNode.class);
    } catch (JsonProcessingException e) {
      throw new GeneratorException("Cannot read json content : " + e.getMessage());
    }

    ArrayNode arrayNode;
    if (rootNode.has(fieldName)) {
      if (rootNode.get(fieldName).getNodeType() == JsonNodeType.ARRAY) {
        arrayNode = ((ArrayNode) rootNode.get(fieldName));
      } else {
        throw new GeneratorException("Cannot add value in field: is not an array");
      }
    } else {
      arrayNode = objectMapper.createArrayNode();
      rootNode.set(fieldName, arrayNode);
    }
    arrayNode.add(value);

    try {
      return objectMapper.writer(customPrettyPrinter).writeValueAsString(rootNode);
    } catch (JsonProcessingException e) {
      throw new GeneratorException("Cannot serialize node : " + e.getMessage());
    }
  }

  private static class CustomPrettyPrinter extends DefaultPrettyPrinter {

    @Override
    public DefaultPrettyPrinter createInstance() {
      return new CustomPrettyPrinter();
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator g) throws IOException {
      g.writeRaw(": ");
    }

    @Override
    public void beforeArrayValues(JsonGenerator g) throws IOException {
      g.writeRaw("");
    }

    @Override
    public void writeEndArray(JsonGenerator g, int nrOfValues) throws IOException {
      g.writeRaw(']');
    }
  }
}
