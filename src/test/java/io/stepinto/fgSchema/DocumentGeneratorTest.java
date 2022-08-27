package io.stepinto.fgSchema;

import io.stepinto.FgSchema.DocumentGenerator;
import io.stepinto.FgSchema.exception.ProcessingException;
import io.stepinto.FgSchema.helpers.DynamicElementPostProcessor;
import io.stepinto.FgSchema.helpers.Marshaller;
import io.stepinto.FgSchema.schema.DefinitionRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DocumentGeneratorTest {
    @Mock
    private Marshaller<DefinitionRoot> marshaller;

    @Mock
    private DynamicElementPostProcessor dynamicElementPostProcessor;

    private DefinitionRoot definitionRoot;
    private DocumentGenerator<DefinitionRoot> sut;

    @BeforeEach
    public void setup() {
        definitionRoot = new DefinitionRoot()
                .version("1.0")
                .name("Name")
                .author("Author")
                .ruleset("Game")
                .displayname("Stuff");
        sut = new DocumentGenerator<>(marshaller, dynamicElementPostProcessor);
    }

    @Test
    public void generateHappyPathTest() throws ProcessingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        sut.generate(definitionRoot, outputStream);
        verify(marshaller).marshal(eq(definitionRoot), any(OutputStream.class));
        verify(dynamicElementPostProcessor).process(any(InputStream.class), any(OutputStream.class));
    }
}
