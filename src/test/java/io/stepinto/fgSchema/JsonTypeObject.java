package io.stepinto.fgSchema;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonTypeObject {
    public static final JsonObject STRING = JsonTypeObject.generate("string");
    public static final JsonObject NUMBER = JsonTypeObject.generate("number");
    public static final JsonObject INTEGER = JsonTypeObject.generate("integer");
    public static final JsonObject OBJECT = JsonTypeObject.generate("object");
    public static final JsonObject ARRAY = JsonTypeObject.generate("array");
    public static final JsonObject BOOLEAN = JsonTypeObject.generate("boolean");
    public static final JsonObject NULL = JsonTypeObject.generate("null");

    private static JsonObject generate(String keyword) {
        return new JsonObjectBuilder().property("type", new JsonPrimitive(keyword)).build();
    }
}
