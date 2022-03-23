package io.stepinto.fgSchema.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.stepinto.fgSchema.TestHarness;
import io.stepinto.fgSchema.dom.ConfigurationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentHelperTest extends TestHarness {

    private ConfigurationModel configurationModel;
    private EnvironmentHelper sut;

    @BeforeEach
    public void setup() throws JsonProcessingException, URISyntaxException {
        configurationModel = new ConfigurationModel()
                .setExternalSchemaPaths(Arrays.asList("file1", "file2"));
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
