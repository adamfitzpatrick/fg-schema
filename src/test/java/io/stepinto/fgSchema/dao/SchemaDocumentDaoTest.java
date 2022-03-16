package io.stepinto.fgSchema.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
    public void testLoad() throws URISyntaxException {
        URI uri = getClass().getResource("/fixtures/testing-instance.xml").toURI();
        assertNotNull(uri);
        assertTrue(sut.load(uri).isPresent());
    }

    @Test
    public void testLoadFileNotFound() throws URISyntaxException {
        URI uri = new URI("file:/bad");
        assertNotNull(uri);
        assertTrue(sut.load(uri).isEmpty());
    }
}
