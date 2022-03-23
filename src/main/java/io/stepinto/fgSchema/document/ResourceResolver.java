package io.stepinto.fgSchema.document;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceResolver implements LSResourceResolver {

    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        Path basePath = new File(baseURI).toPath();
        Path namespacePath = Paths.get("./" + systemId);
        FileInputStream byteStream;
        try {
            byteStream = new FileInputStream(basePath.resolve(namespacePath).toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to load data for namespace reference");
        }
        return new ResourceInput().systemId(systemId).byteStream(byteStream).baseURI(baseURI);
    }
}
