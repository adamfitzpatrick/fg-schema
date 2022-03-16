package io.stepinto.fgSchema.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
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
    public void testLoad() {
        URL url = getClass().getResource("/fixtures/testing-instance.xml");
        assertNotNull(url);
        assertTrue(sut.load(url).isPresent());
    }

    @Test
    public void testLoadFileNotFound() throws MalformedURLException {
        URL url = new URL("file:/bad");
        assertNotNull(url);
        assertTrue(sut.load(url).isEmpty());
    }
}
