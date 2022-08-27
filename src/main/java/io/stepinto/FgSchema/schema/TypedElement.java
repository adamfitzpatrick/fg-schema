package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

@XmlType
public class TypedElement implements Serializable {

    public TypedElement() {}

    @Getter
    @Setter
    @XmlAttribute
    private FgSchemaType type;

    @Getter
    @XmlValue
    private String value;

    public TypedElement value(String value) {
        this.value = value;
        return this;
    }

    public TypedElement value(int value) {
        this.value = String.valueOf(value);
        return this;
    }

    public TypedElement value(Dice value) {
        this.value = value.toString();
        return this;
    }
}
