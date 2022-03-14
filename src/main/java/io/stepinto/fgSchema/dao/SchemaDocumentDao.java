package io.stepinto.fgSchema.dao;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;

public class SchemaDocumentDao {

    public Optional<StreamSource> load(URL url) {
        try {
            return Optional.of(new StreamSource(new FileInputStream(url.getPath()), url.getPath()));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }
}
