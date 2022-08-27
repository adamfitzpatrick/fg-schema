package io.stepinto.FgSchema.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum FgSchemaType {
    string("string"),
    number("number"),
    formattedtext("formattedtext"),
    dice("dice");

    private final String value;

    FgSchemaType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
