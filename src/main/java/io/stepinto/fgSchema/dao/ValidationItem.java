package io.stepinto.fgSchema.dao;

import com.qindesign.json.schema.Error;
import com.qindesign.json.schema.JSONPath;
import lombok.Getter;
import lombok.Setter;

public class ValidationItem {
    @Getter @Setter private JSONPath jsonPath;
    @Getter @Setter private JSONPath schemaContext;
    @Getter @Setter private Error<?> error;

    public ValidationItem(JSONPath jsonPath, JSONPath schemaContext, Error<?> error) {
        this.jsonPath = jsonPath;
        this.schemaContext = schemaContext;
        this.error = error;
    }
}
