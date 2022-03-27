package io.stepinto.fgSchema.document;

import io.stepinto.fgSchema.dao.SchemaDocumentDao;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import lombok.Getter;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class loads XML schema documents from application resources as
 * well as from external sources.
 */
public class SchemaDocumentHelper {
    private static final String INTERNAL_SCHEMA_FOLDER = "/schema";
    private static final String JAR_URI_SCHEME = "jar";
    private static final String LOADING_ERROR_MESSAGE = "Unable to access one or more core schema files";
    private static final String LOADING_WARNING_MESSAGE = "Unable to access one or more external schema files";

    private final SchemaDocumentDao documentDao;
    private final boolean isNotJar;

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
        isNotJar = SchemaDocumentDao.determineIsNotJar();
        builtInSchemaSourceDocuments = getInternalSchemaPaths().stream()
                .map(this::loadRequiredSource).toArray(StreamSource[]::new);
        externalSchemaSourceDocuments = configurationModel.getExternalSchemaPaths().stream()
                .map(this::loadSource).toArray(StreamSource[]::new);
        combineSourceDocuments();
    }

    private List<Path> getInternalSchemaPaths() {
        URI schemaFolderUri = getSchemaUri();
        try {
            Path schemaFolderPath;
            if (isNotJar) {
                schemaFolderPath = Paths.get(schemaFolderUri);
            } else {
                FileSystem fileSystem = FileSystems.newFileSystem(schemaFolderUri, Collections.emptyMap());
                System.out.println(fileSystem.toString());
                schemaFolderPath = fileSystem.getPath(INTERNAL_SCHEMA_FOLDER);
            }
            return Files.walk(schemaFolderPath, 1)
                    .filter(path -> !Files.isDirectory(path))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw loadingException(e.getMessage());
        }
    }

    private URI getSchemaUri() {
        try {
            return Objects.requireNonNull(getClass().getResource(INTERNAL_SCHEMA_FOLDER)).toURI();
        } catch (URISyntaxException e) {
            throw loadingException(e.getMessage());
        }
    }

    private void combineSourceDocuments() {
        schemaSourceDocuments = Arrays.copyOf(builtInSchemaSourceDocuments,
                builtInSchemaSourceDocuments.length + externalSchemaSourceDocuments.length);
        System.arraycopy(externalSchemaSourceDocuments, 0, schemaSourceDocuments,
                builtInSchemaSourceDocuments.length, externalSchemaSourceDocuments.length);
    }

    private StreamSource loadSource(String path) {
        return loadSource(Path.of(path));
    }

    private StreamSource loadSource(Path path) {
        // TODO log warning
        return documentDao.load(path.toString(), isNotJar).orElse(null);
    }

    private StreamSource loadRequiredSource(Path path) {
        return documentDao.load(path.toString(), isNotJar).orElseThrow(this::loadingException);
    }

    private RuntimeException loadingException() {
        return loadingException("");
    }

    private RuntimeException loadingException(String message) {
        message = message.isEmpty() ? message : ": " + message;
        return new RuntimeException(LOADING_ERROR_MESSAGE + message);
    }
}
