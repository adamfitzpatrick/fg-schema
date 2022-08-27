package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
public class Table extends DynamicElement {
    @Getter
    @Setter
    @XmlElement
    private TypedElement locked;

    @Getter
    @Setter
    @XmlElement
    private TypedElement name;

    @Getter
    @Setter
    @XmlElement
    private TypedElement description;

    @Getter
    @Setter
    @XmlElement
    private TypedElement output;

    @Getter
    @Setter
    @XmlElement
    private TypedElement notes;

    @Getter
    @Setter
    @XmlElement
    private TypedElement hiderollresults;

    @Getter
    @Setter
    @XmlElement
    private TypedElement mod;

    @Getter
    @Setter
    @XmlElement
    private TypedElement dice;

    @Getter
    @Setter
    @XmlElement
    private TypedElement labelcol1;

    @Getter
    @Setter
    @XmlElement
    private TypedElement resultscols;

    @Getter
    @Setter
    @XmlElement
    @XmlElementWrapper(name = "tablerows")
    private List<DynamicTableRow> tablerow;

    public Table runtimeTagName(String runtimeTagName) {
        super.runtimeTagName(runtimeTagName);
        return this;
    }
}
