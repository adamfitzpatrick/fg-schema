package io.stepinto.fgSchema;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.qindesign.json.schema.*;
import com.qindesign.json.schema.Error;
import com.qindesign.json.schema.net.URI;
import com.qindesign.json.schema.net.URISyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBase {
    protected URI baseUri;
    protected JsonObject validSchema;
    protected List<JsonObject> supportSchemas;
    protected JsonObject validInstance;
    protected JsonObject invalidInstance;
    protected Error<?> nonErrorError;
    protected Error<?> actualError;

    public void baseSetup() throws URISyntaxException, MalformedSchemaException {
        baseUri = URI.parse("https://stepinto.io/test-fixture.schem");

        validSchema = new JsonObjectBuilder()
                .property("$id", new JsonPrimitive("https://stepinto.io/test-fixture.schem"))
                .property("$schema", new JsonPrimitive("https://json-schema.org/draft/2019-09/schema"))
                .property("xml", new JsonObjectBuilder()
                        .property("version", new JsonPrimitive("1.0"))
                        .property("encoding", new JsonPrimitive("ISO-8859-1"))
                        .build())
                .property("properties", new JsonObjectBuilder()
                        .property("a", new JsonObjectBuilder()
                                .merge(JsonTypeObject.OBJECT)
                                .property("properties", new JsonObjectBuilder()
                                        .property("b", JsonTypeObject.STRING)
                                        .property("c", JsonTypeObject.STRING)
                                        .build())
                                .build())
                        .build()).build();
        JsonObject supportSchema = new JsonObjectBuilder()
                .property("$id", new JsonPrimitive("https://meta-schema-id-url"))
                .property("$schema", new JsonPrimitive("https://json-schema.org/draft/2019-09/schema"))
                .property("properties", new JsonObjectBuilder()
                        .property("extra", JsonTypeObject.STRING)
                        .build())
                .build();
        supportSchemas = List.of(supportSchema);

        this.validInstance = new JsonObjectBuilder()
                .property("a", new JsonObjectBuilder()
                        .property("b", new JsonPrimitive("foo"))
                        .property("c", new JsonPrimitive("bar"))
                        .build())
                .build();
        invalidInstance = new JsonObjectBuilder()
                .property("a", new JsonPrimitive("foo"))
                .build();

        // This appears to be the only way to get com.qindesign.json.schema.Error's for unit testing. *Le sigh*
        Validator validator = new Validator(validSchema, baseUri, null, null, null);
        Map<JSONPath, Map<JSONPath, Error<?>>> errors = new HashMap<>();
        validator.validate(this.validInstance, null, errors);
        nonErrorError = errors.get(JSONPath.fromPath("/a")).get(JSONPath.fromPath("/properties/a/type"));
        errors = new HashMap<>();
        validator.validate(invalidInstance, null, errors);
        actualError = errors.get(JSONPath.fromPath("/a")).get(JSONPath.fromPath("/properties/a/type"));
    }
}
