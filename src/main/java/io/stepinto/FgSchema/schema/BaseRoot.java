package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement(name = "baseroot")
@XmlType()
public abstract class BaseRoot implements Serializable {
    @Getter
    @Setter
    @XmlAttribute
    protected String version;
}
