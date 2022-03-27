package io.stepinto.fgSchema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import io.stepinto.fgSchema.utils.EnvironmentHelper;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class provides an abstracted way for unit and integration tests
 * to access actual XML schema documents as test fixtures.  Methods in
 * this class do not rely on any source code from other classes in this
 * project.
 *
 * Many XML schemas required for these tests reside in the resources root,
 * and as such, are part of application production source code.  Generally,
 * these would not be considered ideal test fixtures.  In this case, however,
 * it will be necessary to access these schemas for integration testing
 * including tests on the schemas themselves.
 *
 * Additionally, some classes under test require valid schemas to function,
 * and the production schemas provide an easily maintained source.
 */
@ExtendWith(MockitoExtension.class)
public class TestHarness {

    protected static final String SCHEMA_FOLDER_PATH = "/schema";
    protected static final String FIXTURES_FOLDER_PATH = "/fixtures";
    protected static final String TESTING_INSTANCE_FILENAME = "testing-instance.xml";
    @Getter
    private final List<Path> schemaPaths;
    @Getter
    private final List<Path> testFixturePaths;
    @Getter
    private final List<StreamSource> schemaSources;
    private final ObjectMapper objectMapper;

    public TestHarness() {
        schemaPaths = generatePaths(SCHEMA_FOLDER_PATH);
        testFixturePaths = generatePaths(FIXTURES_FOLDER_PATH);
        schemaSources = generateSchemaSources();
        objectMapper = new ObjectMapper();
    }

    public StreamSource[] getSchemaSourceArray() {
        return schemaSources.toArray(StreamSource[]::new);
    }

    public void setConfiguration(ConfigurationModel config) throws JsonProcessingException {
        String configString = objectMapper.writeValueAsString(config);
        System.setProperty(EnvironmentHelper.CONFIGURATION_PROPERTY_NAME, configString);
    }

    public Path getFixtureFolderPath() {
        return testFixturePaths.get(0).getParent();
    }

    public Path getSchemaFolderPath() {
        return schemaPaths.get(0).getParent();
    }

    private List<Path> generatePaths(String folderUri) {
        try {
            Path schemaFolderPath = Paths.get(Objects.requireNonNull(getClass().getResource(folderUri)).toURI());
            return Files.walk(schemaFolderPath, 1)
                    .filter(path -> !path.toFile().isDirectory())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private List<StreamSource> generateSchemaSources() {
        return schemaPaths.stream().map(this::generateStreamSource).collect(Collectors.toList());
    }

    private StreamSource generateStreamSource(Path path) {
        try {
            return new StreamSource(new FileInputStream(path.toFile()), path.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
