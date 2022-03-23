package io.stepinto.fgSchema.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class ObjectMapping {

    public static ObjectMapper getMapper() {
        return new ObjectMapper();
    }

    public static Optional<String> getStringValue(Object object) {
        try {
            return Optional.of(ObjectMapping.getMapper().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> getObject(String jsonString, Class<T> clazz) {
        try {
            return Optional.of(ObjectMapping.getMapper().readValue(jsonString, clazz));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
