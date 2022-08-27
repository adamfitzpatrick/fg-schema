package io.stepinto.FgSchema.helpers;

import io.stepinto.FgSchema.exception.ConfigurationException;
import io.stepinto.FgSchema.exception.ProcessingException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DynamicElementPostProcessor {

    public static final String XPATH_STRING = "//*[@runtimeTagName]";
    private final XPathExpression expression;
    private final DocumentBuilder builder;

    public DynamicElementPostProcessor() throws ConfigurationException {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            expression = xpath.compile(XPATH_STRING);
        } catch (ParserConfigurationException | XPathExpressionException e) {
            // TODO Begin incorporating more information about the circumstances in which this may occur in commentary and exception data
            throw new ConfigurationException(e.getMessage(), e);
        }
    }

    public void process(InputStream inputStream, OutputStream outputStream) throws ProcessingException {
        try {
            Document document = builder.parse(inputStream);
            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for (int k = 0; k < nodeList.getLength(); k++) {
                applyDynamicName(document, nodeList.item(k));
            }
            Utils.getStreamFromDocument(document, outputStream, StandardCharsets.ISO_8859_1.name());
        } catch (SAXException | IOException | XPathExpressionException | TransformerException e) {
            // TODO Begin incorporating more information about the circumstances in which this may occur in commentary and exception data
            throw new ProcessingException(e.getMessage(), e);
        }
    }

    private void applyDynamicName(Document document, Node node) {
        document.renameNode(node, null, node.getAttributes().getNamedItem("runtimeTagName").getNodeValue());
        node.getAttributes().removeNamedItem("runtimeTagName");
    }
}
