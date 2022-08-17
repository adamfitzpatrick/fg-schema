package io.stepinto.fgSchema.utils;

import com.google.gson.JsonElement;
import com.qindesign.json.schema.Annotation;
import com.qindesign.json.schema.Error;
import com.qindesign.json.schema.JSON;
import com.qindesign.json.schema.JSONPath;
import com.qindesign.json.schema.Validator;
import com.qindesign.json.schema.net.URI;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonSchemaSandbox {

    public static void main(String[] args) {
        try {
            InputStream metaSchemaStream = JsonSchemaSandbox.class.getResourceAsStream("/schema/meta-schema.schema.json");
            InputStream schemaStream = JsonSchemaSandbox.class.getResourceAsStream("/schema/definition-file.schema.json");
            InputStream instanceStream = JsonSchemaSandbox.class.getResourceAsStream("/schema/test-instance.json");
            JsonElement metaSchema = JSON.parse(metaSchemaStream);
            JsonElement schema = JSON.parse(schemaStream);
            JsonElement instance = JSON.parse(instanceStream);
            URI metaSchemaUri = URI.parse("https://stepinto.io/fg-schema/meta-schema");
            URI schemaUri = URI.parse("https://stepinto.io/fg-schema/definition-file");
            Map<URI, JsonElement> knownIds = new HashMap<>();
            // knownIds.put(metaSchemaUri, metaSchema);
            // knownIds.put(schemaUri, schema);
            Map<URI, URL> knownUrls = new HashMap<>();
            Validator validator = new Validator(schema, schemaUri, knownIds, knownUrls, null);
            Map<JSONPath, Map<String, Map<JSONPath, Annotation<?>>>> annotations = new HashMap<>();
            Map<JSONPath, Map<JSONPath, Error<?>>> errors = new HashMap<>();
            validator.validate(instance, annotations, errors);
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
