package io.stepinto.fgSchema;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonObjectBuilder {

    private final JsonObject jsonObject;

    public JsonObjectBuilder() {
        jsonObject = new JsonObject();
    }

    public JsonObjectBuilder property(String propertyName, JsonElement value) {
        jsonObject.add(propertyName, value);
        return this;
    }

    public JsonObjectBuilder merge(JsonObject incoming) {
        incoming.entrySet().forEach(entry -> {
            jsonObject.add(entry.getKey(), entry.getValue());
        });
        return this;
    }

    public JsonObject build() {
        return jsonObject;
    }
}
