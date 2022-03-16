package io.stepinto.fgSchema;

import io.stepinto.fgSchema.document.ResourceResolver;
import io.stepinto.fgSchema.document.SchemaDocumentHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
public class FGSchemaTest extends TestHarness {

    @Mock private SchemaDocumentHelper schemaDocumentHelper;
    @Mock private SchemaFactory schemaFactory;
    @Mock private ResourceResolver resourceResolver;
    @Mock private Schema schema;

    private Executable sutSupplier;

    @BeforeEach
    public void setup() throws SAXException {
        sutSupplier = () -> new FGSchema(schemaDocumentHelper, schemaFactory, resourceResolver);
    }

    @Test
    public void testConstructorHappyPath() throws SAXException {
        when(schemaDocumentHelper.getSchemaSourceDocuments()).thenReturn(this.getSchemaSourceArray());
        when(schemaFactory.newSchema((StreamSource[]) any())).thenReturn(schema);
        assertDoesNotThrow(sutSupplier);
        verify(schemaDocumentHelper).getSchemaSourceDocuments();
        verify(schemaFactory).newSchema((StreamSource[]) any());
    }

    @Test
    public void testConstructorMissingDocs() {
        when(schemaDocumentHelper.getSchemaSourceDocuments()).thenReturn(this.getSchemaSourceArray());
        when(schemaDocumentHelper.getSchemaSourceDocuments()).thenReturn(new StreamSource[0]);
        assertThrows(IllegalStateException.class, sutSupplier);
        verify(schemaDocumentHelper).getSchemaSourceDocuments();
    }

    @Test
    public void testConstructorBadSchema() throws SAXException {
        when(schemaDocumentHelper.getSchemaSourceDocuments()).thenReturn(this.getSchemaSourceArray());
        when(schemaFactory.newSchema((StreamSource[]) any())).thenThrow(SAXException.class);
        assertThrows(SAXException.class, sutSupplier);
        verify(schemaDocumentHelper).getSchemaSourceDocuments();
        verify(schemaFactory).newSchema((StreamSource[]) any());
    }
}
