package io.stepinto.fgSchema.helpers;

import io.stepinto.FgSchema.helpers.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class UtilsTest {
    private String documentString;
    private String documentFragmentString;
    private Document document;

    @BeforeEach
    public void setup() throws ParserConfigurationException, IOException, SAXException {
        documentString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root>content</root>";
        documentFragmentString = documentString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new ByteArrayInputStream(documentString.getBytes()));
    }

    @Test
    public void getStringFromDocumentNonFragmentTest() throws TransformerException {
        String output = Utils.getStringFromDocument(document, StandardCharsets.ISO_8859_1.name());
        assertEquals(documentString, output);
    }

    @Test
    public void getStringFromDocumentFragmentTest() throws TransformerException {
        String output = Utils.getStringFromDocument(document);
        assertEquals(documentFragmentString, output);
    }

    @Test
    public void getStreamFromDocumentTest() throws TransformerException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Utils.getStreamFromDocument(document, stream, StandardCharsets.UTF_8.name());
        assertEquals(documentString, stream.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void getStreamFromDocumentFragmentTest() throws TransformerException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Utils.getStreamFromDocument(document, stream);
        assertEquals(documentFragmentString, stream.toString(StandardCharsets.UTF_8));
    }
}
