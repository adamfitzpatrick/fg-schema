package io.stepinto.FgSchema.helpers;

import io.stepinto.FgSchema.exception.ConfigurationException;
import io.stepinto.FgSchema.exception.ProcessingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import static javax.xml.bind.Marshaller.JAXB_ENCODING;

public class Marshaller<T extends Serializable> {
    private final javax.xml.bind.Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public Marshaller(Class<T> clazz) throws ConfigurationException {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            marshaller = context.createMarshaller();
            marshaller.setProperty(JAXB_ENCODING, "ISO-8859-1");
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            // TODO Begin incorporating more information about the circumstances in which this may occur in commentary and exception data
            throw new ConfigurationException(e.getMessage(), e);
        }
    }

    public void marshal(T object, OutputStream outputStream) throws ProcessingException {
        try {
            marshaller.marshal(object, outputStream);
        } catch (JAXBException e) {
            // TODO Begin incorporating more information about the circumstances in which this may occur in commentary and exception data
            throw new ProcessingException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public T unmarshal(InputStream xmlStream) throws ProcessingException {
        try {
            String string = new String(xmlStream.readAllBytes(), StandardCharsets.UTF_8);
            InputStream stream = new ByteArrayInputStream(string.getBytes());
            Object object = unmarshaller.unmarshal(stream);
            return (T) object;
        } catch (IOException | JAXBException e) {
            throw new ProcessingException(e.getMessage(), e);
        }
    }
}
