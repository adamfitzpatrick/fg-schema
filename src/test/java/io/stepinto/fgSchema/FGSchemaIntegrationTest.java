package io.stepinto.fgSchema;

import io.stepinto.fgSchema.document.SchemaDocumentHelper;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Tag("integration")
public class FGSchemaIntegrationTest {
    private static final String INSTANCE_FILE = "/fixtures/testing-instance.xml";
    private InputStream instance;
    private SchemaDocumentHelper helper;
    private FGSchema sut;

    @BeforeEach
    public void setup() {
        instance = getClass().getResourceAsStream(INSTANCE_FILE);
        ConfigurationModel configurationModel = new ConfigurationModel()
                .setExternalSchemaPaths(Collections.emptyList());
        helper = new SchemaDocumentHelper(configurationModel);
    }

    @Test
    public void testHappyPath() throws SAXException, IOException {
        sut = new FGSchema(helper);
        sut.validate(new StreamSource(instance));
    }
}
