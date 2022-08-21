package io.stepinto.fgSchema.business;

import io.stepinto.fgSchema.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class XmlGeneratorTest extends TestBase {
    private XmlGenerator sut;

    @BeforeEach
    public void setup() {
        sut = new XmlGenerator(validInstance);
    }

    @Test
    public void generateHappyPathTest() {
        assertDoesNotThrow(() -> sut.generate(validInstance));
    }

    @Test
    public void toJsonStringHappyPathTest() {
        String expectedXml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><a foo=\"bar\"/><b><c>baz</c></b>";
    }
}
