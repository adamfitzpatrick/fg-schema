package io.stepinto.fgSchema.dao;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

public class SchemaDocumentDao {
    private static final String JAR_URI_SCHEME = "jar";

    /**
     * Returns true if the application is NOT executing from a jar file.
     * The intended use of the application is from a jar; as such, this returns
     * true for the exceptional case.
     * @return true if the application is not executing from a jar file
     */
    public static boolean determineIsNotJar() {
        try {
            return !Objects.requireNonNull(SchemaDocumentDao.class.getResource("/")).toURI().getScheme().equals(JAR_URI_SCHEME);
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public Optional<StreamSource> load(String path) {
        return load(path, false);
    }

    public Optional<StreamSource> load(String path, boolean isNotJar) {
        if (isNotJar) {
            return loadFromFileSystem(path);
        }
        return loadFromJar(path);
    }

    private Optional<StreamSource> loadFromJar(String path) {
        InputStream resource = getClass().getResourceAsStream(path);
        if (resource != null) {
            return Optional.of(new StreamSource(resource, path));
        }
        return Optional.empty();
    }

    private Optional<StreamSource> loadFromFileSystem(String path) {
        try {
            return Optional.of(new StreamSource(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }
}
