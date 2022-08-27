package io.stepinto.fgSchema.helpers;

import io.stepinto.FgSchema.exception.ConfigurationException;
import io.stepinto.FgSchema.exception.ProcessingException;
import io.stepinto.FgSchema.helpers.DynamicElementPostProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DynamicElementPostProcessorTest {
    private DynamicElementPostProcessor sut;
    private ByteArrayInputStream docStream;
    private DocumentBuilder builder;

    @BeforeEach
    public void setup() throws ParserConfigurationException, ConfigurationException {
        sut = new DynamicElementPostProcessor();
        docStream = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><root><thing runtimeTagName=\"runtime\"/></root>".getBytes());
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    @Test
    public void processTest() throws ProcessingException, IOException, SAXException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        sut.process(docStream, outputStream);
        Document document = builder.parse(new ByteArrayInputStream(outputStream.toByteArray()));

        NodeList nodeList = document.getElementsByTagName("runtime");
        assertEquals(1, nodeList.getLength());
        assertFalse(nodeList.item(0).hasAttributes());
    }
}
