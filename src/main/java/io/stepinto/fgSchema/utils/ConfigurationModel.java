package io.stepinto.fgSchema.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ConfigurationModel {

    @Setter
    @Getter
    private List<String> externalSchemaPaths;

    @Override
    public boolean equals(Object other) {
        if (other == null || !other.getClass().equals(this.getClass())) {
            return false;
        }
        ConfigurationModel otherConfig = (ConfigurationModel) other;
        return otherConfig.externalSchemaPaths.equals(this.externalSchemaPaths);
    }
}
