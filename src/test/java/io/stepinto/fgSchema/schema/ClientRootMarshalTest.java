package io.stepinto.fgSchema.schema;

import io.stepinto.FgSchema.exception.ConfigurationException;
import io.stepinto.FgSchema.helpers.Marshaller;
import io.stepinto.FgSchema.schema.ClientRoot;
import io.stepinto.fgSchema.TestBase;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ClientRootMarshalTest extends TestBase {

    @Test
    public void test() throws ConfigurationException {
        baseSetup();
        Marshaller<ClientRoot> marshaller = new Marshaller<>(ClientRoot.class);
        assertDoesNotThrow(() -> marshaller.marshal(clientRoot, new ByteArrayOutputStream()));
    }
}
