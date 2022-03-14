package io.stepinto.fgSchema.utils;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.List;

public class ConfigurationModel {

    @Setter
    @Getter
    private List<URL> externalSchemaUrls;

    @Override
    public boolean equals(Object other) {
        if (other == null || !other.getClass().equals(this.getClass())) {
            return false;
        }
        ConfigurationModel otherConfig = (ConfigurationModel) other;
        return otherConfig.externalSchemaUrls.equals(this.externalSchemaUrls);
    }
}
