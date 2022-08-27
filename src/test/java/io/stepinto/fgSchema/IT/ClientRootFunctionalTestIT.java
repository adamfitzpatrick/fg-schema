package io.stepinto.fgSchema.IT;

import io.stepinto.FgSchema.exception.ConfigurationException;
import io.stepinto.FgSchema.exception.ProcessingException;
import io.stepinto.FgSchema.helpers.DynamicElementPostProcessor;
import io.stepinto.FgSchema.helpers.Marshaller;
import io.stepinto.FgSchema.schema.Category;
import io.stepinto.FgSchema.schema.ClientRoot;
import io.stepinto.FgSchema.schema.Dice;
import io.stepinto.FgSchema.schema.DynamicResultTableRow;
import io.stepinto.FgSchema.schema.DynamicTableRow;
import io.stepinto.FgSchema.schema.FgSchemaType;
import io.stepinto.FgSchema.schema.Table;
import io.stepinto.FgSchema.schema.TypedElement;
import io.stepinto.fgSchema.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientRootFunctionalTestIT extends TestBase {
    private InputStream expectedTransitionalXmlStream;
    private String expectedTransitionalXml;
    private String expectedFinalXml;
    @BeforeEach
    public void setup() throws IOException {
        baseSetup();
        expectedTransitionalXmlStream = this.getClass().getResourceAsStream("/functional-test-fixtures/transitional-client-expected.xml");
        assert expectedTransitionalXmlStream != null;
        expectedTransitionalXml = new String(expectedTransitionalXmlStream.readAllBytes());
        expectedTransitionalXmlStream = new ByteArrayInputStream(expectedTransitionalXml.getBytes());

        InputStream expectedFinalXmlStream = this.getClass().getResourceAsStream("/functional-test-fixtures/final-client-expected.xml");
        assert expectedFinalXmlStream != null;
        expectedFinalXml = new String(expectedFinalXmlStream.readAllBytes());
    }
    @Test
    public void marshallObjectToTransitionalXmlTest() throws ConfigurationException, ProcessingException {
        Marshaller<ClientRoot> marshaller = new Marshaller<>(ClientRoot.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        marshaller.marshal(clientRoot, outputStream);
        String actualTransitionalXml = outputStream.toString(StandardCharsets.UTF_8);

        assertEquals(expectedTransitionalXml, actualTransitionalXml);
    }

    @Test
    public void dynamicElementPostProcessingTest() throws ProcessingException, ConfigurationException {
        DynamicElementPostProcessor processor = new DynamicElementPostProcessor();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        processor.process(expectedTransitionalXmlStream, outputStream);
        assertEquals(expectedFinalXml, outputStream.toString(StandardCharsets.UTF_8));
    }
}
