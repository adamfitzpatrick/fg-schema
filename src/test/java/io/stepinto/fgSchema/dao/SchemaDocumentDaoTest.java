package io.stepinto.fgSchema.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("unit")
public class SchemaDocumentDaoTest {

    private SchemaDocumentDao sut;

    @BeforeEach
    public void setup() {
        sut = new SchemaDocumentDao();
    }

    @Test
    public void testLoadFromJar() {
        String path = "/fixtures/testing-instance.xml";
        assertTrue(sut.load(path).isPresent());
    }

    @Test
    public void testLoadFromFS() throws URISyntaxException {
        String path = getClass().getResource("/fixtures/testing-instance.xml").toURI().getPath();
        assertTrue(sut.load(path, true).isPresent());
    }

    @Test
    public void testLoadFileNotFound() {
        String path = "/bad";
        assertTrue(sut.load(path).isEmpty());
    }
}
