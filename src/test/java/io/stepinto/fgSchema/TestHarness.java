package io.stepinto.fgSchema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.stepinto.fgSchema.utils.ConfigurationModel;
import io.stepinto.fgSchema.utils.EnvironmentHelper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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

    private static final String SCHEMA_FOLDER_PATH = "/schema";
    private final List<URL> schemaUrls;
    private final List<StreamSource> schemaSources;
    private final ObjectMapper objectMapper;

    public TestHarness() {
        schemaUrls = generateSchemaUrls();
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

    private List<URL> generateSchemaUrls() {
        URL schemaFolder = getClass().getResource(SCHEMA_FOLDER_PATH);
        assert schemaFolder != null;
        return Arrays.stream(Objects.requireNonNull(new File(schemaFolder.getPath()).listFiles()))
                     .map(this::getFileUrl).collect(Collectors.toList());
    }

    private URL getFileUrl(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<StreamSource> generateSchemaSources() {
        new StreamSource(schemaUrls.get(0).getPath());
        return schemaUrls.stream().map(this::generateStreamSource).collect(Collectors.toList());
    }

    private StreamSource generateStreamSource(URL url) {
        try {
            FileInputStream is = new FileInputStream(url.getPath());
            return new StreamSource(is, url.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
