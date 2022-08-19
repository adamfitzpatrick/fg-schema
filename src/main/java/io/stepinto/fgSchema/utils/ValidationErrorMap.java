package io.stepinto.fgSchema.utils;

import com.qindesign.json.schema.Error;
import com.qindesign.json.schema.JSONPath;
import io.stepinto.fgSchema.dao.ValidationItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValidationErrorMap extends HashMap<JSONPath, Map<JSONPath, Error<?>>> {
    private final List<ValidationItem> validationItems;

    public ValidationErrorMap() {
        this.validationItems = new ArrayList<>();
    }

    @Override
    public Map<JSONPath, Error<?>> put(JSONPath schemaContext, Map<JSONPath, Error<?>> errorSchemaMap) {
        validationItems.addAll(errorSchemaMap.entrySet().stream()
                                             .map(getMapper(schemaContext))
                                             .collect(Collectors.toList()));
        return super.put(schemaContext, errorSchemaMap);
    }

    public List<ValidationItem> getItemList() {
        return this.validationItems;
    }

    private Function<Map.Entry<JSONPath, Error<?>>, ValidationItem> getMapper(JSONPath schemaContext) {
        return entry -> new ValidationItem(schemaContext, entry.getKey(), entry.getValue());
    }
}
