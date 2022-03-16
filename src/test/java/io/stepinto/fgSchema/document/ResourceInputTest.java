package io.stepinto.fgSchema.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class ResourceInputTest {

    private static final String INPUT_FILE = "/fixtures/testing-instance.xml";
    private ResourceInput sut;

    @BeforeEach
    public void setUp() {
        sut = new ResourceInput()
                .byteStream(getClass().getResourceAsStream(INPUT_FILE))
                .systemId("systemId")
                .baseURI("baseURI");
    }

    @Test
    public void testByteStream() {
        sut.byteStream(null);
        ResourceInput actual = sut.byteStream(getClass().getResourceAsStream(INPUT_FILE));
        assertEquals(actual, sut);
        assertNotNull(sut.getByteStream());
    }

    @Test
    public void testSystemId() {
        sut.systemId(null);
        ResourceInput actual = sut.systemId("systemId");
        assertEquals(actual, sut);
        assertNotNull(sut.getSystemId());
    }

    @Test
    public void testBaseURI() {
        sut.baseURI(null);
        ResourceInput actual = sut.baseURI("baseURI");
        assertEquals(actual, sut);
        assertNotNull(sut.getBaseURI());
    }
}