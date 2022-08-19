package io.stepinto.fgSchema.business;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qindesign.json.schema.MalformedSchemaException;
import com.qindesign.json.schema.Validator;
import com.qindesign.json.schema.net.URI;
import com.qindesign.json.schema.net.URISyntaxException;
import io.stepinto.fgSchema.dao.ValidationItem;
import io.stepinto.fgSchema.utils.ValidationErrorMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonValidator {
    private final Validator validator;

    public JsonValidator(JsonObject schema, List<JsonObject> supportingSchema) throws
                                                                                MalformedSchemaException,
                                                                                URISyntaxException {
        URI baseUri = URI.parse(schema.get("$id").getAsString());
        Map<URI, JsonElement> knownIds = generateKnownIds(baseUri, schema, supportingSchema);
        validator = new Validator(schema, baseUri, knownIds, null, null);
    }

    public List<ValidationItem> validate(JsonElement instance) throws MalformedSchemaException {
        ValidationErrorMap errorMap = new ValidationErrorMap();
        validator.validate(instance, null, errorMap);
        return errorMap.getItemList();
    }

    private Map<URI, JsonElement> generateKnownIds(URI baseUri, JsonObject schema, List<JsonObject> supportingSchema) throws URISyntaxException {
        Map<URI, JsonElement> knownIds = new HashMap<>();
        knownIds.put(baseUri, schema);
        for (JsonObject supporter: supportingSchema) {
            URI uri = URI.parse(supporter.get("$id").getAsString());
            knownIds.put(uri, supporter);
        }
        return knownIds;
    }
}
