package io.stepinto.fgSchema.document;

import io.stepinto.fgSchema.TestHarness;
import io.stepinto.fgSchema.dao.SchemaDocumentDao;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import javax.xml.transform.stream.StreamSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@Tag("unit")
public class SchemaDocumentHelperTest extends TestHarness {

    @Mock private SchemaDocumentDao documentDao;
    private SchemaDocumentHelper sut;
    private ConfigurationModel configurationModel;

    @BeforeEach
    public void setup() throws URISyntaxException {
        configurationModel = new ConfigurationModel().setExternalSchemaUris(Collections.singletonList(getClass().getResource("/fixtures/testing-instance.xml").toURI()));
        when(documentDao.load(any(URI.class))).thenReturn(Optional.of(new StreamSource()));
        sut = new SchemaDocumentHelper(documentDao, configurationModel);
    }

    @Test
    public void testInstantiation() {
        int schemaFileCount = this.getSchemaUris().size() + this.getTestFixtureUris().size();
        assertNotNull(sut.getBuiltInSchemaSourceDocuments());
        assertNotNull(sut.getExternalSchemaSourceDocuments());

        verify(documentDao, times(schemaFileCount)).load(any(URI.class));
    }

    @Test
    public void testInstantiationBuiltInLoadingFailure() {
        when(documentDao.load(argThat(new UriMatcher(".*FantasyGrounds.*")))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            new SchemaDocumentHelper(documentDao, configurationModel);
        });
    }

    @Test
    public void testInstantiationExternalLoadingFailure() {
        when(documentDao.load(argThat(new UriMatcher(".*testing-instance.*")))).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> new SchemaDocumentHelper(documentDao, configurationModel));
    }

    @Test
    public void testGetSchemaSourceDocuments() {
        int totalDocs = sut.builtInSchemaSourceDocuments.length + sut.externalSchemaSourceDocuments.length;
        assertEquals(totalDocs, sut.getSchemaSourceDocuments().length);
    }

    private static class UriMatcher implements ArgumentMatcher<URI> {

        private final String referenceUri;

        public UriMatcher(String referenceUri) {
            this.referenceUri = referenceUri;
        }

        @Override
        public boolean matches(URI argument) {
            return argument.getPath().matches(referenceUri);
        }
    }
}
