package io.stepinto.fgSchema.document;

import io.stepinto.fgSchema.TestHarness;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSInput;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class ResourceResolverTest extends TestHarness {
    ResourceResolver sut = new ResourceResolver();

    @Test
    public void testResolveResource() throws URISyntaxException {
        URL baseURL = this.getTestFixtureUrls().get(0);
        LSInput input = sut.resolveResource("not used", "namespaceURI", "not used", TestHarness.TESTING_INSTANCE_FILENAME, baseURL.toString());
        assertEquals(TestHarness.TESTING_INSTANCE_FILENAME, input.getSystemId());
        assertEquals(baseURL.toString(), input.getBaseURI());
        assertNotNull(input.getByteStream());
    }
}