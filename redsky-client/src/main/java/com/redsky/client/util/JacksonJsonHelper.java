package com.redsky.client.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.redsky.client.exception.JsonParseException;

public class JacksonJsonHelper {

    public static String serialize(final Object object) throws JsonParseException {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);

        try {
            return mapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new JsonParseException("Error in serializing object", e);
        }

    }

    public static String serialize(final Object object, final Class<?> clazz) throws JsonParseException {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        final ObjectWriter writer = mapper.writerFor(clazz);

        try {
            return writer.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new JsonParseException("Error in serializing object", e);
        }

    }

    public static String serialize(final Object object, final TypeReference<?> typeRef) throws JsonParseException {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        final ObjectWriter writer = mapper.writerFor(typeRef);

        try {
            return writer.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new JsonParseException("Error in serializing object", e);
        }

    }

    public static <T> T deserialize(final String json, final Class<T> clazz) throws JsonParseException {

        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, clazz);
        } catch (final IOException e) {
            throw new JsonParseException(String.format("Error in deserializing json %s", json), e);
        }

    }

    public static <T> T deserialize(final String json, final TypeReference<?> typeRef) throws JsonParseException {

        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, typeRef);
        } catch (final IOException e) {
            throw new JsonParseException(String.format("Error in deserializing json %s", json), e);
        }
    }

    public static <T> T convertValue(final Map<String, Object> map, final Class<T> clazz) throws JsonParseException {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.convertValue(map, clazz);
        } catch (final Exception e) {
            throw new JsonParseException(String.format("Error in converting map %s to pojo of %s", map, clazz), e);
        }
    }
}
