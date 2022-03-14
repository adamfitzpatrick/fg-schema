package io.stepinto.fgSchema;

import io.stepinto.fgSchema.utils.ConfigurationModel;
import io.stepinto.fgSchema.document.SchemaDocumentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class FGSchemaIntTest {
    private InputStream instance;
    private SchemaDocumentHelper helper;
    private FGSchema sut;

    @BeforeEach
    public void setup() {
        instance = getClass().getResourceAsStream("/testing-instance.xml");
        ConfigurationModel configurationModel = new ConfigurationModel()
                .setExternalSchemaUrls(Collections.emptyList());
        helper = new SchemaDocumentHelper(configurationModel);
    }

    @Test
    public void testHappyPath() throws SAXException, IOException {
        sut = new FGSchema(helper);
        sut.validate(new StreamSource(instance));
    }
}
