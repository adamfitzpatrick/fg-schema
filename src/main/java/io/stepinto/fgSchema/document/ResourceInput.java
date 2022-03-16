package io.stepinto.fgSchema.document;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.w3c.dom.ls.LSInput;

import java.io.InputStream;
import java.io.Reader;

@Accessors(chain = false)
public class ResourceInput implements LSInput {

    @Getter(onMethod = @__({@Override}))
    @Setter(onMethod = @__({@Override}))
    private InputStream byteStream;

    @Getter(onMethod = @__({@Override}))
    @Setter(onMethod = @__({@Override}))
    private String systemId;

    @Setter(onMethod = @__({@Override}))
    @Getter(onMethod = @__({@Override}))
    private String baseURI;

    @Getter(onMethod = @__({@Override}))
    @Setter(onMethod = @__({@Override}))
    private Reader characterStream;

    @Getter(onMethod = @__({@Override}))
    @Setter(onMethod = @__({@Override}))
    private String stringData;

    @Getter(onMethod = @__({@Override}))
    @Setter(onMethod = @__({@Override}))
    private String publicId;

    @Getter(onMethod = @__({@Override}))
    @Setter(onMethod = @__({@Override}))
    private String encoding;

    @Setter(onMethod = @__({@Override}))
    private boolean certifiedText;

    public ResourceInput byteStream(InputStream byteStream) {
        this.byteStream = byteStream;
        return this;
    }

    public ResourceInput systemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    public ResourceInput baseURI(String baseURI) {
        this.baseURI = baseURI;
        return this;
    }

    @Override
    public boolean getCertifiedText() {
        return certifiedText;
    }
}
