package io.stepinto.fgSchema.document;

import io.stepinto.fgSchema.TestHarness;
import io.stepinto.fgSchema.dao.SchemaDocumentDao;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.xml.transform.stream.StreamSource;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
public class SchemaDocumentHelperTest extends TestHarness {

    @Mock private SchemaDocumentDao documentDao;
    private SchemaDocumentHelper sut;
    private ConfigurationModel configurationModel;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() throws URISyntaxException {
        configurationModel = new ConfigurationModel().setExternalSchemaPaths(Collections.singletonList("path"));
        Optional<StreamSource> doc = Optional.of(new StreamSource());
        when(documentDao.load(anyString(), eq(true))).thenReturn(doc);
        sut = new SchemaDocumentHelper(documentDao, configurationModel);
    }

    @Test
    public void testInstantiation() {
        int schemaFileCount = this.getSchemaPaths().size() + this.getTestFixturePaths().size();
        assertNotNull(sut.getBuiltInSchemaSourceDocuments());
        assertNotNull(sut.getExternalSchemaSourceDocuments());
        assertEquals(schemaFileCount, sut.getSchemaSourceDocuments().length);
    }

    @Test
    public void testInstantiationBuiltInLoadingFailure() {
        when(documentDao.load(contains("FantasyGrounds"), eq(true))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            new SchemaDocumentHelper(documentDao, configurationModel);
        });
    }

    @Test
    public void testInstantiationExternalLoadingFailure() {
        when(documentDao.load(contains("path"), eq(true))).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> new SchemaDocumentHelper(documentDao, configurationModel));
    }

    @Test
    public void testGetSchemaSourceDocuments() {
        int totalDocs = sut.builtInSchemaSourceDocuments.length + sut.externalSchemaSourceDocuments.length;
        assertEquals(totalDocs, sut.getSchemaSourceDocuments().length);
    }
}
