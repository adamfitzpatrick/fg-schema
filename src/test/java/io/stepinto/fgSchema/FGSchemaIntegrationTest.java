package io.stepinto.fgSchema;

import io.stepinto.fgSchema.document.SchemaDocumentHelper;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDOMImplementation;

import javax.xml.transform.stream.StreamSource;
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
    public void testHappyPath() throws Exception {
        sut = new FGSchema(helper);
        // NodeList nodeList = huh();
        sut.validate(new StreamSource(instance));
    }

    public NodeList huh() throws Exception {

        String expression = "/root";
        return null;
    }
}
