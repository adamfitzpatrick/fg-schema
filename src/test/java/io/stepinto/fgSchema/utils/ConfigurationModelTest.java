package io.stepinto.fgSchema.utils;

import io.stepinto.fgSchema.utils.ConfigurationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationModelTest {
    ConfigurationModel one;
    ConfigurationModel two;

    @BeforeEach
    public void setup() {
        one = new ConfigurationModel();
        two = new ConfigurationModel();
    }

    @Test
    void testEqualsNullProvided() {
        assertFalse(one.equals(null));
    }

    @Test
    void testEqualsFalse() {
        two.setExternalSchemaPaths(Collections.singletonList("path"));
        assertFalse(one.equals(two));
    }

    @Test
    void testEqualsTrue() {
        one.setExternalSchemaPaths(Collections.singletonList("path"));
        two.setExternalSchemaPaths(Collections.singletonList("path"));
    }
}