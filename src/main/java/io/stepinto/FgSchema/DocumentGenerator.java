package io.stepinto.FgSchema;

import io.stepinto.FgSchema.exception.ProcessingException;
import io.stepinto.FgSchema.helpers.DynamicElementPostProcessor;
import io.stepinto.FgSchema.helpers.Marshaller;
import io.stepinto.FgSchema.schema.BaseRoot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class DocumentGenerator<T extends BaseRoot> {
    private final Marshaller<T> marshaller;
    private final DynamicElementPostProcessor postProcessor;

    public DocumentGenerator(Marshaller<T> marshaller, DynamicElementPostProcessor postProcessor) {
        this.marshaller = marshaller;
        this.postProcessor = postProcessor;
    }

    public void generate(T object, OutputStream outputStream) throws ProcessingException {
        ByteArrayOutputStream transitionalOutput = new ByteArrayOutputStream();
        marshaller.marshal(object, outputStream);
        ByteArrayInputStream transitionalInput = new ByteArrayInputStream(transitionalOutput.toByteArray());
        postProcessor.process(transitionalInput, outputStream);
    }
}
