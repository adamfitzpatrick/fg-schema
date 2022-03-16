package io.stepinto.fgSchema.utils;

import io.stepinto.fgSchema.TestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationModelTest extends TestHarness {
    private ConfigurationModel sut;

    @BeforeEach
    void setUp() {
        sut = new ConfigurationModel();
        sut.setExternalSchemaUris(this.getSchemaUris());
    }

    @Test
    public void testEquals() {

    }

    @Test
    public void getExternalSchemaUris() {
    }

    @Test
    public void setExternalSchemaUris() {
    }
}