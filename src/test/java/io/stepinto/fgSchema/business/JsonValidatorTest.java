package io.stepinto.fgSchema.business;

import com.qindesign.json.schema.MalformedSchemaException;
import com.qindesign.json.schema.net.URISyntaxException;
import io.stepinto.fgSchema.TestBase;
import io.stepinto.fgSchema.dao.ValidationItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonValidatorTest extends TestBase {
    private JsonValidator sut;

    @BeforeEach
    public void setup() throws MalformedSchemaException, URISyntaxException {
        baseSetup();
        sut = new JsonValidator(validSchema, supportSchemas);
    }

    @Test
    public void constructorHappyPathTest() {
        assertDoesNotThrow(() -> new JsonValidator(validSchema, supportSchemas));
    }

    @Test
    public void validateHappyPathTest() throws MalformedSchemaException {
        List<ValidationItem> items = sut.validate(validInstance);
        assertEquals(0, items.size());
    }
}
