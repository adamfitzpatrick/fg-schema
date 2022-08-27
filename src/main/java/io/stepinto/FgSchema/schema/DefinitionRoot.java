package io.stepinto.FgSchema.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="root")
@XmlType(propOrder = { "name", "author", "ruleset", "displayname" })
public class DefinitionRoot extends BaseRoot {

    public DefinitionRoot version(String version) {
        super.version(version);
        return this;
    }
    @Getter
    @Setter
    @XmlElement
    private String name;

    @Getter
    @Setter
    @XmlElement
    private String author;

    @Getter
    @Setter
    @XmlElement
    private String ruleset;

    @Getter
    @Setter
    @XmlElement
    private String displayname;
}
