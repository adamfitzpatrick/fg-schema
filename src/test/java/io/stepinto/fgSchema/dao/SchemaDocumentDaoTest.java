package io.stepinto.fgSchema.dao;

import io.stepinto.fgSchema.dao.SchemaDocumentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SchemaDocumentDaoTest {

    private SchemaDocumentDao sut;

    @BeforeEach
    public void setup() {
        sut = new SchemaDocumentDao();
    }

    @Test
    public void testLoad() {
        URL url = getClass().getResource("/testing-instance.xml");
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
