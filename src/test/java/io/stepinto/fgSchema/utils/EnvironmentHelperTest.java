package io.stepinto.fgSchema.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.stepinto.fgSchema.TestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentHelperTest extends TestHarness {

    private ConfigurationModel configurationModel;
    private EnvironmentHelper sut;

    @BeforeEach
    public void setup() throws JsonProcessingException, URISyntaxException {
        configurationModel = new ConfigurationModel()
                .setExternalSchemaUris(Arrays.asList(new URI("file:url1"), new URI("file:url2")));
        this.setConfiguration(configurationModel);
        sut = EnvironmentHelper.get();
    }

    @Test
    public void readEnvironmentTest() throws IOException {
        assertNull(sut.configurationModel);
        sut.readEnvironment();
        assertNotNull(sut.configurationModel);
    }

    @Test
    public void getConfigTest() throws IOException {
        sut.readEnvironment();
        assertEquals(configurationModel, sut.getConfig());
    }
}
