package io.stepinto.fgSchema.document;

import org.w3c.dom.ls.LSInput;

import java.io.InputStream;
import java.io.Reader;

public class ResourceInput implements LSInput {
    private InputStream byteStream;
    private String systemId;

    public ResourceInput byteStream(InputStream byteStream) {
        this.byteStream = byteStream;
        return this;
    }

    public ResourceInput systemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    @Override
    public Reader getCharacterStream() {
        return null;
    }

    @Override
    public void setCharacterStream(Reader characterStream) {

    }

    @Override
    public InputStream getByteStream() {
        return byteStream;
    }

    @Override
    public void setByteStream(InputStream byteStream) {
        this.byteStream = byteStream;
    }

    @Override
    public String getStringData() {
        return null;
    }

    @Override
    public void setStringData(String stringData) {

    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String getPublicId() {
        return null;
    }

    @Override
    public void setPublicId(String publicId) {

    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public void setBaseURI(String baseURI) {

    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public void setEncoding(String encoding) {

    }

    @Override
    public boolean getCertifiedText() {
        return false;
    }

    @Override
    public void setCertifiedText(boolean certifiedText) {

    }
}
