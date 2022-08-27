package io.stepinto.fgSchema;

import io.stepinto.FgSchema.exception.ConfigurationException;
import io.stepinto.FgSchema.exception.ProcessingException;
import io.stepinto.FgSchema.helpers.Marshaller;
import io.stepinto.FgSchema.schema.BaseRoot;
import io.stepinto.FgSchema.schema.DefinitionRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarshallerTest {
    private Marshaller<DefinitionRoot> sut;
    private DefinitionRoot expectedObj;
    private String expectedXml;

    @BeforeEach
    public void setup() throws ConfigurationException {
        expectedObj = new DefinitionRoot();
        expectedXml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?><root/>";
        sut = new Marshaller<>(DefinitionRoot.class);
    }

    @Test
    public void marshalTest() throws ProcessingException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        sut.marshal(expectedObj, stream);
        String actualXml = stream.toString(StandardCharsets.UTF_8);
        assertEquals(expectedXml, actualXml);
    }

    @Test
    public void unmarshalTest() throws ProcessingException {
        InputStream xmlStream = new ByteArrayInputStream(expectedXml.getBytes());
        BaseRoot actualObj = sut.unmarshal(xmlStream);
        assertEquals(expectedObj.version(), actualObj.version());
    }
}
