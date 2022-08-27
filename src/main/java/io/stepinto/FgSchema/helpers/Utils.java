package io.stepinto.FgSchema.helpers;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Utils {
    public static String getStringFromDocument(Document document, String encoding) throws TransformerException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        getStreamFromDocument(document, stream, encoding);
        return stream.toString(StandardCharsets.UTF_8);
    }

    public static String getStringFromDocument(Document document) throws TransformerException {
        return getStringFromDocument(document, null);
    }

    public static void getStreamFromDocument(Document document, OutputStream stream, String encoding) throws TransformerException {
        String omitXmlDeclaration = encoding == null ? "yes" : "no";
        DOMSource source = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        if (encoding != null) {
            transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        }

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration);
        StreamResult result = new StreamResult(stream);
        transformer.transform(source, result);
    }

    public static void getStreamFromDocument(Document document, OutputStream stream) throws TransformerException {
        getStreamFromDocument(document, stream, null);
    }
}
