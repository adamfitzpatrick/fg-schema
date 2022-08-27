package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.UUID;

@XmlType()
public class DynamicTableRow extends DynamicElement {

    @Getter
    @Setter
    @XmlElement
    private TypedElement fromrange;

    @Getter
    @Setter
    @XmlElement
    private TypedElement torange;

    @Getter
    @Setter
    @XmlElement
    @XmlElementWrapper(name = "results")
    private List<DynamicResultTableRow> results;
    public DynamicTableRow runtimeTagName(String runtimeTagName) {
        super.runtimeTagName(runtimeTagName);
        return this;
    }
}
