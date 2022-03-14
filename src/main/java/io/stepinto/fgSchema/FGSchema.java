package io.stepinto.fgSchema;

import io.stepinto.fgSchema.document.ResourceResolver;
import io.stepinto.fgSchema.document.SchemaDocumentHelper;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;

public class FGSchema {
    public static String W3C_XML_SCHEMA_1_1 = "http://www.w3.org/XML/XMLSchema/v1.1";

    private SchemaDocumentHelper schemaDocumentHelper;
    private SchemaFactory schemaFactory;
    private Schema schema;

    public FGSchema(SchemaDocumentHelper schemaDocumentHelper) throws SAXException {
        this(schemaDocumentHelper, SchemaFactory.newInstance(W3C_XML_SCHEMA_1_1), new ResourceResolver());
    }

    public FGSchema(SchemaDocumentHelper schemaDocumentHelper,
                    SchemaFactory schemaFactory,
                    LSResourceResolver resourceResolver) throws SAXException {
        this.schemaDocumentHelper = schemaDocumentHelper;
        this.schemaFactory = schemaFactory;
        this.schemaFactory.setResourceResolver(resourceResolver);
        instantiate();
    }

    private void instantiate() throws SAXException {
        StreamSource[] docs = schemaDocumentHelper.getSchemaSourceDocuments();
        if (docs == null || docs.length == 0) {
            throw new IllegalStateException("No schema documents provided");
        }
        schema = schemaFactory.newSchema(docs);
    }

    public void validate(StreamSource instance) throws IOException, SAXException {
        schema.newValidator().validate(instance);
    }
}
