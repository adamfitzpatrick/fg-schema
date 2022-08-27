package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "root")
@XmlType
public class ClientRoot extends BaseRoot {

    @Getter
    @Setter
    @XmlElement(name = "category")
    @XmlElementWrapper(name = "tables")
    private List<Category> tables;
}
