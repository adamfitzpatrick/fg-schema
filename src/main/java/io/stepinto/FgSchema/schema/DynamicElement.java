package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.UUID;

@XmlType
public abstract class DynamicElement implements Serializable {
    @Getter
    @Setter
    @XmlAttribute
    protected String runtimeTagName;
}
