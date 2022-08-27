package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
public class Category {
    @Getter
    @Setter
    @XmlAttribute
    private String name;

    @Getter
    @Setter
    @XmlAttribute
    private String baseicon;

    @Getter
    @Setter
    @XmlAttribute
    private String decalicon;

    @Getter
    @Setter
    @XmlElement
    private TypedElement thing;

    @Getter
    @Setter
    @XmlElement(name = "table")
    private List<Table> tables;
}
