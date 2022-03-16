package io.stepinto.fgSchema.utils;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.List;

public class ConfigurationModel {

    @Setter
    @Getter
    private List<URI> externalSchemaUris;

    @Override
    public boolean equals(Object other) {
        if (other == null || !other.getClass().equals(this.getClass())) {
            return false;
        }
        ConfigurationModel otherConfig = (ConfigurationModel) other;
        return otherConfig.externalSchemaUris.equals(this.externalSchemaUris);
    }
}
