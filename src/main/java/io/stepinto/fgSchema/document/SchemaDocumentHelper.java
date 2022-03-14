package io.stepinto.fgSchema.document;

import io.stepinto.fgSchema.dao.SchemaDocumentDao;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import lombok.Getter;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class loads XML schema documents from application resources as
 * well as from external sources.
 */
public class SchemaDocumentHelper {
    private static final String INTERNAL_SCHEMA_FOLDER = "/schema";
    private static final String LOADING_ERROR_MESSAGE = "Unable to access one or more core schema files";
    private static final String LOADING_WARNING_MESSAGE = "Unable to access one or more external schema files";

    private final SchemaDocumentDao documentDao;

    @Getter
    protected final StreamSource[] builtInSchemaSourceDocuments;
    @Getter
    protected final StreamSource[] externalSchemaSourceDocuments;
    @Getter
    protected StreamSource[] schemaSourceDocuments;

    /**
     * Production constructor accepts only a ConfigurationModel object.
     * @param configurationModel Application configuration parameters
     */
    public SchemaDocumentHelper(ConfigurationModel configurationModel) {
        this(new SchemaDocumentDao(), configurationModel);
    }

    /**
     * Testing constructor
     */
    public SchemaDocumentHelper(SchemaDocumentDao documentDao, ConfigurationModel configurationModel) {
        this.documentDao = documentDao;
        builtInSchemaSourceDocuments = Arrays.stream(getSchemaUrls())
                .map(this::loadRequiredSource).toArray(StreamSource[]::new);
        externalSchemaSourceDocuments = configurationModel.getExternalSchemaUrls().stream()
                .map(this::loadSource).toArray(StreamSource[]::new);
        combineSourceDocuments();
    }

    private void combineSourceDocuments() {
        schemaSourceDocuments = Arrays.copyOf(builtInSchemaSourceDocuments,
                builtInSchemaSourceDocuments.length + externalSchemaSourceDocuments.length);
        System.arraycopy(externalSchemaSourceDocuments, 0, schemaSourceDocuments,
                builtInSchemaSourceDocuments.length, externalSchemaSourceDocuments.length);
    }

    private StreamSource loadSource(URL url) {
        // TODO log warning
        return documentDao.load(url).orElse(null);
    }

    private StreamSource loadRequiredSource(URL url) {
        return documentDao.load(url).orElseThrow(loadingException());
    }

    private URL[] getSchemaUrls() {
        URL schemaFolderUrl = getClass().getResource(INTERNAL_SCHEMA_FOLDER);
        if (null != schemaFolderUrl) {
            return getSchemaFiles(schemaFolderUrl)
                    .map(this::generateSchemaUrls)
                    .orElseThrow(SchemaDocumentHelper.loadingException());
        }
        throw SchemaDocumentHelper.loadingException().get();
    }

    private Optional<File[]> getSchemaFiles(URL schemaFolderUrl) {
        try {
            return Optional.ofNullable(new File(schemaFolderUrl.toURI()).listFiles());
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }

    private URL[] generateSchemaUrls(File[] schemaFiles) {
        return Arrays.stream(schemaFiles).map(this::generateSchemaUrl).toArray(URL[]::new);
    }

    private URL generateSchemaUrl(File schemaFile) {
        try {
            return schemaFile.toURI().toURL();
        } catch (MalformedURLException e) {
            throw loadingException().get();
        }
    }

    private static Supplier<RuntimeException> loadingException() {
        return () -> new RuntimeException(LOADING_ERROR_MESSAGE);
    }
}
