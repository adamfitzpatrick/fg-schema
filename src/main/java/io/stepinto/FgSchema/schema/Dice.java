package io.stepinto.FgSchema.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum Dice {
    d4("d4"),
    d6("d6"),
    d8("d8"),
    d10("d10"),
    d12("d12"),
    d20("d20");

    private final String value;

    Dice(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
