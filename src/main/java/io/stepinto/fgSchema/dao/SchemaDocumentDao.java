package io.stepinto.fgSchema.dao;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Optional;

public class SchemaDocumentDao {

    public Optional<StreamSource> load(URI uri) {
        try {
            return Optional.of(new StreamSource(new FileInputStream(uri.getPath()), uri.getPath()));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }
}
