package io.stepinto.fgSchema.document;

import io.stepinto.fgSchema.dao.SchemaDocumentDao;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import lombok.Getter;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
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
        builtInSchemaSourceDocuments = Arrays.stream(getSchemaUris())
                .map(this::loadRequiredSource).toArray(StreamSource[]::new);
        externalSchemaSourceDocuments = configurationModel.getExternalSchemaUris().stream()
                .map(this::loadSource).toArray(StreamSource[]::new);
        combineSourceDocuments();
    }

    private void combineSourceDocuments() {
        schemaSourceDocuments = Arrays.copyOf(builtInSchemaSourceDocuments,
                builtInSchemaSourceDocuments.length + externalSchemaSourceDocuments.length);
        System.arraycopy(externalSchemaSourceDocuments, 0, schemaSourceDocuments,
                builtInSchemaSourceDocuments.length, externalSchemaSourceDocuments.length);
    }

    private StreamSource loadSource(URI uri) {
        // TODO log warning
        return documentDao.load(uri).orElse(null);
    }

    private StreamSource loadRequiredSource(URI uri) {
        return documentDao.load(uri).orElseThrow(loadingException());
    }

    private URI[] getSchemaUris() {
        URI schemaFolderUri;
        try {
            schemaFolderUri = Objects.requireNonNull(getClass().getResource(INTERNAL_SCHEMA_FOLDER)).toURI();
        } catch (URISyntaxException e) {
            throw SchemaDocumentHelper.loadingException().get();
        }
        return getSchemaFiles(schemaFolderUri)
                .map(this::generateSchemaUrls)
                .orElseThrow(SchemaDocumentHelper.loadingException());
    }

    private Optional<File[]> getSchemaFiles(URI schemaFolderUri) {
        return Optional.ofNullable(new File(schemaFolderUri).listFiles());
    }

    private URI[] generateSchemaUrls(File[] schemaFiles) {
        return Arrays.stream(schemaFiles).map(this::generateSchemaUri).toArray(URI[]::new);
    }

    private URI generateSchemaUri(File schemaFile) {
        return schemaFile.toURI();
    }

    private static Supplier<RuntimeException> loadingException() {
        return () -> new RuntimeException(LOADING_ERROR_MESSAGE);
    }
}
