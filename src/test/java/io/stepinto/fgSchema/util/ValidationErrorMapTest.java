package io.stepinto.fgSchema.util;

import com.qindesign.json.schema.Error;
import com.qindesign.json.schema.JSONPath;
import com.qindesign.json.schema.MalformedSchemaException;
import com.qindesign.json.schema.net.URISyntaxException;
import io.stepinto.fgSchema.TestBase;
import io.stepinto.fgSchema.dao.ValidationItem;
import io.stepinto.fgSchema.utils.ValidationErrorMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationErrorMapTest extends TestBase {
    private Map<JSONPath, Error<?>> errorMap;
    private JSONPath path;
    private ValidationErrorMap sut;

    @BeforeEach
    public void setup() throws MalformedSchemaException, URISyntaxException {
        baseSetup();
        errorMap = new HashMap<>();
        errorMap.put(JSONPath.fromPath("/a"), actualError);
        errorMap.put(JSONPath.fromPath("/b"), nonErrorError);
        path = JSONPath.fromPath("/c");
        sut = new ValidationErrorMap();
    }

    @Test
    public void putTest() {
        sut.put(path, errorMap);
        assertEquals(errorMap, sut.get(path));
    }

    @Test
    public void getItemListTest() {
        sut.put(path, errorMap);
        List<ValidationItem> items = sut.getItemList();
        assertEquals(2, items.size());
        assertEquals("/c", items.get(0).getJsonPath().toString());
        assertEquals("/a", items.get(0).getSchemaContext().toString());
        assertEquals(actualError, items.get(0).getError());
        assertEquals("/c", items.get(1).getJsonPath().toString());
        assertEquals("/b", items.get(1).getSchemaContext().toString());
        assertEquals(nonErrorError, items.get(1).getError());

    }
}
