package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@XmlType
public class DynamicResultTableRow extends DynamicElement {
    @Getter
    @Setter
    @XmlElement
    private TypedElement result;
    public DynamicResultTableRow runtimeTagName(String runtimeTagName) {
        super.runtimeTagName(runtimeTagName);
        return this;
    }
}
