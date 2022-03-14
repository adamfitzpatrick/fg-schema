package io.stepinto.fgSchema.document;

import io.stepinto.fgSchema.dao.SchemaDocumentDao;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchemaDocumentHelperTest {

    @Mock private SchemaDocumentDao documentDao;
    private SchemaDocumentHelper sut;
    private ConfigurationModel configurationModel;

    @BeforeEach
    public void setup() {
        configurationModel = new ConfigurationModel().setExternalSchemaUrls(Collections.singletonList(getClass().getResource("/testing-instance.xml")));
        when(documentDao.load(any(URL.class))).thenReturn(Optional.of(new StreamSource()));
        sut = new SchemaDocumentHelper(documentDao, configurationModel);
    }

    @Test
    public void testInstantiation() {
        assertNotNull(sut.getBuiltInSchemaSourceDocuments());
        assertNotNull(sut.getExternalSchemaSourceDocuments());

        verify(documentDao, times(2)).load(any(URL.class));
    }

    @Test
    public void testInstantiationBuiltInLoadingFailure() {
        when(documentDao.load(argThat(new UrlMatcher(".*FantasyGrounds.*")))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            new SchemaDocumentHelper(documentDao, configurationModel);
        });
    }

    @Test
    public void testInstantiationExternalLoadingFailure() {
        when(documentDao.load(argThat(new UrlMatcher(".*testing-instance.*")))).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> new SchemaDocumentHelper(documentDao, configurationModel));
    }

    @Test
    public void testGetSchemaSourceDocuments() {
        int totalDocs = sut.builtInSchemaSourceDocuments.length + sut.externalSchemaSourceDocuments.length;
        assertEquals(totalDocs, sut.getSchemaSourceDocuments().length);
    }

    private static class UrlMatcher implements ArgumentMatcher<URL> {

        private final String referenceUrl;

        public UrlMatcher(String referenceUrl) {
            this.referenceUrl = referenceUrl;
        }

        @Override
        public boolean matches(URL argument) {
            return argument.getPath().matches(referenceUrl);
        }
    }
}
