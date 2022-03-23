package io.stepinto.fgSchema.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.stepinto.fgSchema.dom.ConfigurationModel;

import java.io.IOException;

public class EnvironmentHelper {
    public static final String CONFIGURATION_PROPERTY_NAME = "FGSCHEMA_ENV_CONFIG";

    private static EnvironmentHelper instance;

    private final ObjectMapper objectMapper;

    protected ConfigurationModel configurationModel;

    public static EnvironmentHelper get() {
        if (instance == null) {
            instance = new EnvironmentHelper();
        }
        return instance;
    }
    private EnvironmentHelper() {
        objectMapper = new ObjectMapper();
    }

    public void readEnvironment() throws IOException {
        String configString = System.getProperty(EnvironmentHelper.CONFIGURATION_PROPERTY_NAME);
        configurationModel = objectMapper.readValue(configString, ConfigurationModel.class);
    }

    public ConfigurationModel getConfig() {
        return configurationModel;
    }
}
